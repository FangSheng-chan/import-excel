package com.klec.importexcel.service.impl;

import com.klec.importexcel.mapper.MeterMapper;
import com.klec.importexcel.model.Meter;
import com.klec.importexcel.service.MeterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author fangsheng
 * @date 2021/8/17 3:33 下午
 */
@Service
public class MeterServiceImpl implements MeterService {

  @Resource private MeterMapper meterMapper;

  @Override
  public void save(List<Meter> list) {
    meterMapper.save(list);
    meterMapper.saveBox(list);
  }

  @Override
  public void saveData(List<Map<Integer, String>> list) {
    Meter meter = new Meter();
    for (Map<Integer, String> map : list) {
      for (Map.Entry<Integer, String> entry : map.entrySet()) {
        Integer key = entry.getKey();
        switch (key) {
          case 0:
            continue;
          case 1:
            meter.setMeterId(Long.valueOf(entry.getValue()));
          case 2:
            meter.setConsName(entry.getValue());
        }
      }
    }

    meterMapper.saveData(list);
  }
}
