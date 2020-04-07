package com.candan.cvs;


import com.candan.configuration.ConfigurationReader;
import com.candan.db.Environment;
import com.candan.db.Heart;
import com.candan.db.Skin;
import com.candan.db.UserInfo;
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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@Component
public class ExportExcel {

    @Autowired
    ConfigurationReader.MyConfig config;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public  void WriteDataToExcelFile(List<Skin> skinList, List<Environment> environmentList, List<Heart> heartList){
        parseSkinData(skinList);
        parseEnvironmentData(environmentList);
        parseHeartData(heartList);
    }

    public void parseSkinData(List<Skin> skinList){
        XSSFWorkbook wbSkin = new XSSFWorkbook();
        XSSFSheet sheet = wbSkin.createSheet("Data");
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        data.put("1", new Object[] {"ID", "TYPE", "DATE","DATA","PERSON"});
        int i=1;
        for(Skin skin : skinList){
            i++;
            data.put(""+i,new Object[]{""+skin.getId(),skin.getType(),skin.getDate().toString(),skin.getData(),skin.getPerson()});
        }
        setExcelTables(data,sheet);

        saveExcelFile(config.getExcelPath()+"/skin.xlsx",wbSkin);
        data.clear();
    }

    public void parseHeartData(List<Heart> heartList){
        XSSFWorkbook wbHeart = new XSSFWorkbook();
        XSSFSheet sheet = wbHeart.createSheet("Data");
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        data.put("1", new Object[] {"ID", "TYPE", "DATE","DATA","PERSON"});
        int i=1;
        for(Heart heart : heartList){
            i++;
            data.put(""+i,new Object[]{""+heart.getId(),heart.getType(),heart.getDate().toString(),heart.getData(),heart.getPerson()});
        }
        setExcelTables(data,sheet);
        saveExcelFile(config.getExcelPath()+"/hr.xlsx",wbHeart);
        data.clear();
    }

    public void parseEnvironmentData(List<Environment> environmentList){
        XSSFWorkbook wbEnvironment = new XSSFWorkbook();
        XSSFSheet sheet = wbEnvironment.createSheet("Data");
        Map<String, Object[]> dataHumidity = new TreeMap<String, Object[]>();
        Map<String, Object[]> dataTemperature = new TreeMap<String, Object[]>();
        Map<String, Object[]> dataLux = new TreeMap<String, Object[]>();
        dataHumidity.put("1", new Object[] {"ID", "TYPE", "DATE","DATA","PERSON"});
        dataTemperature.put("1", new Object[] {"ID", "TYPE", "DATE","DATA","PERSON"});
        dataLux.put("1", new Object[] {"ID", "TYPE", "DATE","DATA","PERSON"});
        int i=1,j=1,k=1;
        for(Environment env : environmentList){
            if(env.getType().equals("humidity")){
                i++;
                dataHumidity.put(""+i,new Object[]{""+env.getId(),env.getType(),env.getDate().toString(),env.getData(),env.getPerson()});
            }else if (env.getType().equals("temperature")){
                j++;
                dataTemperature.put(""+j,new Object[]{""+env.getId(),env.getType(),env.getDate().toString(),env.getData(),env.getPerson()});
            }else{
                k++;
                dataLux.put(""+k,new Object[]{""+env.getId(),env.getType(),env.getDate().toString(),env.getData(),env.getPerson()});
            }
        }
        setExcelTables(dataHumidity,sheet);
        saveExcelFile(config.getExcelPath()+"/humidity.xlsx",wbEnvironment);
        setExcelTables(dataTemperature,sheet);
        saveExcelFile(config.getExcelPath()+"/temperature.xlsx",wbEnvironment);
        setExcelTables(dataLux,sheet);
        saveExcelFile(config.getExcelPath()+"/luminance.xlsx",wbEnvironment);
    }

    public void  setExcelTables(Map<String, Object[]> data,XSSFSheet sheet){
        //Iterate over data and write to sheet
        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset)
        {
            Row row = sheet.createRow(rownum++);
            Object [] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr)
            {
                Cell cell = row.createCell(cellnum++);
                if(obj instanceof String)
                    cell.setCellValue((String)obj);
                else if(obj instanceof Integer)
                    cell.setCellValue((Integer)obj);
            }
        }
    }

    public  void  saveExcelFile(String path,XSSFWorkbook workbook ){
        try
        {
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File(path));
            workbook.write(out);
            out.close();
            logger.info("excel file "+path+" successfully written on disk");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}


