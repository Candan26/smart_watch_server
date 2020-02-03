package com.candan.services;

import com.candan.db.Skin;
import com.candan.db.UserInfo;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class SkinService {
    public void updateAttributes(long skinId, String type, String data, Date date) {
    }

    public void deleteById(long skinId) {
    }

    public void update(Skin skinSensor) {
    }

    public Skin save(Skin skinSensor) {
        return null;
    }

    public Skin findById(long skinId) {
        return null;
    }

    public List<Skin> findAllByType(String type, int pageNumber, int row_per_page) {
        return null;
    }

    public List<Skin> findAll(int pageNumber, int row_per_page) {
        return null;
    }
}
