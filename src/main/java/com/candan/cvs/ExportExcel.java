package com.candan.cvs;


import com.candan.configuration.ConfigurationReader;
import com.candan.mongo.swb.Max3003;
import com.candan.mongo.swb.Max30102;
import com.candan.mongo.swb.Si7021;
import com.candan.mongo.swb.SkinResistance;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

@Component
public class ExportExcel {

    @Autowired
    ConfigurationReader.MyConfig config;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void WriteDataToExcelFile(List<Max3003> max3003List, List<Max30102> max30102List, List<Si7021> si7021List,
                                     List<SkinResistance> skinResistanceList) {
        parseMax3003Data(max3003List);
        parseMax30102Data(max30102List);
        parseSi7021Data(si7021List);
        parseSkinResistanceData(skinResistanceList);

    }

    public void parseSkinResistanceData(List<SkinResistance> skinList) {
        XSSFWorkbook wbSkin = new XSSFWorkbook();
        XSSFSheet sheet = wbSkin.createSheet("Data");
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        data.put("1", new Object[]{"ID", "STATUS", "SR_VALUE", "PERSON_NAME", "PERSON_SURNAME", "DATE"});
        int i = 1;
        for (SkinResistance sr : skinList) {
            i++;
            data.put("" + i, new Object[]{"" + sr.getId(), sr.getStatus(), sr.getSrValue(), sr.getPersonName(), sr.getPersonSurname(), sr.getDate()});
        }
        setExcelTables(data, sheet);
        saveExcelFile(config.getExcelPath() + "/skin.xlsx", wbSkin);
        data.clear();
    }

    public void parseMax3003Data(List<Max3003> max3003List) {
        XSSFWorkbook wbHeart = new XSSFWorkbook();
        XSSFSheet sheet = wbHeart.createSheet("Data");
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        data.put("1", new Object[]{"ID", "STATUS", "ECG", "RR", "PERSON_NAME", "PERSON_SURNAME", "DATE"});
        int i = 1;
        for (Max3003 m : max3003List) {
            i++;
            data.put("" + i, new Object[]{"" + m.getId(), m.getStatus(), m.getEcg(), m.getRr(),
                    m.getPersonName(), m.getPersonSurname(), m.getDate()});
        }
        setExcelTables(data, sheet);
        saveExcelFile(config.getExcelPath() + "/max3003.xlsx", wbHeart);
        data.clear();
    }

    public void parseMax30102Data(List<Max30102> max30102List) {
        XSSFWorkbook wbHeart = new XSSFWorkbook();
        XSSFSheet sheet = wbHeart.createSheet("Data");
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        data.put("1", new Object[]{"ID", "STATUS", "HR", "SPO2","IRED", "RED", "DIFF", "PERSON_NAME", "PERSON_SURNAME", "DATE"});
        int i = 1;
        for (Max30102 m : max30102List) {
            i++;
            data.put("" + i, new Object[]{"" + m.getId(), m.getStatus(), m.getHr(), m.getSpo2(),m.getIred(), m.getRed(),
                    m.getDiff(),  m.getPersonName(), m.getPersonSurname(), m.getDate()});
        }
        setExcelTables(data, sheet);
        saveExcelFile(config.getExcelPath() + "/max30102.xlsx", wbHeart);
        data.clear();
    }

    private void parseSi7021Data(List<Si7021> si7021List) {
        XSSFWorkbook wbHeart = new XSSFWorkbook();
        XSSFSheet sheet = wbHeart.createSheet("Data");
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        data.put("1", new Object[]{"ID", "STATUS", "HUMIDITY", "TEMP", "PERSON_NAME", "PERSON_SURNAME", "DATE"});
        int i = 1;
        for (Si7021 s : si7021List) {
            i++;
            data.put("" + i, new Object[]{"" + s.getId(), s.getStatus(), s.getHumidity(), s.getTemperature(),
                    s.getPersonName(), s.getPersonSurname(), s.getDate()});
        }
        setExcelTables(data, sheet);
        saveExcelFile(config.getExcelPath() + "/Si7021.xlsx", wbHeart);
        data.clear();
    }

    public void setExcelTables(Map<String, Object[]> data, XSSFSheet sheet) {
        //Iterate over data and write to sheet
        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset) {
            Row row = sheet.createRow(rownum++);
            Object[] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                if (obj instanceof String)
                    cell.setCellValue((String) obj);
                else if (obj instanceof Integer)
                    cell.setCellValue((Integer) obj);
                else if (obj instanceof Date)
                    cell.setCellValue(((Date) obj).toString());
            }
        }
    }

    public void saveExcelFile(String path, XSSFWorkbook workbook) {
        try {
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File(path));
            workbook.write(out);
            out.close();
            logger.info("excel file " + path + " successfully written on disk");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}


