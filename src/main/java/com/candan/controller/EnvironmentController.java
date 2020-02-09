package com.candan.controller;

import java.net.URI;

import com.candan.db.Contact;
import com.candan.db.Environment;
import com.candan.exceptions.BadResourceException;
import com.candan.exceptions.ResourceAlreadyExistsException;
import com.candan.services.EnvironmentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.*;
import java.net.URISyntaxException;
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
    public ResponseEntity<List<Environment>> findAll(
            @RequestParam(value="page", defaultValue="1") int pageNumber,
            @RequestParam(required=false) String type) {
        logger.info("Trying to find  all skin elements by page number ["+ pageNumber+"] and type ["+type+"]");
        if (StringUtils.isEmpty(type)) {
            return ResponseEntity.ok(environmentService.findAll(pageNumber,ROW_PER_PAGE));
        }
        else {
            return ResponseEntity.ok(environmentService.findAllByType(type, pageNumber, ROW_PER_PAGE));
        }
    }

    @GetMapping(value = "/environment/{environmentId}",produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Environment> findContactById(@PathVariable long environmentId) {
        try {
            logger.info("Finding skin environment by id ["+environmentId+"]");
            Environment sensor = environmentService.findById(environmentId);
            return ResponseEntity.ok(sensor);  // return 200, with json body
        } catch (Exception ex) {
            logger.error("Exception on ",ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }

    @PostMapping(value = "/environment")
    public  ResponseEntity<Environment> addEnvironmentSensor(@Valid @RequestBody Environment environmentSensor) throws URISyntaxException{
        try {
            logger.info("Adding new skin contact value ["+environmentSensor.toString()+"]");
            Environment newEnvironmentSensor = environmentService.save(environmentSensor);
            return  ResponseEntity.created(new URI("/api/environment/"+newEnvironmentSensor.getId_environment())).body(environmentSensor);
        }catch (ResourceAlreadyExistsException ex){
            logger.error("Exception on ",ex);
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }catch (BadResourceException ex){
            logger.error("Exception on ",ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(value = "/environment/{environmentId}")
    public ResponseEntity<Contact> updateEnvironmentSensor(@Valid @RequestBody Environment environment,
                                                 @PathVariable long environmentId) {

        try {
            logger.info("updating environment with id ["+environmentId+"] "+environment.toString());
            environment.setId_environment(environmentId);
            environmentService.update(environment);
            return ResponseEntity.ok().build();
        }catch (BadResourceException ex){
            logger.error("Exception on ",ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (Exception ex){
            logger.error("Exception on ",ex);
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping(path="/environment/{environmentId}")
    public ResponseEntity<Void> deleteContactById(@PathVariable long environmentId) {
        logger.info("Deleting contactId by ["+environmentId+"]");
        environmentService.deleteById(environmentId);
        return ResponseEntity.ok().build();
    }

}
