package com.candan.services;

import com.candan.mongo.swb.Max3003;
import com.candan.exceptions.BadResourceException;
import com.candan.interfaces.Max3003Repository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class Max3003Service {
    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private Max3003Repository max3003Repository;

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
            return max3003Repository.save(max3003);
        } catch (Exception ex) {
            logger.error("Exception on", ex);
            return null;
        }
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
