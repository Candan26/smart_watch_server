package com.candan.services;

import com.candan.mongo.swb.Si7021;
import com.candan.mongo.swb.SkinResistance;
import com.candan.exceptions.BadResourceException;
import com.candan.interfaces.SkinResistanceRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class SkinResistanceService {
    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private SkinResistanceRepository skinResistanceRepository;

    public LinkedBlockingQueue<SkinResistance> lbq = new LinkedBlockingQueue<>();

    public void update(SkinResistance skinResistance) throws BadResourceException, org.springframework.data.rest.webmvc.ResourceNotFoundException {
        try {
            SkinResistance tmpObj = skinResistanceRepository.findById(skinResistance.getId()).get();
            getClonedData(tmpObj, skinResistance);
            skinResistanceRepository.save(tmpObj);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
        }
    }

    private void getClonedData(SkinResistance tmpObj, SkinResistance skinResistance) {
        tmpObj.setDate(skinResistance.getDate());
        tmpObj.setPersonName(skinResistance.getPersonName());
        tmpObj.setStatus(skinResistance.getStatus());
        tmpObj.setSrValue(skinResistance.getSrValue());
    }

    public void deleteByDate(Date date) {
        try {
            skinResistanceRepository.deleteByDate(date);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
        }
    }

    public void deleteById(List<String> ids) {
        try {
            for (String id : ids)
                skinResistanceRepository.deleteById(id);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
        }
    }

    public void deleteByNameSurname(List<SkinResistance> skinResistances) {
        try {
            for (SkinResistance skinResistance : skinResistances)
                skinResistanceRepository.deleteByPersonNameAndPersonSurname(skinResistance.getPersonName(), skinResistance.getPersonSurname());
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
        }
    }

    public SkinResistance save(SkinResistance skinResistance) {
        try {
            return skinResistanceRepository.save(skinResistance);
        } catch (Exception ex) {
            logger.error("Exception on", ex);
            return null;
        }
    }

    public List<SkinResistance> findListByNameSurname(String name, String surname) {
        try {
            return skinResistanceRepository.findByPersonName(name, surname);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return null;
        }
    }

    public List<SkinResistance> findListByStatus(String status) {
        try {
            return skinResistanceRepository.findByStatus(status);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return null;
        }
    }

    public List<SkinResistance> findListByDate(Date from, Date to) {
        try {
            return skinResistanceRepository.findByDateBetween(from, to);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return null;
        }
    }

    public SkinResistance findById(String id) {
        try {
            return skinResistanceRepository.findById(id).get();
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return null;
        }
    }

    public List<SkinResistance> findListByMaxNumber(int maxVal) {
        try {
            Pageable pageableRequest = PageRequest.of(0, maxVal);
            Page<SkinResistance> page = skinResistanceRepository.findAll(pageableRequest);
            return page.getContent();
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return null;
        }
    }
}
