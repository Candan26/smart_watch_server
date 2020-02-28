package com.candan.services;


import com.candan.db.Environment;
import com.candan.exceptions.BadResourceException;
import com.candan.exceptions.ResourceAlreadyExistsException;
import com.candan.exceptions.ResourceNotFoundException;
import com.candan.interfaces.EnvironmentRepository;
import com.candan.specification.EnvironmentSpecification;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

//TODO id does not recognized by hibernate sequencer currently give id
@Service
public class EnvironmentService {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private EnvironmentRepository environmentRepository;

    private boolean existsById(Long id){
        return environmentRepository.existsById(id);
    }

    public Environment findById(Long id) throws ResourceNotFoundException {
        Environment environment = environmentRepository.findById(id).orElse(null);
        logger.info("Searching data for id ["+id+"] on Environment DB");
        if(environment==null) {
            throw new ResourceNotFoundException("Cannot find environment with id [" + id + "]");
        }else
            return environment;
    }

    public List<Environment> findAll(int pageNumber, int rowPerPage){
        logger.info("finding all numbers fromPAge ["+pageNumber+"] to row for page ["+rowPerPage+"] ");
        List<Environment> environments = new ArrayList<>();
        environmentRepository.findAll(PageRequest.of(pageNumber-1, rowPerPage)).forEach(environments::add);
        return environments;
    }

    public List<Environment> findAllByType(String type, int pageNumber, int rowPerPage){
        logger.info("finding All numbers from type ["+type +"] ");
        Environment filter = new Environment();
        filter.setType(type);
        Specification<Environment> spec = new EnvironmentSpecification(filter);

        List<Environment> environments = new ArrayList<Environment>();
        environmentRepository.findAll(spec, PageRequest.of(pageNumber-1, rowPerPage)).forEach(environments::add);
        return environments;
    }

    public Environment save(Environment environment) throws BadResourceException, ResourceAlreadyExistsException {
        if(!StringUtils.isEmpty(environment.getType())){
        logger.info("Trying to update environment which is ["+environment.getType()+"] ");
        if(environment.getType() !=null && existsById(environment.getId())){
            logger.error("Resource already exist throwing exception");
            throw new ResourceAlreadyExistsException("Environment with id: " + environment.getId() +
                    " already exists");
        }
        return environmentRepository.save(environment);
        }else{
            BadResourceException exc = new BadResourceException("Failed to save contact");
            exc.addErrorMessage("Throwing exception "+exc);
            throw exc;
        }
    }

    public void  update(Environment environment) throws BadResourceException, org.springframework.data.rest.webmvc.ResourceNotFoundException {
        if (!StringUtils.isEmpty(environment.getType())) {
            if (!existsById(environment.getId())) {
                throw new org.springframework.data.rest.webmvc.ResourceNotFoundException("Cannot find Environment with id: " + environment.getId());
            }
            logger.info("Trying to save envrionment which has ["+environment.getId()+"] id name");
            environmentRepository.save(environment);
        }
        else {
            BadResourceException exc = new BadResourceException("Failed to save environment");
            exc.addErrorMessage("Contact is null or empty");
            logger.error("Throwing exception "+ exc);
            throw exc;
        } 
    }


    public void deleteById(Long id) throws org.springframework.data.rest.webmvc.ResourceNotFoundException {
        if (!existsById(id)) {
            throw new org.springframework.data.rest.webmvc.ResourceNotFoundException("Cannot find environment with id: " + id);
        }
        else {
            logger.info("Deleting environment name which has ["+id+"] id");
            environmentRepository.deleteById(id);
        }
    }

    public Long count() {
        logger.info("counting numbers in count table  ["+environmentRepository.count() +"] ");
        return environmentRepository.count();
    }

}

