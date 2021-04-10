package com.candan.interfaces;

import com.candan.mongo.swb.Max30102;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface Max30102Repository extends MongoRepository<Max30102, String> {
    public List<Max30102> findByPersonName(String personName, String personSurname);

    public List<Max30102> findByStatus(String status);

    public List<Max30102> findByDateBetween(Date from, Date to);

    public void deleteByPersonNameAndPersonSurname(String personName, String personSurname);

    public void deleteByDate(Date date);
}