package com.candan.interfaces;

import com.candan.mongo.web.Website;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface WebsiteRepository extends MongoRepository<Website, String> {
    public List<Website> findByDate(Date date);
    public List<Website> findWebSiteByUserName(String userName);
}
