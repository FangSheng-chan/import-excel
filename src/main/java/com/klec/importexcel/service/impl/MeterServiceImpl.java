package com.klec.importexcel.service.impl;

import com.klec.importexcel.mapper.MeterMapper;
import com.klec.importexcel.model.Meter;
import com.klec.importexcel.service.MeterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author fangsheng
 * @date 2021/8/17 3:33 下午
 */
@Service
public class MeterServiceImpl implements MeterService {

  @Resource private MeterMapper meterMapper;

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
}
