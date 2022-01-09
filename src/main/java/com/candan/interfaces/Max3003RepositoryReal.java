package com.candan.interfaces;

import com.candan.mongo.swb.Max3003;
import com.candan.mongo.swb.Max3003Real;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Max3003RepositoryReal extends MongoRepository<Max3003Real, String> {
}
