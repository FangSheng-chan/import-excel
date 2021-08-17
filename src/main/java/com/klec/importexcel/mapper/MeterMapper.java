package com.klec.importexcel.mapper;

import com.klec.importexcel.model.Meter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author fangsheng
 * @date 2021/8/17 3:25 下午
 */
@Mapper
public interface MeterMapper {
  void save(List<Meter> list);

  void saveBox(List<Meter> list);

  void saveData(List<Map<Integer, String>> list);
}
