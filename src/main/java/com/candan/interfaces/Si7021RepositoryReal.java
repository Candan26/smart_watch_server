package com.candan.interfaces;

import com.candan.mongo.swb.Si7021;
import com.candan.mongo.swb.Si7021Real;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Si7021RepositoryReal extends MongoRepository<Si7021Real, String> {
}
