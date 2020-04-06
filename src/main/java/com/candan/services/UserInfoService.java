package com.candan.services;


import com.candan.interfaces.UserInfoRepository;
import com.candan.specification.UserInfoSpecification;
import com.candan.db.UserInfo;
import com.candan.exceptions.BadResourceException;
import com.candan.exceptions.ResourceAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    private boolean existsById(Long id) {
        return userInfoRepository.existsById(id);
    }

    public UserInfo findById(Long id) throws ResourceNotFoundException {
        UserInfo userInfo = userInfoRepository.findById(id).orElse(null);
        if (userInfo == null) {
            throw new ResourceNotFoundException("Cannot find Contact with id: " + id);
        } else return userInfo;
    }

    public List<UserInfo> findAll(int pageNumber, int rowPerPage) {
        List<UserInfo> userInfo = new ArrayList<>();
        userInfoRepository.findAll(PageRequest.of(pageNumber - 1, rowPerPage)).forEach(userInfo::add);
        return userInfo;
    }

    public List<UserInfo> findAllByName(String name, int pageNumber, int rowPerPage) {
        UserInfo filter = new UserInfo();
        filter.setName(name);
        Specification<UserInfo> spec = new UserInfoSpecification(filter);

        List<UserInfo> userInfoList = new ArrayList<>();
        userInfoRepository.findAll(spec, PageRequest.of(pageNumber - 1, rowPerPage)).forEach(userInfoList::add);
        return userInfoList;
    }

    public UserInfo save(UserInfo userInfo) throws BadResourceException, ResourceAlreadyExistsException {
        if (!StringUtils.isEmpty(userInfo.getName())) {
            if (userInfo.getId() != null && existsById(userInfo.getId())) {
                throw new ResourceAlreadyExistsException("Contact with id: " + userInfo.getId() +
                        " already exists");
            }
            return userInfoRepository.save(userInfo);
        } else {
            BadResourceException exc = new BadResourceException("Failed to save contact");
            exc.addErrorMessage("Contact is null or empty");
            throw exc;
        }
    }

    public void update(UserInfo userInfo)
            throws BadResourceException, ResourceNotFoundException {
        if (!StringUtils.isEmpty(userInfo.getName())) {
            if (!existsById(userInfo.getId())) {
                throw new ResourceNotFoundException("Cannot find Contact with id [" + userInfo.getId()+"]");//TODO correct this logic in each seq
            }
            userInfoRepository.save(userInfo);
        }
        else {
            BadResourceException exc = new BadResourceException("Failed to save contact");
            exc.addErrorMessage("Contact is null or empty");
            throw exc;
        }
    }

    public void updateParams(Long id, String name, String surName, Long age, Long weight, Long height, String email)
            throws ResourceNotFoundException {
        UserInfo userInfo = findById(id);
        userInfo.setName(name);
        userInfo.setSurname(surName);
        userInfo.setAge(age);
        userInfo.setWeight(weight);
        userInfo.setHeight(height);
        userInfo.setEmail(email);
        userInfoRepository.save(userInfo);
    }

    public void deleteById(Long id) throws ResourceNotFoundException {
        if (!existsById(id)) {
            throw new ResourceNotFoundException("Cannot find contact with id: " + id);
        }
        else {
            userInfoRepository.deleteById(id);
        }
    }

    public Long count() {
        return userInfoRepository.count();
    }
}