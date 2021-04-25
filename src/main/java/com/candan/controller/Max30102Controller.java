package com.candan.controller;

import com.candan.configuration.ConfigurationReader;
import com.candan.mongo.swb.Max30102;
import com.candan.exceptions.BadResourceException;
import com.candan.services.Max30102Service;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class Max30102Controller {
    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    ConfigurationReader.MyConfig config;

    @Autowired
    private Max30102Service max30102Service;

    @GetMapping(value = "/max30102/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Max30102> findContactById(@PathVariable String id) {
        try {
            logger.info("Finding max30102 by id [" + id + "]");
            Max30102 sensor = max30102Service.findById(id);
            return ResponseEntity.ok(sensor);  // return 200, with json body
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }

    @GetMapping(value = "/max30102/date", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Max30102>> findByDate(
            @RequestParam(value = "date_from", defaultValue = "") Long from,
            @RequestParam(value = "date_to", defaultValue = "") Long to) {
        logger.info("Trying to find  all max30102 elements from date [" + new Date(from) + "] to [" + new Date(to) + "]");
        try {
            return ResponseEntity.ok(max30102Service.findListByDate(new Date(from) , new Date(to)));
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }

    @GetMapping(value = "/max30102/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Max30102>> findByStatus(
            @RequestParam(value = "status", defaultValue = "") String status) {
        logger.info("Trying to find  all max30102 elements status [" + status + "]");
        try {
            return ResponseEntity.ok(max30102Service.findListByStatus(status));
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }

    @GetMapping(value = "/max30102/findByNameSurname", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Max30102>> findByNameSurname(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "surname", defaultValue = "") String surname
    ) {
        logger.info("Trying to find  all Max30102 elements name [" + name + "]and surname [" + surname + "]");
        try {
            return ResponseEntity.ok(max30102Service.findListByNameSurname(name, surname));

        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }

    @GetMapping(value = "/max30102/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Max30102>> findAllWithMaxNo(
            @RequestParam(value = "max", defaultValue = "1") int maxVal) {
        logger.info("Trying to find  all max30102 elements by page number [" + maxVal + "]");
        try {
            return ResponseEntity.ok(max30102Service.findListByMaxNumber(maxVal));
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }

    @PostMapping(value = "/max30102")
    public ResponseEntity<Max30102> addEnvironmentSensor(@Valid @RequestBody Max30102 max30102Sensor) throws URISyntaxException {
        try {
            logger.info("Adding new max30102 contact value [" + max30102Sensor.toString() + "]");
            Max30102 newMax30102Sensor = max30102Service.save(max30102Sensor);
            return ResponseEntity.created(new URI("/api/max30102/" + newMax30102Sensor.getId())).body(max30102Sensor);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(value = "/max30102")
    public ResponseEntity<Max30102> updateMax3003Sensor(@Valid @RequestBody Max30102 max30102) {
        try {
            logger.info("updating max3003 with id [" + max30102.getId() + "] " + max30102.toString());
            max30102Service.update(max30102);
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
    @DeleteMapping(path = "/max30102/date/{max30102}")
    public ResponseEntity<Void> deleteContactByDate(@PathVariable Date date) {
        logger.info("Deleting contactId by [" + date + "]");
        max30102Service.deleteByDate(date);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/max30102/id/{max30102}")
    public ResponseEntity<Void> deleteContactById(@PathVariable String id) {
        logger.info("Deleting contactId by [" + id + "]");
        List<String> tmpList = new ArrayList<>();
        tmpList.add(id);
        max30102Service.deleteById(tmpList);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/max30102/ids/{max30102}")
    public ResponseEntity<Void> deleteContactByIds(@PathVariable List<String> ids) {
        logger.info("Deleting contactId by [" + ids + "]");
        max30102Service.deleteById(ids);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/max30102/name_surname/{names}&{surnames}")
    public ResponseEntity<Void> deleteContactByIds(@PathVariable List<String> names,
                                                   @PathVariable List<String> surnames) {
        logger.info("Deleting contactId by [" + names + "] and  [" + surnames + "]");
        List<Max30102> max30102s = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            max30102s.add(new Max30102("", "", "", "", names.get(i), surnames.get(i), null));
        }
        max30102Service.deleteByNameSurname(max30102s);
        return ResponseEntity.ok().build();
    }
}
