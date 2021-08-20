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
  void save2(List<Meter> list);

  void save(Meter meter);

  void saveBox2(List<Meter> list);

  void saveBox(Meter meter);

  void saveMeterBoxRelation(List<Meter> list);

  void saveData(List<Map<Integer, String>> list);

  Meter queryByBarCode(String barCode);

  Meter query(String barCode);

  void saveMeterBoxRelationShip(Long boxId, Long meterId);

  List<Meter> queryAll();
}
