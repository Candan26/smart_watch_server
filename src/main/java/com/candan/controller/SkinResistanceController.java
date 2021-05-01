package com.candan.controller;

//TODO Add candan server mvc

import com.candan.configuration.ConfigurationReader;
import com.candan.mongo.swb.SkinResistance;
import com.candan.exceptions.BadResourceException;
import com.candan.services.SkinResistanceService;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api")
public class SkinResistanceController {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    ConfigurationReader.MyConfig config;

    @Autowired
    private SkinResistanceService skinResistanceService;

    @GetMapping(value = "/skin/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SkinResistance> testSkin() {
        try {
            System.out.println("Testing all functions");
            SkinResistance sr = new SkinResistance("relax","12345","cagri","candan",new Date(1234L));
            System.out.println("Testing Creating new sr object: " + sr.toString());
            System.out.println("Saving sr values");

            skinResistanceService.save(sr);
            String id = sr.getId();
            SkinResistance ts = skinResistanceService.findById(id);
            System.out.println("Getting sr by id: " +  ts.toString());
            System.out.println("Getting sr by name surname: " +  skinResistanceService.findListByNameSurname(sr.getPersonName(), sr.getPersonSurname()).toString());
            System.out.println("Getting sr by date:  "+         skinResistanceService.findListByDate(new Date(1233L), new Date(1236L)).toString());
            System.out.println("Getting sr by status "+  skinResistanceService.findListByStatus("relax").toString());
            System.out.println("Getting sr by max value: ");
            skinResistanceService.findListByMaxNumber(2).forEach(System.out::println);
            System.out.println("updating sr by status");
            sr.setStatus("deleting");
            skinResistanceService.update(sr);
            System.out.println("Deleting sr by nameSurname");
            List<SkinResistance> srList= new ArrayList<>();
            srList.add(sr);
            skinResistanceService.deleteByNameSurname(srList);
            skinResistanceService.save(sr);
            System.out.println("Deleting sr by date");
            skinResistanceService.deleteByDate(new Date(1234L));

            return ResponseEntity.ok(sr);  // return 200, with json body
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }

    @GetMapping(value = "/skin/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SkinResistance> findContactById(@PathVariable String id) {
        try {
            logger.info("Finding Si7021 by id [" + id + "]");
            SkinResistance sensor = skinResistanceService.findById(id);
            return ResponseEntity.ok(sensor);  // return 200, with json body
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }

    @GetMapping(value = "/skin/date", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SkinResistance>> findByDate(
            @RequestParam(value = "date_from", defaultValue = "") Long from,
            @RequestParam(value = "date_to", defaultValue = "") Long to) {
        logger.info("Trying to find  all skin elements from date [" + new java.util.Date(from) + "] to [" + new java.util.Date(to)+ "]");
        try {
            return ResponseEntity.ok(skinResistanceService.findListByDate(new java.util.Date(from), new java.util.Date(to)));
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }

    @GetMapping(value = "/skin/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SkinResistance>> findByStatus(
            @RequestParam(value = "status", defaultValue = "") String status) {
        logger.info("Trying to find  all SkinResistance elements status [" + status + "]");
        try {
            return ResponseEntity.ok(skinResistanceService.findListByStatus(status));
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }

    @GetMapping(value = "/skin/findByNameSurname", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SkinResistance>> findByNameSurname(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "surname", defaultValue = "") String surname) {
        logger.info("Trying to find  all skin elements name [" + name + "]and surname [" + surname + "]");
        try {
            return ResponseEntity.ok(skinResistanceService.findListByNameSurname(name, surname));
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }

    @GetMapping(value = "/skin/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SkinResistance>> findAllWithMaxNo(
            @RequestParam(value = "max", defaultValue = "1") int maxVal) {
        logger.info("Trying to find  all skin elements by page number [" + maxVal + "]");
        try {
            return ResponseEntity.ok(skinResistanceService.findListByMaxNumber(maxVal));
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }

    @GetMapping(value = "/skin/getQueue", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SkinResistance> getElementFromQueue() {
        logger.info("The size od queue [" + skinResistanceService.lbq.size() + "]");
        try {
            return ResponseEntity.ok( skinResistanceService.lbq.poll());
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }

    @PostMapping(value = "/skin/queue")
    public ResponseEntity<SkinResistance> addmax30102RabbitSensor(@Valid @RequestBody SkinResistance skinSensor) throws URISyntaxException {
        try {
            logger.info("Adding new skin contact value [" + skinSensor.toString() + "]");
            skinResistanceService.lbq.add(skinSensor);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(value = "/skin")
    public ResponseEntity<SkinResistance> addSkinResistanceSensor(@Valid @RequestBody SkinResistance skinResistance) throws URISyntaxException {
        try {
            logger.info("Adding new skin contact value [" + skinResistance.toString() + "]");
            SkinResistance newSkinResistance = skinResistanceService.save(skinResistance);
            return ResponseEntity.created(new URI("/api/skin/" + newSkinResistance.getId())).body(skinResistance);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(value = "/skin")
    public ResponseEntity<SkinResistance> updateMax3003Sensor(@Valid @RequestBody SkinResistance skinResistance) {
        try {
            logger.info("updating skin with id [" + skinResistance.getId() + "] " + skinResistance.toString());
            skinResistanceService.update(skinResistance);
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
    @DeleteMapping(path = "/skin/date/{skin}")
    public ResponseEntity<Void> deleteContactByDate(@PathVariable Date date) {
        logger.info("Deleting contactId by [" + date + "]");
        skinResistanceService.deleteByDate(date);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/skin/id/{skin}")
    public ResponseEntity<Void> deleteContactById(@PathVariable String id) {
        logger.info("Deleting contactId by [" + id + "]");
        List<String> tmpList = new ArrayList<>();
        tmpList.add(id);
        skinResistanceService.deleteById(tmpList);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/skin/ids/{skin}")
    public ResponseEntity<Void> deleteContactByIds(@PathVariable List<String> ids) {
        logger.info("Deleting contactId by [" + ids + "]");
        skinResistanceService.deleteById(ids);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/skin/name_surname/{names}&{surnames}")
    public ResponseEntity<Void> deleteContactByIds(@PathVariable List<String> names,
                                                   @PathVariable List<String> surnames) {
        logger.info("Deleting contactId by [" + names + "] and  [" + surnames + "]");
        List<SkinResistance> skinResistances = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            skinResistances.add(new SkinResistance("", "",  names.get(i), surnames.get(i), null));
        }
        skinResistanceService.deleteByNameSurname(skinResistances);
        return ResponseEntity.ok().build();
    }
}
