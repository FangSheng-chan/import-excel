package com.klec.importexcel.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.klec.importexcel.service.MeterService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author fangsheng
 * @date 2021/8/17 4:34 下午
 */
public class NoModelDataListener extends AnalysisEventListener<Map<Integer, String>> {
  /** 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收 */
  private static final int BATCH_COUNT = 5;

  List<Map<Integer, String>> list = new ArrayList<>();

  @Resource private MeterService meterService;

  public NoModelDataListener(MeterService meterService) {
    this.meterService = meterService;
  }

  @Override
  public void doAfterAllAnalysed(AnalysisContext context) {
    saveData();
  }

  @Override
  public void invoke(Map<Integer, String> data, AnalysisContext analysisContext) {
    list.add(data);
    if (list.size() >= BATCH_COUNT) {
      saveData();
      list.clear();
    }
  }

  /** 加上存储数据库 */
  private void saveData() {
    meterService.saveData(list);
  }
}
