package com.candan.interfaces;

import com.candan.mongo.swb.Max3003;
import com.candan.mongo.swb.Max3003Real;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface Max3003RepositoryReal extends MongoRepository<Max3003Real, String> {
    List<Max3003> findByPersonName(String personName, String personSurname);
    List<Max3003> findByDateBetweenAndPersonNameAndPersonSurname(Date from , Date to, String personName, String personSurname);
    List<Max3003> findByDateBetween(Date from, Date to);
}
