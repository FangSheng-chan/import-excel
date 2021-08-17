package com.klec.importexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.klec.importexcel.model.Meter;
import com.klec.importexcel.service.MeterService;
import com.klec.importexcel.utils.MeterDataListener;
import com.klec.importexcel.utils.NoModelDataListener;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;

@SpringBootTest
class ImportExcelApplicationTests {

  @Resource private MeterService meterService;

  @Test
  void contextLoads() {
    String fileName = "/Users/ss/Documents" + File.separator + "工作簿4.xlsx";
    ExcelReader excelReader = null;
    try {
      excelReader =
          EasyExcel.read(fileName, Meter.class, new MeterDataListener(meterService)).build();
      ReadSheet readSheet = EasyExcel.readSheet(0).build();
      excelReader.read(readSheet);
    } finally {
      if (excelReader != null) {
        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();
      }
    }
  }

  @Test
  public void noModelRead() {
    String fileName = "/Users/ss/Documents" + File.separator + "ss.xlsx";
    // 这里 只要，然后读取第一个sheet 同步读取会自动finish
    EasyExcel.read(fileName, new NoModelDataListener(meterService)).sheet().doRead();
  }
}
