package com.candan.services;

import com.candan.interfaces.Max3003RepositoryReal;
import com.candan.mongo.swb.Max3003;
import com.candan.exceptions.BadResourceException;
import com.candan.interfaces.Max3003Repository;
import com.candan.mongo.swb.Max3003Real;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class Max3003Service {

    private final Logger logger = Logger.getLogger(this.getClass());

    public LinkedBlockingQueue<Max3003> lbq = new LinkedBlockingQueue<>();

    @Autowired
    private Max3003Repository max3003Repository;

    @Autowired
    private Max3003RepositoryReal max3003RepositoryReal;

    public void update(Max3003 max3003)throws BadResourceException, org.springframework.data.rest.webmvc.ResourceNotFoundException {
        try {
            Max3003 tmpObj = max3003Repository.findById(max3003.getId()).get();
            getClonedData(tmpObj, max3003);
            max3003Repository.save(tmpObj);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
        }
    }

    private void getClonedData(Max3003 tmpObj, Max3003 max3003) {
        tmpObj.setDate(max3003.getDate());
        tmpObj.setPersonName(max3003.getPersonName());
        tmpObj.setStatus(max3003.getStatus());
        tmpObj.setEcg(max3003.getEcg());
        tmpObj.setRr(max3003.getRr());
    }

    public  void deleteByDate(Date date) {
        try {
            max3003Repository.deleteByDate(date);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
        }
    }

    public  void deleteByNameSurname(List<Max3003> max3003s) {
        try {
            for (Max3003 max30102 : max3003s)
                max3003Repository.deleteByPersonNameAndPersonSurname(max30102.getPersonName(), max30102.getPersonSurname());
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
        }
    }

    public  void deleteById(List<String> ids) {
        try {
            for (String id : ids)
                max3003Repository.deleteById(id);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
        }
    }

    public Max3003 save(Max3003 max3003){
        try {
            List<Max3003Real> max3003RealList = getDataObjFromRawData(max3003);
            for(Max3003Real m :max3003RealList )
                max3003RepositoryReal.save(m);
            return max3003Repository.save(max3003);
        } catch (Exception ex) {
            logger.error("Exception on", ex);
            return null;
        }
    }

    private List<Max3003Real> getDataObjFromRawData(Max3003 max3003) {
        List<Max3003Real> max3003RealList = new ArrayList<>();
        Date dateOfMax3003 = max3003.getDate();
        long firstDateTime = 0;
        if(dateOfMax3003==null){
            firstDateTime = 1;
        }else{
            firstDateTime =  dateOfMax3003.getTime();
        }
        List<Integer> ecgDataList = getParsedDataOfShort(max3003.getEcg());
        //List<Float> bpmDataList = getParsedDataOfFloat(max3003.getBpm());
        List<Integer> rrDataList = getParsedDataOfInt(max3003.getBpm());
        double averageBpm= 0;
        double averageRr = 0;
        /*
        for( Float f : bpmDataList){
            averageBpm = averageBpm + f;
        }
        if (bpmDataList.size()>0){
            averageBpm = averageBpm/bpmDataList.size();
        }

         */
        for( Integer i : rrDataList){
            averageRr = averageRr + i;
        }
        if (rrDataList.size()>0){
            averageRr = averageRr/rrDataList.size();
        }
        //if(!bpmDataList.isEmpty())
        //    averageBpm = ecgDataList.stream().mapToDouble(bpmDataList::get).average().orElse(0);
        //if(!rrDataList.isEmpty())
        //    averageRr = ecgDataList.stream().mapToDouble(rrDataList::get).average().orElse(0);

        for (Integer i : ecgDataList){
            Max3003Real  m = new Max3003Real(max3003.getStatus(),i,averageRr,averageBpm,
                    max3003.getPersonName(),max3003.getPersonSurname(),new Date(firstDateTime));
                    firstDateTime= firstDateTime +8;//128 sps aprx 7.82 for sec ms for each data
                max3003RealList.add(m);
        }
        return max3003RealList;
    }

    private List<Float> getParsedDataOfFloat(String floatString) {
        List<Float> ls = new ArrayList<>();
        //bpm is uchar data
        for( int i = 0; i<floatString.length(); i = i+8){
            String s = floatString.substring(i,i+8);
            ls.add(Float.parseFloat(s));
        }
        return ls;
    }

    private List<Integer> getParsedDataOfInt(String intString) {
        List<Integer> ls = new ArrayList<>();
        //bpm is uchar data
        for( int i = 0; i<intString.length(); i = i+8){
            String s = intString.substring(i,i+8);
            ls.add(Integer.parseUnsignedInt(s,16));
        }
        return ls;
    }

    private List<Integer> getParsedDataOfShort(String shortString) {
        List<Integer> ls = new ArrayList<>();
        //ecg short data
        for( int i = 0; i<shortString.length(); i = i+4){
            String s = shortString.substring(i,i+4);
            ls.add((int)Integer.valueOf(s,16).shortValue());
        }
        return ls;
    }

    public List<Max3003> findListByNameSurname(String name, String surname){
        try {
            return max3003Repository.findByPersonName(name, surname);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return null;
        }
    }

    public List<Max3003> findListByStatus(String status){
        try {
            return max3003Repository.findByStatus(status);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return null;
        }
    }

    public List<Max3003> findListByDate(Date from , Date to){
        try {
            return max3003Repository.findByDateBetween(from, to);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return null;
        }
    }

    public Max3003 findById(String id) {
        try {
            return max3003Repository.findById(id).get();
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return null;
        }
    }

    public List<Max3003> findListByMaxNumber(int maxVal) {
        try {
            Pageable pageableRequest = PageRequest.of(0, maxVal);
            Page<Max3003> page = max3003Repository.findAll(pageableRequest);
            return page.getContent();
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return null;
        }
    }
}
