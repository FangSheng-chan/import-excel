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

/**
 * @author fangsheng
 * @date 2021/8/17 3:18 下午
 */
public class MeterDataListener2 extends AnalysisEventListener<Meter> {

  private static final Logger LOGGER = LoggerFactory.getLogger(MeterDataListener2.class);

  /** 每隔30条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收 */
  private static final int BATCH_COUNT = 3000;

  Map<String, Meter> map = new TreeMap<>();

  List<Meter> list = new ArrayList<>();

  List<Meter> result = new ArrayList<>();

  @Resource private MeterService meterService;

  public MeterDataListener2(MeterService meterService) {
    this.meterService = meterService;
  }

  @Override
  public void invoke(Meter meter, AnalysisContext analysisContext) {
    list.add(meter);
    if (list.size() >= BATCH_COUNT) {
      saveData2();
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
    saveData2();
    //    List<Meter> MeterList = new ArrayList<>();
    //    for (Map.Entry<String, Meter> entry : map.entrySet()) {
    //      Meter value = entry.getValue();
    //      MeterList.add(value);
    //    }
    //    meterService.saveBox(MeterList);
    //    meterService.saveMeterBoxRelation(result);
    //    LOGGER.info("全部数据解析完毕");
  }

  private void saveData() {
    meterService.save(list);
  }

  private List<Meter> distinctExcel(List<Meter> list) {
    Set<Meter> set = new TreeSet<>(Comparator.comparing(Meter::getBoxBarCode));

    set.addAll(list);

    return new ArrayList<>(set);
  }

  private void saveData2() {
    meterService.saveMeter(list);
  }
}
