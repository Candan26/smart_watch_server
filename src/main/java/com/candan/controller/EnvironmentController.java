package com.candan.controller;


import com.candan.db.Contact;
import com.candan.db.Environment;
import com.candan.services.EnvironmentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EnvironmentController {

    private final Logger logger = Logger.getLogger(this.getClass());

    private final int ROW_PER_PAGE =5;

    @Autowired
    private EnvironmentService environmentService;

    @GetMapping(value = "/environment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> findAll(
            @RequestParam(value="page", defaultValue="1") int pageNumber,
            @RequestParam(required=false) String name) {
        logger.info("Trying to find  all skin elements by page number ["+ pageNumber+"] and name ["+name+"]");
        List<String >tstList= new ArrayList<String>();
        if (StringUtils.isEmpty(name)) {
            tstList.add("environmentService.findAll(pageNumber, ROW_PER_PAGE)");
            return ResponseEntity.ok(tstList);
        }
        else {
            tstList.add("testCandanLALALAL");
            return ResponseEntity.ok(tstList);
        }
    }

}
