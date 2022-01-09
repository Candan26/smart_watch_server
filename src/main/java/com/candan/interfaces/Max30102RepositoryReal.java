package com.candan.interfaces;

import com.candan.mongo.swb.Max30102;
import com.candan.mongo.swb.Max30102Real;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Max30102RepositoryReal  extends MongoRepository<Max30102Real, String> {
}
