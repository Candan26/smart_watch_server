package com.candan.controller;

import com.candan.configuration.ConfigurationReader;
import com.candan.db.Skin;
import com.candan.services.SkinService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SkinController {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    ConfigurationReader.MyConfig config;

    @Autowired
    private SkinService skinService;

    @GetMapping(value = "/skin",produces =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Skin>> findAll(
            @RequestParam (value = "page", defaultValue = "1") int pageNumber,
            @RequestParam (required = false) String type) {
        logger.info("Trying to find  all skin elements by page number ["+ pageNumber+"] and type ["+type+"]");
        if(StringUtils.isEmpty(type)){
            return ResponseEntity.ok(skinService.findAll(pageNumber, config.getRowPerPageSkin().intValue()));
        }else{
            return  ResponseEntity.ok(skinService.findAllByType(type,pageNumber, config.getRowPerPageSkin().intValue()));
        }
    }

    @GetMapping(value = "/skin/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<Skin> findSkinById(@PathVariable long id){
        try {
            logger.info("Finding skinId by id ["+id+"]");
            Skin skinSensor = skinService.findById(id);
            return ResponseEntity.ok(skinSensor);// return 200 , with json body
        }catch (Exception ex){
            logger.error("Exception on ",ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);// return 404, with null body
        }
    }

    @PostMapping(value = "/skin")
    public  ResponseEntity<Skin> addSkinSensor(@Valid @RequestBody Skin skinSensor){
        try {
            logger.info("Adding new skin sensor value ["+skinSensor.toString()+"]");
            Skin newSkinSensor =skinService.save(skinSensor);
            return ResponseEntity.created(new URI("/api/skin/"+newSkinSensor.getId())).body(skinSensor);
        }catch (Exception ex){
            logger.error("Exception on ",ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(value ="/skin/{id}")
    public ResponseEntity<Skin> updateSkinSensor(@Valid @RequestBody Skin skinSensor,
                                                 @PathVariable long id){
        try {
            logger.info("updating skin sensor with id ["+id+"] "+skinSensor.toString());
            skinSensor.setId(id);
            skinService.update(skinSensor);
            return ResponseEntity.ok().build();
        }catch (Exception ex){
            logger.error("Exception on ",ex);
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/skin/{id}")
    public ResponseEntity<Void> updateAttributes(@PathVariable long id,
                                                 @RequestBody String type,
                                                 @RequestBody String data,
                                                 @RequestBody Date date) {
        logger.info("Updating attributes skinId["+id+"] type["+type+"] data["+data+"] date["+data.toString()+"]");
        skinService.updateAttributes(id,type,data,date);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(path="/skin/{skinId}")
    public ResponseEntity<Void> deleteContactById(@PathVariable long skinId) {
        logger.info("Deleting skinId by ["+skinId+"]");
        skinService.deleteById(skinId);
        return ResponseEntity.ok().build();
    }
}
