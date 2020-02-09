package com.candan.services;

import com.candan.db.Contact;
import com.candan.db.SensorInfo;
import com.candan.exceptions.BadResourceException;
import com.candan.exceptions.ResourceAlreadyExistsException;
import com.candan.interfaces.ContactRepository;
import com.candan.interfaces.SensorInfoRepository;
import com.candan.specification.ContactSpecification;
import com.candan.specification.SensorInfoSpecification;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class SensorInfoService {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private SensorInfoRepository sensorInfoRepository;

    private boolean existsById(Long id) {
        return sensorInfoRepository.existsById(id);
    }

    public SensorInfo findById(Long id) throws ResourceNotFoundException {
        SensorInfo sensorInfo = sensorInfoRepository.findById(id).orElse(null);
        logger.info("Searching data for id["+id.toString()+"] on sensorInfo");
        if (sensorInfo==null) {
            throw new ResourceNotFoundException("Cannot find sensorInfo with id: [" + id+"]");
        }
        else return sensorInfo;
    }

    public List<SensorInfo> findAll(int pageNumber, int rowPerPage) {
        logger.info("finding All numbers fromPage ["+pageNumber +"] to row for page ["+rowPerPage+"] ");
        List<SensorInfo> sensorInfos = new ArrayList<>();
        sensorInfoRepository.findAll(PageRequest.of(pageNumber - 1, rowPerPage)).forEach(sensorInfos::add);
        return sensorInfos;
    }

    public List<SensorInfo> findAllByName(String type, int pageNumber, int rowPerPage) {
        logger.info("finding All numbers from name  ["+type +"] ");
        SensorInfo filter = new SensorInfo();
        filter.setType(type);
        Specification<SensorInfo> spec = new SensorInfoSpecification(filter);

        List<SensorInfo> sensorInfos = new ArrayList<>();
        sensorInfoRepository.findAll(spec, PageRequest.of(pageNumber - 1, rowPerPage)).forEach(sensorInfos::add);
        return sensorInfos;
    }

    public SensorInfo save(SensorInfo sensorInfo) throws BadResourceException, ResourceAlreadyExistsException {
        if (!StringUtils.isEmpty(sensorInfo.getType())) {
            logger.info("Trying to update sensorInfo which is ["+sensorInfo.getType()+"]");
            if (sensorInfo.getId_sensor() != null && existsById(sensorInfo.getId_sensor())) {
                logger.error("Resource already exist throwing exception");
                throw new ResourceAlreadyExistsException("SensorInfo with id: " + sensorInfo.getId_sensor() +
                        " already exists");
            }
            return sensorInfoRepository.save(sensorInfo);
        }
        else {
            BadResourceException exc = new BadResourceException("Failed to save sensorInfo");
            exc.addErrorMessage("Throwing exception "+exc);
            throw exc;
        }
    }

    public void update(SensorInfo sensorInfo)
            throws BadResourceException, ResourceNotFoundException {
        if (!StringUtils.isEmpty(sensorInfo.getType())) {
            if (!existsById(sensorInfo.getId_sensor())) {
                throw new ResourceNotFoundException("Cannot find sensorInfo with id: " + sensorInfo.getId_sensor());
            }
            logger.info("Trying to save sensorInfo which has ["+sensorInfo.getId_sensor()+"] id name");
            sensorInfoRepository.save(sensorInfo);
        }
        else {
            BadResourceException exc = new BadResourceException("Failed to save contact");
            exc.addErrorMessage("Contact is null or empty");
            logger.error("Throwing exception "+ exc);
            throw exc;
        }
    }
    public void deleteById(Long id) throws ResourceNotFoundException {
        if (!existsById(id)) {
            throw new ResourceNotFoundException("Cannot find sensorInfo with id: " + id);
        }
        else {
            logger.info("Deleting sensorInfo name which has ["+id+"] id");
            sensorInfoRepository.deleteById(id);
        }
    }

    public Long count() {
        logger.info("counting numbers in sensorInfo table  ["+sensorInfoRepository.count() +"] ");
        return sensorInfoRepository.count();
    }

}
