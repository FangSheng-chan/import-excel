package com.klec.importexcel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author fangsheng
 * @date 2021/8/17 3:13 下午
 */
public class Meter {
  private Long meterId;

  private Long boxId;

  @ExcelProperty("县级单位")
  private String county;

  @ExcelProperty("供电所")
  private String deptName;

  @ExcelProperty("计量箱条码")
  private String boxBarCode;

  @ExcelProperty("抄表段")
  private String meterSection;

  @ExcelProperty("箱内户号")
  private Long consNo;

  @ExcelProperty("户名")
  private String consName;

  @ExcelProperty("统计时间")
  private Date updateTime;

  @ExcelProperty("地址")
  private String consAddress;

  @ExcelProperty("表计条码")
  private String barCode;

  public Meter() {}

  public Meter(
      Long meterId,
      Long boxId,
      String county,
      String deptName,
      String boxBarCode,
      String meterSection,
      Long consNo,
      String consName,
      Date updateTime,
      String consAddress,
      String barCode) {
    this.meterId = meterId;
    this.boxId = boxId;
    this.county = county;
    this.deptName = deptName;
    this.boxBarCode = boxBarCode;
    this.meterSection = meterSection;
    this.consNo = consNo;
    this.consName = consName;
    this.updateTime = updateTime;
    this.consAddress = consAddress;
    this.barCode = barCode;
  }

  public Long getMeterId() {
    return meterId;
  }

  public void setMeterId(Long meterId) {
    this.meterId = meterId;
  }

  public Long getBoxId() {
    return boxId;
  }

  public void setBoxId(Long boxId) {
    this.boxId = boxId;
  }

  public String getCounty() {
    return county;
  }

  public void setCounty(String county) {
    this.county = county;
  }

  public String getDeptName() {
    return deptName;
  }

  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }

  public String getBoxBarCode() {
    return boxBarCode;
  }

  public void setBoxBarCode(String boxBarCode) {
    this.boxBarCode = boxBarCode;
  }

  public String getMeterSection() {
    return meterSection;
  }

  public void setMeterSection(String meterSection) {
    this.meterSection = meterSection;
  }

  public Long getConsNo() {
    return consNo;
  }

  public void setConsNo(Long consNo) {
    this.consNo = consNo;
  }

  public String getConsName() {
    return consName;
  }

  public void setConsName(String consName) {
    this.consName = consName;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public String getConsAddress() {
    return consAddress;
  }

  public void setConsAddress(String consAddress) {
    this.consAddress = consAddress;
  }

  public String getBarCode() {
    return barCode;
  }

  public void setBarCode(String barCode) {
    this.barCode = barCode;
  }

  @Override
  public String toString() {
    return "Meter{" +
            "meterId=" + meterId +
            ", boxId=" + boxId +
            ", boxBarCode='" + boxBarCode + '\'' +
            ", consName='" + consName + '\'' +
            ", barCode='" + barCode + '\'' +
            '}';
  }
}
