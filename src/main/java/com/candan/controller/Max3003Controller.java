package com.candan.controller;

import com.candan.configuration.ConfigurationReader;
import com.candan.mongo.swb.Max3003;
import com.candan.exceptions.BadResourceException;
import com.candan.services.Max3003Service;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class Max3003Controller {
    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    ConfigurationReader.MyConfig config;

    @Autowired
    private Max3003Service max3003Service;

    @GetMapping(value = "/max3003/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Max3003> findContactById(@PathVariable String id) {
        try {
            logger.info("Finding Max3003 by id [" + id + "]");
            Max3003 sensor = max3003Service.findById(id);
            return ResponseEntity.ok(sensor);  // return 200, with json body
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }

    @GetMapping(value = "/max3003/date", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Max3003>> findByDate(
            @RequestParam(value = "date_from", defaultValue = "") Long  from,
            @RequestParam(value = "date_to", defaultValue = "") Long to) {
        logger.info("Trying to find  all Max3003 elements from date ["+new Date(from)+"] to ["+ new Date(to)+"]");
        try {
            return ResponseEntity.ok(max3003Service.findListByDate(new Date(from), new Date(to)));
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }

    @GetMapping(value = "/max3003/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Max3003>> findByStatus(
            @RequestParam(value = "status", defaultValue = "") String  status) {
        logger.info("Trying to find  all Max3003 elements status ["+status+"]");
        try {
            return ResponseEntity.ok(max3003Service.findListByStatus(status));

        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }

    @GetMapping(value = "/max3003/findByNameSurname", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Max3003>> findByNameSurname(
            @RequestParam(value = "name", defaultValue = "") String  name,
            @RequestParam(value = "surname", defaultValue = "") String  surname
            ) {
        logger.info("Trying to find  all Max3003 elements name ["+name+"]and surname [" + surname + "]");
        try {
            return ResponseEntity.ok(max3003Service.findListByNameSurname(name, surname));

        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }

    @GetMapping(value = "/max3003/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Max3003>> findAllWithMaxNo(
            @RequestParam(value = "max", defaultValue = "1") int maxVal) {
        logger.info("Trying to find  all Max3003 elements by page number [" + maxVal + "]");
        try {
            return ResponseEntity.ok(max3003Service.findListByMaxNumber(maxVal));

        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }


    @PostMapping(value = "/max3003")
    public ResponseEntity<Max3003> addEnvironmentSensor(@Valid @RequestBody Max3003 max3003Sensor) throws URISyntaxException {
        try {
            logger.info("Adding new Max3003 contact value [" + max3003Sensor.toString() + "]");
            Max3003 newMax3003Sensor = max3003Service.save(max3003Sensor);
            return ResponseEntity.created(new URI("/api/max3003/" + newMax3003Sensor.getId())).body(max3003Sensor);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(value = "/max3003")
    public ResponseEntity<Max3003> updateMax3003Sensor(@Valid @RequestBody Max3003 max3003) {
        try {
            logger.info("updating max3003 with id [" + max3003.getId() + "] " + max3003.toString());
            max3003Service.update(max3003);
            return ResponseEntity.ok().build();
        } catch (BadResourceException ex) {
            logger.error("Exception on ", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return ResponseEntity.notFound().build();
        }
    }


    // Delete part
    @DeleteMapping(path = "/max3003/date/{max3003}")
    public ResponseEntity<Void> deleteContactByDate(@PathVariable Date date) {
        logger.info("Deleting contactId by [" + date + "]");
        max3003Service.deleteByDate(date);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/max3003/id/{max3003}")
    public ResponseEntity<Void> deleteContactById(@PathVariable String id) {
        logger.info("Deleting contactId by [" + id + "]");
        List<String> tmpList = new ArrayList<>();
        tmpList.add(id);
        max3003Service.deleteById(tmpList);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/max3003/ids/{max3003}")
    public ResponseEntity<Void> deleteContactByIds(@PathVariable List<String> ids) {
        logger.info("Deleting contactId by [" + ids + "]");
        max3003Service.deleteById(ids);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/max3003/name_surname/{names}&{surnames}")
    public ResponseEntity<Void> deleteContactByIds(@PathVariable List<String> names,
                                                   @PathVariable List<String> surnames) {
        logger.info("Deleting contactId by [" + names + "] and  [" + surnames + "]");

        List<Max3003> max3003s = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {

            max3003s.add(new Max3003("", "", "", names.get(i), surnames.get(i), null));
        }
        max3003Service.deleteByNameSurname(max3003s);
        return ResponseEntity.ok().build();
    }

}
