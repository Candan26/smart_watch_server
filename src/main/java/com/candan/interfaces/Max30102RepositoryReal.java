package com.candan.interfaces;

import com.candan.mongo.swb.Max30102;
import com.candan.mongo.swb.Max30102Real;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface Max30102RepositoryReal  extends MongoRepository<Max30102Real, String> {
    List<Max30102> findByPersonName(String name, String surname);
    List<Max30102> findByDateBetweenAndPersonNameAndPersonSurname(Date from , Date to, String personName, String personSurname);
}
