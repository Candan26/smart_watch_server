package com.candan.controller;

import com.candan.configuration.ConfigurationReader;
import com.candan.cvs.ExportExcel;
import com.candan.db.Environment;
import com.candan.db.Heart;
import com.candan.db.Skin;
import com.candan.db.UserInfo;
import com.candan.services.EnvironmentService;
import com.candan.services.HeartService;
import com.candan.services.SkinService;
import com.candan.services.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserInfoController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ConfigurationReader.MyConfig config;

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping (value = "/userInfo",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserInfo>> findAll(
            @RequestParam(value = "page",defaultValue = "1") int pageNumber,
            @RequestParam(required = false) String name){
        logger.info("Trying to find  all userInfo elements by page number ["+ pageNumber+"] and name ["+name+"]");
        if (StringUtils.isEmpty(name)){
            return ResponseEntity.ok(userInfoService.findAll(pageNumber,config.getRowPerPageUserInfo().intValue()));
        }else {
            return ResponseEntity.ok(userInfoService.findAllByName(name, pageNumber, config.getRowPerPageUserInfo().intValue()));
        }
    }

    @GetMapping(value = "/userInfo/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<UserInfo> findUserInfoById(@PathVariable long userId){
        try {
            logger.info("Finding skinId by id ["+userId+"]");
            UserInfo user = userInfoService.findById(userId); // return 200
            return ResponseEntity.ok(user);
        }catch (Exception ex){
            logger.error("Exception on ",ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); //return 404 without buddy
        }
    }

    @PostMapping(value = "/userInfo")
    public  ResponseEntity<UserInfo> addUserInfo(@Valid @RequestBody UserInfo userInfo){
        try {
            logger.info("Adding new userInfo value ["+userInfo.toString()+"]");
            UserInfo newUserInfo = userInfoService.save(userInfo);
            return ResponseEntity.created(new URI("/api/userInfo/"+newUserInfo.getId())).body(userInfo);
        }catch (Exception ex){
            logger.error("Exception on ",ex);
            //todo check exception type
            //todo check post mapping, put mapping
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }



    @PutMapping(value = "userInfo/{userInfoId}")
    public ResponseEntity<UserInfo> updateUserInfo( @Valid @RequestBody UserInfo userInfo,
                                                    @PathVariable long userInfoId){
        logger.info("updating user info with id ["+userInfoId+"] "+userInfo.toString());
        try {
            userInfo.setId(userInfoId);
            userInfoService.update(userInfo);
            return ResponseEntity.ok().build();
        }catch (Exception ex){
            logger.error("Exception on ",ex);
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/userInfo/{userInfoId}")
    public ResponseEntity<Void> updateAttribute(@PathVariable long userInfoId,
                                                @RequestBody String name,
                                                @RequestBody String surName,
                                                @RequestBody String email,
                                                @RequestBody Long age,
                                                @RequestBody Long weight,
                                                @RequestBody Long height){
        try {
            logger.info("Updating attributes userInfoId["+userInfoId+"] name["+name+"] surName["+surName+
                    "] age["+age+"] weight["+weight+"] height["+height+"]");
            userInfoService.updateParams(userInfoId,name,surName,age,weight,height,email);
            return ResponseEntity.ok().build();
        }catch (Exception ex){
            logger.error("Exception on ",ex);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/userInfo/{userInfoId}")
    public ResponseEntity<Void> deleteContactId(@PathVariable Long userInfoId){
        logger.info("Deleting userInfoId by ["+userInfoId+"]");
        userInfoService.deleteById(userInfoId);
        return ResponseEntity.ok().build();
    }
}
