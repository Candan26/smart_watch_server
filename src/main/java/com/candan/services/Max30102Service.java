package com.candan.services;

import com.candan.interfaces.Max3003RepositoryReal;
import com.candan.interfaces.Max30102RepositoryReal;
import com.candan.mongo.swb.*;
import com.candan.exceptions.BadResourceException;
import com.candan.interfaces.Max30102Repository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class Max30102Service {
    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private Max30102Repository max30102Repository;

    @Autowired
    private Max30102RepositoryReal max30102RepositoryReal;

    public LinkedBlockingQueue<Max30102> lbq = new LinkedBlockingQueue<>();

    public void update(Max30102 max30102) throws BadResourceException, org.springframework.data.rest.webmvc.ResourceNotFoundException {
        try {
            Max30102 tmpObj = max30102Repository.findById(max30102.getId()).get();
            getClonedData(tmpObj, max30102);
            max30102Repository.save(tmpObj);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
        }
    }

    private void getClonedData(Max30102 tmpObj, Max30102 max30102) {
        tmpObj.setDate(max30102.getDate());
        tmpObj.setPersonName(max30102.getPersonName());
        tmpObj.setStatus(max30102.getStatus());
        tmpObj.setHr(max30102.getHr());
        tmpObj.setSpo2(max30102.getSpo2());
        tmpObj.setDiff(max30102.getDiff());
    }

    public void deleteByDate(Date date) {
        try {
            max30102Repository.deleteByDate(date);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
        }
    }

    public Max30102 save(Max30102 max30102) {
        try {
            List<Max30102Real> max30102List = getDataObjFromRawData(max30102);
            for(Max30102Real sr :max30102List )
                max30102RepositoryReal.save(sr);
            return max30102Repository.save(max30102);
        } catch (Exception ex) {
            logger.error("Exception on", ex);
            return null;
        }
    }

    private List<Max30102Real> getDataObjFromRawData(Max30102 max30102) {
        List<Max30102Real> max30102RealList = new ArrayList<>();
        Date date = max30102.getDate();
        long firstDateTime = 0;
        if(date==null){
            firstDateTime = 1;
        }else{
            firstDateTime =  date.getTime();
        }
        List<Integer> irList = getParsedData(max30102.getIred());
        List<Integer> rList = getParsedData(max30102.getRed());
        List<Integer> hrList = getParsedByteData(max30102.getHr());
        List<Integer> spo2List = getParsedByteData(max30102.getSpo2());
        int i = 0;
        int r = 0;
        int hr = 0;
        int spo2 = 0;
        for (Integer in : irList){
            if(i<rList.size()){
               r = rList.get(i);
            }
            if(i< hrList.size()){
                hr = hrList.get(i);
            }
            if(i < spo2List.size()){
                spo2 = spo2List.get(i);
            }
            i++;
            Max30102Real  mr = new Max30102Real(max30102.getStatus(),hr,spo2,in,r,"",
                    max30102.getPersonName(),max30102.getPersonSurname(),new Date(firstDateTime++));
            max30102RealList.add(mr);
        }
        return max30102RealList;
    }
    private List<Integer> getParsedData(String srValue) {
        List<Integer> li = new ArrayList<>();
        for( int i = 0; i<srValue.length(); i = i+8){
            String s = srValue.substring(i,i+8);
            li.add(Integer.parseInt(s,16));
        }
        return li;
    }
    private List<Integer> getParsedByteData(String srValue) {
        List<Integer> li = new ArrayList<>();
        for( int i = 0; i<srValue.length(); i = i+2){
            String s = srValue.substring(i,i+2);
            li.add(Integer.parseInt(s,16));
        }
        return li;
    }

    public List<Max30102> findListByNameSurname(String name, String surname) {
        try {
            return max30102Repository.findByPersonName(name, surname);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return null;
        }
    }

    public List<Max30102> findListByStatus(String status) {
        try {
            return max30102Repository.findByStatus(status);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return null;
        }
    }

    public List<Max30102> findListByDate(Date from, Date to) {
        try {
            return max30102Repository.findByDateBetween(from, to);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return null;
        }
    }

    public Max30102 findById(String id) {
        try {
            return max30102Repository.findById(id).get();
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return null;
        }
    }

    public List<Max30102> findListByMaxNumber(int maxVal) {
        try {
            Pageable pageableRequest = PageRequest.of(0, maxVal);
            Page<Max30102> page = max30102Repository.findAll(pageableRequest);
            return page.getContent();
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return null;
        }
    }

    public void deleteById(List<String> ids) {
        try {
            for (String id : ids)
                max30102Repository.deleteById(id);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
        }
    }

    public void deleteByNameSurname(List<Max30102> max30102s) {
        try {
            for (Max30102 max30102 : max30102s)
                max30102Repository.deleteByPersonNameAndPersonSurname(max30102.getPersonName(), max30102.getPersonSurname());
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
        }
    }
}
