package com.candan.services;

import com.candan.interfaces.Si7021RepositoryReal;
import com.candan.mongo.swb.Max30102;
import com.candan.mongo.swb.Si7021;
import com.candan.exceptions.BadResourceException;
import com.candan.interfaces.Si7021Repository;
import com.candan.mongo.swb.Si7021Real;
import com.candan.mongo.swb.SkinResistanceReal;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class Si7021Service {
    private final Logger logger = Logger.getLogger(this.getClass());

    public LinkedBlockingQueue<Si7021> lbq = new LinkedBlockingQueue<>();

    @Autowired
    private Si7021Repository si7021Repository;

    @Autowired
    private Si7021RepositoryReal si7021RepositoryReal;

    public void update(Si7021 si7021) throws BadResourceException, org.springframework.data.rest.webmvc.ResourceNotFoundException {
        try {
            Si7021 tmpObj = si7021Repository.findById(si7021.getId()).get();
            getClonedData(tmpObj, si7021);
            si7021Repository.save(tmpObj);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
        }
    }

    private void getClonedData(Si7021 tmpObj, Si7021 si7021) {
        tmpObj.setDate(si7021.getDate());
        tmpObj.setPersonName(si7021.getPersonName());
        tmpObj.setStatus(si7021.getStatus());
        tmpObj.setHumidity(si7021.getHumidity());
        tmpObj.setTemperature(si7021.getTemperature());
    }

    public void deleteByDate(Date date) {
        try {
            si7021Repository.deleteByDate(date);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
        }
    }

    public Si7021 save(Si7021 si7021) {
        try {
            //si7021RepositoryReal
            List<Si7021Real> si7021List = getDataObjFromRawData(si7021);
            for(Si7021Real sr :si7021List )
                si7021RepositoryReal.save(sr);
            return si7021Repository.save(si7021);
        } catch (Exception ex) {
            logger.error("Exception on", ex);
            return null;
        }
    }

    private List<Si7021Real> getDataObjFromRawData(Si7021 si7021) {
        List<Si7021Real> Si7021List = new ArrayList<>();
        Date dateOfSi7021 = si7021.getDate();
        long firstDateTime = 0;
        if(dateOfSi7021==null){
            firstDateTime = 1;
        }else{
            firstDateTime =  dateOfSi7021.getTime();
        }
        List<Float> getHumidityDataList = getParsedDataSkinResistance(si7021.getHumidity());
        List<Float> getTemperatureList = getParsedDataSkinResistance(si7021.getTemperature());
        int i= 0;
        float ft = 0;
        for (Float fh : getHumidityDataList){
            if(i<getTemperatureList.size()){
                ft = getTemperatureList.get(i);
            }
            i++;
            Si7021Real  srl = new Si7021Real(si7021.getStatus(),fh,ft,
                    si7021.getPersonName(),si7021.getPersonSurname(),new Date(firstDateTime++));
            Si7021List.add(srl);
        }
        return Si7021List;
    }

    private List<Float> getParsedDataSkinResistance(String srValue) {
        List<Float> lf = new ArrayList<>();
        for( int i = 0; i<srValue.length(); i = i+8){
            String s =srValue.substring(i,i+8);
            Long l= Long.parseLong(s, 16);
            Float f = Float.intBitsToFloat(l.intValue());
            lf.add(f);
        }
        return lf;
    }

    public List<Si7021> findListByNameSurname(String name, String surname) {
        try {
            return si7021Repository.findByPersonName(name, surname);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return null;
        }
    }

    public List<Si7021> findListByNameSurnameFromReal(String name, String surname) {
        try {
            return si7021RepositoryReal.findByPersonName(name, surname);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return null;
        }
    }

    public List<Si7021> findListByStatus(String status) {
        try {
            return si7021Repository.findByStatus(status);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return null;
        }
    }

    public List<Si7021> findListByDate(Date from, Date to) {
        try {
            return si7021Repository.findByDateBetween(from, to);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return null;
        }
    }

    public Si7021 findById(String id) {
        try {
            return si7021Repository.findById(id).get();
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return null;
        }
    }

    public List<Si7021> findListByMaxNumber(int maxVal) {
        try {
            Pageable pageableRequest = PageRequest.of(0, maxVal);
            Page<Si7021> page = si7021Repository.findAll(pageableRequest);
            return page.getContent();
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return null;
        }
    }

    public void deleteById(List<String> ids) {
        try {
            for (String id : ids)
                si7021Repository.deleteById(id);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
        }
    }

    public void deleteByNameSurname(List<Si7021> si7021s) {
        try {
            for (Si7021 si7021 : si7021s)
                si7021Repository.deleteByPersonNameAndPersonSurname(si7021.getPersonName(), si7021.getPersonSurname());
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
        }
    }

    public List<Si7021> findListByNameSurnameAndDateFromReal(String name, String surname, String date_from, String date_to) throws ParseException {
        Date from = new SimpleDateFormat("yyyy-MM-dd").parse(date_from);   //new Date("2022-02-04");
        Date to = new SimpleDateFormat("yyyy-MM-dd").parse(date_to);   //new Date("2022-02-04");
        return si7021RepositoryReal.findByDateBetweenAndPersonNameAndPersonSurname(from,to,name,surname);
    }
}
