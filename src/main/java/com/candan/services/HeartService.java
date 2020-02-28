package com.candan.services;

import com.candan.db.Heart;
import com.candan.exceptions.BadResourceException;
import com.candan.exceptions.ResourceAlreadyExistsException;
import com.candan.interfaces.HeartRepository;
import com.candan.specification.HeartSensorSpecification;
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
public class HeartService {
    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private HeartRepository heartRepository;

    private boolean existsById(Long id) {
        return heartRepository.existsById(id);
    }
    public Heart findById(Long id) throws ResourceNotFoundException {
        Heart heart = heartRepository.findById(id).orElse(null);
        logger.info("Searching data for id["+id.toString()+"] on heart");
        if (heart==null) {
            throw new ResourceNotFoundException("Cannot find heart with id: [" + id+"]");
        }
        else return heart;
    }

    public List<Heart> findAll(int pageNumber, int rowPerPage) {
        logger.info("finding All numbers fromPage ["+pageNumber +"] to row for page ["+rowPerPage+"] ");
        List<Heart> hearts = new ArrayList<>();
        heartRepository.findAll(PageRequest.of(pageNumber - 1, rowPerPage)).forEach(hearts::add);
        return hearts;
    }

    public List<Heart> findAllByType(String type, int pageNumber, int rowPerPage) {
        logger.info("finding All numbers from type  ["+type +"] ");
        Heart filter = new Heart();
        filter.setType(type);
        Specification<Heart> spec = new HeartSensorSpecification(filter);

        List<Heart> hearts = new ArrayList<>();
        heartRepository.findAll(spec, PageRequest.of(pageNumber - 1, rowPerPage)).forEach(hearts::add);
        return hearts;
    }

    public Heart save(Heart heart) throws BadResourceException, ResourceAlreadyExistsException {
        if (!StringUtils.isEmpty(heart.getType())) {
            logger.info("Trying to update heart which is ["+heart.getType()+"]");
            if (heart.getId() != null && existsById(heart.getId())) {
                logger.error("Resource already exist throwing exception");
                throw new ResourceAlreadyExistsException("Heart with id: " + heart.getId() +
                        " already exists");
            }
            return heartRepository.save(heart);
        }
        else {
            BadResourceException exc = new BadResourceException("Failed to save contact");
            exc.addErrorMessage("Throwing exception "+exc);
            throw exc;
        }
    }

    public void update(Heart heart)
            throws BadResourceException, ResourceNotFoundException {
        if (!StringUtils.isEmpty(heart.getType())) {
            if (!existsById(heart.getId())) {
                throw new ResourceNotFoundException("Cannot find heart with id: " + heart.getId());
            }
            logger.info("Trying to save heart which has ["+heart.getId()+"] id name");
            heartRepository.save(heart);
        }
        else {
            BadResourceException exc = new BadResourceException("Failed to save heart");
            exc.addErrorMessage("Heart is null or empty");
            logger.error("Throwing exception "+ exc);
            throw exc;
        }
    }

    public void deleteById(Long id) throws ResourceNotFoundException {
        if (!existsById(id)) {
            throw new ResourceNotFoundException("Cannot find heart with id: " + id);
        }
        else {
            logger.info("Deleting Heart name which has ["+id+"] id");
            heartRepository.deleteById(id);
        }
    }

    public Long count() {
        logger.info("counting numbers in count table  ["+heartRepository.count() +"] ");
        return heartRepository.count();
    }
}
