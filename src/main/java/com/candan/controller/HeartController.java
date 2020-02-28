package com.candan.controller;


import com.candan.configuration.ConfigurationReader;
import com.candan.db.Heart;
import com.candan.exceptions.BadResourceException;
import com.candan.exceptions.ResourceAlreadyExistsException;
import com.candan.services.HeartService;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class HeartController {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    ConfigurationReader.MyConfig config;

    @Autowired
    private HeartService heartService;

    @GetMapping(value = "/heart",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Heart>> findAll(
            @RequestParam(value="page", defaultValue="1") int pageNumber,
            @RequestParam(required=false) String type) {
        logger.info("Trying to find  all skin elements by page number ["+ pageNumber+"] and type ["+type+"]");
        if (StringUtils.isEmpty(type)) {
            return ResponseEntity.ok(heartService.findAll(pageNumber, config.getRowPerPageHeart().intValue()));
        }
        else {
            return ResponseEntity.ok(heartService.findAllByType(type, pageNumber, config.getRowPerPageHeart().intValue()));
        }
    }

    @GetMapping(value = "/heart/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<Heart> findContactById(@PathVariable long id){
        try {
            logger.info("Finding heart sensor by id ["+id+"]");
            Heart sensor = heartService.findById(id);
            return ResponseEntity.ok(sensor);
        }catch (Exception ex){
            logger.error("Exception on ",ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);//return 404 with null body
        }
    }

    @PostMapping(value = "/heart")
    public ResponseEntity<Heart> addHeartSensor(@Valid @RequestBody Heart heartSensor) throws URISyntaxException{
        try {
            logger.info("Adding new heart sensor value ["+heartSensor.toString()+"]");
            Heart newHeartSensor = heartService.save(heartSensor);
            return ResponseEntity.created(new URI("/api/heartSensor/"+newHeartSensor.getId())).body(heartSensor);
        }catch (ResourceAlreadyExistsException rae){
            logger.error("Exception on ",rae);
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }catch (BadResourceException bre){
            logger.error("Exception on ",bre);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(value = "/heart/{id}")
    public ResponseEntity<Heart> updateHeartSensor(@Valid @RequestBody Heart heartSensorInfo, @PathVariable long id){
        try {
            logger.info("updating heart rate sensor with id ["+id+ "] "+heartSensorInfo.toString());
            heartSensorInfo.setId(id);
            heartService.update(heartSensorInfo);
            return ResponseEntity.ok().build();
        }catch (BadResourceException ex){
            logger.error("Exception on ",ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (Exception ex){
            logger.error("Exception on ",ex);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/heart/{id}")
    public ResponseEntity<Void> deleteByHeartSensorId(@PathVariable long id){
        logger.info("Deleting heart sensor by id ["+id+"]");
        heartService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
