package com.candan.interfaces;

import com.candan.mongo.swb.Si7021;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface Si7021Repository extends MongoRepository<Si7021, String> {
    public List<Si7021> findByPersonName(String personName, String personSurname);

    public List<Si7021> findByStatus(String status);

    public List<Si7021> findByDateBetween(Date from, Date to);

    public void deleteByPersonNameAndPersonSurname(String personName, String personSurname);

    public void deleteByDate(Date date);
}