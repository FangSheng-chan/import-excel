package com.klec.importexcel.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.klec.importexcel.model.Meter;
import com.klec.importexcel.service.MeterService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fangsheng
 * @date 2021/8/17 3:18 下午
 */
public class MeterDataListener extends AnalysisEventListener<Meter> {

  private static final int BATCH_COUNT = 5;

  List<Meter> list = new ArrayList<>();

  @Resource private MeterService meterService;

  public MeterDataListener(MeterService meterService) {
    this.meterService = meterService;
  }

  @Override
  public void invoke(Meter meter, AnalysisContext analysisContext) {
    list.add(meter);
    if (list.size() >= BATCH_COUNT) {
      saveData();
      list.clear();
    }
  }

  @Override
  public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    saveData();
  }

  private void saveData() {
    meterService.save(list);
  }
}
