package com.candan.interfaces;

import com.candan.mongo.swb.SkinResistance;
import com.candan.mongo.swb.SkinResistanceReal;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SkinResistanceRepositoryReal extends MongoRepository<SkinResistanceReal, String> {
}
