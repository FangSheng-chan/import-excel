package com.klec.importexcel.service;

import com.klec.importexcel.model.Meter;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author fangsheng
 * @date 2021/8/17 3:32 下午
 */
public interface MeterService {

    void save(List<Meter> list);

    void saveBox(List<Meter> list);

    void saveMeterBoxRelation(List<Meter> list);

    void saveMeter(List<Meter> list);

}
