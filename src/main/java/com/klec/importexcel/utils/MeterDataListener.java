package com.klec.importexcel.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.klec.importexcel.model.Meter;
import com.klec.importexcel.service.MeterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author fangsheng
 * @date 2021/8/17 3:18 下午
 */
public class MeterDataListener extends AnalysisEventListener<Meter> {

  private static final Logger LOGGER = LoggerFactory.getLogger(MeterDataListener.class);

  /** 每隔30条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收 */
  private static final int BATCH_COUNT = 3;

  Map<String, Meter> map = new TreeMap<>();

  List<Meter> list = new ArrayList<>();

  List<Meter> result = new ArrayList<>();

  private static final ExecutorService executorService = Executors.newFixedThreadPool(3);

  @Resource private MeterService meterService;

  public MeterDataListener(MeterService meterService) {
    this.meterService = meterService;
  }

  @Override
  public void invoke(Meter meter, AnalysisContext analysisContext) {
    list.add(meter);
    if (list.size() >= BATCH_COUNT) {
      //      saveData2();
      Todo();
      //      saveData();
      list.clear();
    }
  }

  /**
   * 在转换异常 获取其他异常下会调用本接口。抛出异常则停止读取。如果这里不抛出异常则 继续读取下一行。
   *
   * @param exception
   * @param context
   */
  @Override
  public void onException(Exception exception, AnalysisContext context) {
    LOGGER.error("解析失败，但是继续解析下一行:{}", exception.getMessage());
    if (exception instanceof ExcelDataConvertException) {
      ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) exception;
      LOGGER.error(
          "第{}行，第{}列解析异常",
          excelDataConvertException.getRowIndex(),
          excelDataConvertException.getColumnIndex());
    }
  }

  @Override
  public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    //    saveData2();
    //    Todo();
    LOGGER.info("全部数据解析完毕");
  }

  private void saveData() {
    meterService.save(list);
  }

  Set<Meter> set = new TreeSet<>(Comparator.comparing(Meter::getBoxBarCode));

  private void Todo() {
    List<Meter> result = new ArrayList<>();
    List<Meter> meters = distinctExcel(list);
    List<Meter> meterList = distinctDataBase();
    result.addAll(meters);
    result.addAll(meterList);
    set.addAll(result);

    //    CountDownLatch countDownLatch = new CountDownLatch(list.size());
    //    try {
    //      executorService.execute(
    //              () -> {
    //                distinctDataBase();
    //                countDownLatch.countDown();
    //              });
    //      countDownLatch.await();
    //    } catch (InterruptedException e) {
    //      e.printStackTrace();
    //    }
  }

  private List<Meter> distinctExcel(List<Meter> list) {
    Set<Meter> set = new TreeSet<>(Comparator.comparing(Meter::getBoxBarCode));
    set.addAll(list);
    return new ArrayList<>(set);
  }

  private List<Meter> distinctDataBase() {
    List<Meter> meters = meterService.queryAll();
    if (meters != null) {
      Set<Meter> set = new TreeSet<>(Comparator.comparing(Meter::getBoxBarCode));
      set.addAll(meters);
      return new ArrayList<>(set);
    }
    return new ArrayList<>();
  }

  private void insert(List list) {
    meterService.saveBox(list);
  }
}
