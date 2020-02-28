package com.candan.controller;

import java.net.URI;
import java.net.URISyntaxException;

import com.candan.configuration.ConfigurationReader;
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

import java.util.List;


@RestController
@RequestMapping("/api")
public class EnvironmentController {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    ConfigurationReader.MyConfig config;

    @Autowired
    private EnvironmentService environmentService;

    @GetMapping(value = "/environment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Environment>> findAll(
            @RequestParam(value="page", defaultValue="1") int pageNumber,
            @RequestParam(required=false) String type) {
        logger.info("Trying to find  all environment elements by page number ["+ pageNumber+"] and type ["+type+"]");
        if (StringUtils.isEmpty(type)) {
            return ResponseEntity.ok(environmentService.findAll(pageNumber,config.getRowPerPageEnvironment().intValue()));
        }
        else {
            return ResponseEntity.ok(environmentService.findAllByType(type, pageNumber, config.getRowPerPageEnvironment().intValue()));
        }
    }

    @GetMapping(value = "/environment/{id}",produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Environment> findContactById(@PathVariable long id) {
        try {
            logger.info("Finding skin environment by id ["+id+"]");
            Environment sensor = environmentService.findById(id);
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
            return  ResponseEntity.created(new URI("/api/environment/"+newEnvironmentSensor.getId())).body(environmentSensor);
        }catch (ResourceAlreadyExistsException ex){
            logger.error("Exception on ",ex);
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }catch (BadResourceException ex){
            logger.error("Exception on ",ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(value = "/environment/{id}")
    public ResponseEntity<Contact> updateEnvironmentSensor(@Valid @RequestBody Environment environment,
                                                 @PathVariable long id) {

        try {
            logger.info("updating environment with id ["+id+"] "+environment.toString());
            environment.setId(id);
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
