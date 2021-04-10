package com.candan.interfaces;

import com.candan.mongo.swb.Max3003;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface Max3003Repository extends MongoRepository<Max3003, String> {
    public List<Max3003> findByPersonName(String personName, String personSurname);

    public List<Max3003> findByStatus(String status);

    public List<Max3003> findByDateBetween(Date from, Date to);

    public void deleteByPersonNameAndPersonSurname(String personName, String personSurname);

    public void deleteByDate(Date date);
}
