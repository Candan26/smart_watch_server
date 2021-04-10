package com.candan.interfaces;

import com.candan.mongo.swb.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface UserInfoRepository extends MongoRepository<UserInfo, String> {
    public UserInfo findByName(String name);
    public List<UserInfo> findByDate(Date date);
}