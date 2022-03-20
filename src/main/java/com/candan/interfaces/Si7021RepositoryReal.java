package com.candan.interfaces;

import com.candan.mongo.swb.Si7021;
import com.candan.mongo.swb.Si7021Real;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface Si7021RepositoryReal extends MongoRepository<Si7021Real, String> {
    List<Si7021> findByPersonName(String name, String surname);
    List<Si7021> findByDateBetweenAndPersonNameAndPersonSurname(Date from , Date to, String personName, String personSurname);

}
