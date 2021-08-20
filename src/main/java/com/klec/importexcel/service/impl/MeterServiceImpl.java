package com.klec.importexcel.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.klec.importexcel.mapper.MeterMapper;
import com.klec.importexcel.model.Meter;
import com.klec.importexcel.service.MeterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author fangsheng
 * @date 2021/8/17 3:33 下午
 */
@Service
public class MeterServiceImpl implements MeterService {

  @Resource private MeterMapper meterMapper;

  private static final Logger LOGGER = LoggerFactory.getLogger(MeterServiceImpl.class);

  private static final ExecutorService executorService = Executors.newFixedThreadPool(3);

  @Override
  public void save(List<Meter> list) {
    meterMapper.save2(list);
  }

  @Override
  public void saveBox(List<Meter> list) {
    meterMapper.saveBox2(list);
  }

  @Override
  public void saveMeterBoxRelation(List<Meter> list) {
    meterMapper.saveMeterBoxRelation(list);
  }

  //  @Override
  //  public void saveMeter(List<Meter> list) {
  //    CountDownLatch countDownLatch = new CountDownLatch(list.size());
  //    long startTime = System.currentTimeMillis();
  //    try {
  //      for (Meter meter : list) {
  //        executorService.execute(
  //            () -> {
  //              try {
  //                singleProcess(meter);
  //              } catch (Throwable e) {
  //                e.printStackTrace();
  //              } finally {
  //                countDownLatch.countDown();
  //              }
  //            });
  //      }
  //      countDownLatch.await();
  //    } catch (Exception e) {
  //      e.printStackTrace();
  //    }
  //    long endTime = System.currentTimeMillis();
  //    LOGGER.info("time:{}", (endTime - startTime) / 1000);
  //  }

  @Override
  public void importMeter(List<Meter> list) {
    List<Meter> meters = Collections.synchronizedList(list);
    CountDownLatch countDownLatch = new CountDownLatch(list.size());
  }

  @Override
  public List<Meter> queryAll() {
    return meterMapper.queryAll();
  }

  private void singleProcess(Meter parameter) {
    if (StringUtils.isEmpty(parameter.getBarCode())) {
      return;
    }
    Long boxId = null;
    Long meterId = null;
    Meter meterBox = meterMapper.queryByBarCode(parameter.getBoxBarCode());
    if (meterBox == null) {
      meterMapper.saveBox(parameter);
      boxId = parameter.getBoxId();
    } else {
      boxId = meterBox.getBoxId();
    }
    Meter meter = meterMapper.query(parameter.getBarCode());
    if (meter == null) {
      meterMapper.save(parameter);
      meterId = parameter.getMeterId();
    } else {
      LOGGER.info("boxId:{}, meterId:{}, meterBarCode:{}", boxId, meterId, parameter.getBarCode());
      return;
    }
    if (meterId == null) {
      LOGGER.info("boxId:{}, meterBarCode:{}, meterId is null", boxId, parameter.getBoxBarCode());
      return;
    }
    meterMapper.saveMeterBoxRelationShip(boxId, meterId);
  }

  private void importBoxMeter(Meter parameter) {
    Meter meterBox = meterMapper.queryByBarCode(parameter.getBoxBarCode());
    if (meterBox == null) {
      meterMapper.saveBox(parameter);
    }
  }

  private void importMeterData(List<Meter> list) {
    meterMapper.save2(list);
    for (Meter meter : list) {
      Meter query = meterMapper.queryByBarCode(meter.getBoxBarCode());
      meterMapper.saveMeterBoxRelationShip(query.getBoxId(), meter.getMeterId());
    }
  }

  @Override
  public void saveMeter(List<Meter> list) {
    long startTime = System.currentTimeMillis();
    Set<Meter> set = new TreeSet<>(Comparator.comparing(Meter::getBoxBarCode));
    set.addAll(list);
    List<Meter> meters = new ArrayList<>(set);
    for (Meter meter : meters) {
      importBoxMeter(meter);
    }
    importMeterData(list);
    long endTime = System.currentTimeMillis();
    LOGGER.info("time:{}", (endTime - startTime) / 1000);
  }
}
