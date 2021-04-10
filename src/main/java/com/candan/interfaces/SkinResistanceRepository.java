package com.candan.interfaces;

import com.candan.mongo.swb.SkinResistance;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface SkinResistanceRepository extends MongoRepository<SkinResistance, String> {
    public List<SkinResistance> findByPersonName(String personName, String personSurname);

    public List<SkinResistance> findByStatus(String status);

    public List<SkinResistance> findByDateBetween(Date from, Date to);

    public void deleteByPersonNameAndPersonSurname(String personName, String personSurname);

    public void deleteByDate(Date date);
}