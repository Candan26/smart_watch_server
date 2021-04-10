package com.candan.services;


import com.candan.mongo.swb.UserInfo;
import com.candan.interfaces.UserInfoRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserInfoService {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private UserInfoRepository userInfoRepository;

    public void update(UserInfo userInfo) {
        try {
            UserInfo tmpObj = userInfoRepository.findById(userInfo.getId()).get();
            getClonedData(tmpObj, userInfo);
            userInfoRepository.save(tmpObj);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
        }
    }

    public void updateByName(UserInfo userInfo) {
        try {
            UserInfo tmpObj = userInfoRepository.findByName(userInfo.getName());
            getClonedData(tmpObj, userInfo);
            userInfoRepository.save(tmpObj);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
        }
    }

    private void getClonedData(UserInfo tmpObj, UserInfo userInfo) {
        tmpObj.setEmail(userInfo.getEmail());
        tmpObj.setName(userInfo.getName());
        tmpObj.setSurname(userInfo.getSurname());
        tmpObj.setAge(userInfo.getAge());
        tmpObj.setHeight(userInfo.getHeight());
        tmpObj.setWeight(userInfo.getWeight());
    }

    public UserInfo save(UserInfo userInfo) {
        try {
            return userInfoRepository.save(userInfo);
        } catch (Exception ex) {
            logger.error("Exception on", ex);
            return null;
        }
    }

    public UserInfo findListByName(String name) {
        try {
            return userInfoRepository.findByName(name);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return null;
        }
    }

    public List<UserInfo> findListByDate(Date date) {
        try {
            return userInfoRepository.findByDate(date);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return new ArrayList<>();
        }
    }

    public List<UserInfo> findByDate(Date date) {
        try {
            return userInfoRepository.findByDate(date);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return null;
        }
    }

    public void updateParams(String userInfoId, String name, String surName, Long age, Long weight, Long height, String email) {
        try {
            UserInfo userInfo = findById(userInfoId);
            userInfo.setName(name);
            userInfo.setSurname(surName);
            userInfo.setAge(age);
            userInfo.setWeight(weight);
            userInfo.setHeight(height);
            userInfo.setEmail(email);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
        }
    }

    public void deleteById(String id) {
        try {
            userInfoRepository.deleteById(id);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
        }
    }

    public UserInfo findById(String id) {
        try {
            return userInfoRepository.findById(id).get();
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return null;
        }
    }

    public List<UserInfo> findAll(int maxVal) {
        try {
            Pageable pageableRequest = PageRequest.of(0, maxVal);
            Page<UserInfo> page = userInfoRepository.findAll(pageableRequest);
            return page.getContent();
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return null;
        }
    }
}