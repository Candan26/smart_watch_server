package com.candan.services;

import com.candan.mongo.swb.Si7021;
import com.candan.exceptions.BadResourceException;
import com.candan.interfaces.Si7021Repository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class Si7021Service {
    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private Si7021Repository si7021Repository;

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
            return si7021Repository.save(si7021);
        } catch (Exception ex) {
            logger.error("Exception on", ex);
            return null;
        }
    }

    public List<Si7021> findListByNameSurname(String name, String surname) {
        try {
            return si7021Repository.findByPersonName(name, surname);
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
}
