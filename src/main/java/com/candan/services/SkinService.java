package com.candan.services;

import com.candan.db.Skin;
import com.candan.exceptions.ResourceAlreadyExistsException;
import com.candan.interfaces.SkinRepository;
import com.candan.specification.SkinSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class SkinService {


    @Autowired
    private SkinRepository skinRepository;

    private boolean existsById(Long id) {
        return skinRepository.existsById(id);
    }

    public Skin findById(long skinId) {
        Skin skinSensorInfo = skinRepository.findById(skinId).orElse(null);
        if(skinSensorInfo == null){
            throw new ResourceNotFoundException("Cannot find Contact with id: " + skinId);
        }else
            return skinSensorInfo;
    }

    public List<Skin> findAll(int pageNumber , int rowPerPage){
        List<Skin> skinInfo = new ArrayList<>();
        skinRepository.findAll(PageRequest.of(pageNumber - 1, rowPerPage)).forEach(skinInfo::add);
        return skinInfo;
    }

    public List<Skin> findAllByType(String type, int pageNumber, int row_per_page) {
        Skin filter =  new Skin();
        filter.setType(type);
        Specification<Skin> spec = new SkinSpecification(filter); // TODO analyze this line
        List<Skin> skinList = new ArrayList<>();
        skinRepository.findAll(spec,PageRequest.of(pageNumber-1, row_per_page)).forEach(skinList::add);
        return  skinList;
    }

    public Skin save(Skin skinSensor) throws ResourceAlreadyExistsException {
        if (skinSensor.getId() != null && existsById(skinSensor.getId())) {
            throw new ResourceAlreadyExistsException("Contact with id : [" + skinSensor.getId() +
                    "] already exists"); //TODO correct log logic in each class
        }
     return skinRepository.save(skinSensor);//TODO check this logic for each class some of them does not need checking
        //TODO add usernameLogic to each table
    }

    public void update(Skin skinSensor) {
    if(!existsById(skinSensor.getId())){
        throw    new ResourceNotFoundException("cannot find with id ["+skinSensor.getId()+"]");
    }
        skinRepository.save(skinSensor);
    }

    public void updateAttributes(long skinId, String type, String data, Date date) {
        Skin skinSensor = findById(skinId);
        skinSensor.setType(type);
        skinSensor.setData(data);
        skinSensor.setDate(date);
        skinRepository.save(skinSensor);
    }



    public void deleteById(long skinId) {
        if (!existsById(skinId)) {
            throw new ResourceNotFoundException("Cannot find skin sensor with id: " + skinId);
        }
        else {
            skinRepository.deleteById(skinId);
        }
    }

    public Long count() {
        return skinRepository.count();
    }
}
