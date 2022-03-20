package com.candan.interfaces;

import com.candan.mongo.swb.SkinResistance;
import com.candan.mongo.swb.SkinResistanceReal;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface SkinResistanceRepositoryReal extends MongoRepository<SkinResistanceReal, String> {
    List<SkinResistance> findByPersonName(String name, String surname);

    List<SkinResistance> findByDateBetweenAndPersonNameAndPersonSurname(Date from , Date to, String personName, String personSurname);
}
