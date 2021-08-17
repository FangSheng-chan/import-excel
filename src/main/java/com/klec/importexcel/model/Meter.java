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
@Data
@AllArgsConstructor
@NoArgsConstructor
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
}
