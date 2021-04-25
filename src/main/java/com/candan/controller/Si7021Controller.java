package com.candan.controller;

import com.candan.configuration.ConfigurationReader;
import com.candan.mongo.swb.Si7021;
import com.candan.exceptions.BadResourceException;
import com.candan.services.Si7021Service;
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
public class Si7021Controller {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    ConfigurationReader.MyConfig config;

    @Autowired
    private Si7021Service si7021Service;

    @GetMapping(value = "/si7021/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Si7021> findContactById(@PathVariable String id) {
        try {
            logger.info("Finding Si7021 by id [" + id + "]");
            Si7021 sensor = si7021Service.findById(id);
            return ResponseEntity.ok(sensor);  // return 200, with json body
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }

    @GetMapping(value = "/si7021/date", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Si7021>> findByDate(
            @RequestParam(value = "date_from", defaultValue = "") Long from,
            @RequestParam(value = "date_to", defaultValue = "") Long to) {
        logger.info("Trying to find  all si7021 elements from date [" + new Date(from) + "] to [" + new Date(to) + "]");
        try {
            return ResponseEntity.ok(si7021Service.findListByDate(new Date(from), new Date(to)));
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }

    @GetMapping(value = "/si7021/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Si7021>> findByStatus(
            @RequestParam(value = "status", defaultValue = "") String status) {
        logger.info("Trying to find  all Si7021 elements status [" + status + "]");
        try {
            return ResponseEntity.ok(si7021Service.findListByStatus(status));
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }

    @GetMapping(value = "/si7021/findByNameSurname", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Si7021>> findByNameSurname(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "surname", defaultValue = "") String surname) {
        logger.info("Trying to find  all Si7021 elements name [" + name + "]and surname [" + surname + "]");
        try {
            return ResponseEntity.ok(si7021Service.findListByNameSurname(name, surname));
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }

    @GetMapping(value = "/si7021/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Si7021>> findAllWithMaxNo(
            @RequestParam(value = "max", defaultValue = "1") int maxVal) {
        logger.info("Trying to find  all Si7021 elements by page number [" + maxVal + "]");
        try {
            return ResponseEntity.ok(si7021Service.findListByMaxNumber(maxVal));
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }

    @PostMapping(value = "/si7021")
    public ResponseEntity<Si7021> addEnvironmentSensor(@Valid @RequestBody Si7021 si7021Sensor) throws URISyntaxException {
        try {
            logger.info("Adding new Si7021 contact value [" + si7021Sensor.toString() + "]");
            Si7021 newSi7021Sensor = si7021Service.save(si7021Sensor);
            return ResponseEntity.created(new URI("/api/si7021/" + newSi7021Sensor.getId())).body(si7021Sensor);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(value = "/si7021")
    public ResponseEntity<Si7021> updateMax3003Sensor(@Valid @RequestBody Si7021 Si7021) {
        try {
            logger.info("updating si7021 with id [" + Si7021.getId() + "] " + Si7021.toString());
            si7021Service.update(Si7021);
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
    @DeleteMapping(path = "/si7021/date/{si7021}")
    public ResponseEntity<Void> deleteContactByDate(@PathVariable Date date) {
        logger.info("Deleting contactId by [" + date + "]");
        si7021Service.deleteByDate(date);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/si7021/id/{si7021}")
    public ResponseEntity<Void> deleteContactById(@PathVariable String id) {
        logger.info("Deleting contactId by [" + id + "]");
        List<String> tmpList = new ArrayList<>();
        tmpList.add(id);
        si7021Service.deleteById(tmpList);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/si7021/ids/{si7021}")
    public ResponseEntity<Void> deleteContactByIds(@PathVariable List<String> ids) {
        logger.info("Deleting contactId by [" + ids + "]");
        si7021Service.deleteById(ids);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/si7021/name_surname/{names}&{surnames}")
    public ResponseEntity<Void> deleteContactByIds(@PathVariable List<String> names,
                                                   @PathVariable List<String> surnames) {
        logger.info("Deleting contactId by [" + names + "] and  [" + surnames + "]");
        List<Si7021> Si7021s = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            Si7021s.add(new Si7021("", "", "", names.get(i), surnames.get(i), null));
        }
        si7021Service.deleteByNameSurname(Si7021s);
        return ResponseEntity.ok().build();
    }
}
