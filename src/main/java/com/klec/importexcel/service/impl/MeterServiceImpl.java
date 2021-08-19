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

/**
 * @author fangsheng
 * @date 2021/8/17 3:33 下午
 */
@Service
public class MeterServiceImpl implements MeterService {

  @Resource private MeterMapper meterMapper;

  private static final Logger LOGGER = LoggerFactory.getLogger(MeterServiceImpl.class);

  ExecutorService executorService = Executors.newFixedThreadPool(2);

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

  @Override
  public void saveMeter(List<Meter> list) {
    long startTime = System.currentTimeMillis();
    for (Meter meter : list) {
      //      try {
      //        singleProcess(meter);
      //      } catch (Exception e) {
      //        e.printStackTrace();
      //      }
      executorService.execute(
          () -> {
            try {
              singleProcess(meter);
            } catch (Throwable e) {
              e.printStackTrace();
            }
          });
    }
    long endTime = System.currentTimeMillis();
    LOGGER.info("time:{}", (endTime - startTime) / 1000);
  }

  private synchronized void singleProcess(Meter meter) {
    if (StringUtils.isEmpty(meter.getBarCode())) {
      return;
    }
    Meter meter1 = meterMapper.queryByBarCode(meter.getBoxBarCode());
    Long boxId = 0L;
    Long meterId = 0L;

    if (meter1 == null) {
      meterMapper.saveBox(meter);
      boxId = meter.getBoxId();
    } else {
      boxId = meter1.getBoxId();
    }
    Meter m2 = meterMapper.query(meter.getBarCode());
    if (m2 == null) {
      meterMapper.save(meter);
      meterId = meter.getBoxId();
    } else {
      LOGGER.info("boxId:{}, meterId:{}, meterBarCode:{}", boxId, meterId, meter.getBarCode());
      return;
    }
    if (meterId == null) {
      LOGGER.info("boxId:{}, meterBarCode:{}, meterId is null", boxId, meter.getBoxBarCode());
      return;
    }
    meterMapper.saveMeterBoxRelationShip(boxId, meterId);
  }
}
