package com.candan.services;

import com.candan.mongo.swb.Max30102;
import com.candan.exceptions.BadResourceException;
import com.candan.interfaces.Max30102Repository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class Max30102Service {
    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private Max30102Repository max30102Repository;

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
            return max30102Repository.save(max30102);
        } catch (Exception ex) {
            logger.error("Exception on", ex);
            return null;
        }
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
