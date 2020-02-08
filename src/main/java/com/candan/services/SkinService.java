package com.candan.services;

import com.candan.db.Skin;
import com.candan.interfaces.SkinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
        return null;
    }

    public Skin save(Skin skinSensor) {
        return null;
    }

    public void update(Skin skinSensor) {
    }

    public void updateAttributes(long skinId, String type, String data, Date date) {
        Skin skinSensor = findById(skinId);
        skinSensor.setType(type);
        skinSensor.setData(data);
        skinSensor.setSkin_time(date);
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
