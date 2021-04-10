package com.candan.controller;

import com.candan.configuration.ConfigurationReader;
import com.candan.mongo.swb.SkinResistance;
import com.candan.mongo.web.Website;
import com.candan.services.UserInfoService;
import com.candan.services.WebsiteService;
import com.candan.utils.img.Photo;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api")
public class WebsiteController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ConfigurationReader.MyConfig config;

    @Autowired
    private WebsiteService websiteService;

    @GetMapping(value = "/website/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Website> testSkin() {
        try{
            System.out.println("Testing all functions");
            Website ws = new Website("candan",null,null,null,new Date(1234L));
            System.out.println("Testing Creating new ws object: " + ws.toString());
            System.out.println("Saving ws values");
            websiteService.save(ws);
            Website newWebsite = websiteService.addReceivedMessageFromWebsite("test message", "candan");
            return ResponseEntity.ok(newWebsite);  // return 200, with json body
        }catch (Exception ex){
            logger.error("Exception on ", ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }

    }

    @PostMapping(value = "/website/addMessage")
    public ResponseEntity<Website> addMessageOnMongo(  @RequestParam("name") String name,
                                                       @RequestParam("message") String message) throws URISyntaxException {
        try {
            logger.info("Adding new message value [" + message + "] for user ["+name+"]");
            Website newWebsite = websiteService.addReceivedMessageFromWebsite(message, name);
            return ResponseEntity.created(new URI("/api/skin/" + newWebsite.getId())).body(newWebsite);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    //TODO Add get message for gizem api

    @PostMapping(value = "/website/addImage")
    public ResponseEntity<Website> addImageOnMongo(@Valid @RequestParam("name") String name,
                                                       @RequestParam("title") String title,
                                                       @RequestParam("image") MultipartFile image) throws URISyntaxException {
        try {
            logger.info("Adding new image value [" + title + "] for user ["+name+"]");
             websiteService.addImage(name,title,image);
            return ResponseEntity.created(new URI("/api/website/addImage/" )).body(null);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/website/addImage/{name}")
    public  ResponseEntity<List<JSONObject>> getPhoto(@PathVariable String name) throws JSONException {
        List<Photo> photoList = websiteService.getPhotoList(name);
        List<JSONObject> jsonObjects = new ArrayList<>();
        for(Photo photo : photoList){
            JSONObject e = new JSONObject();
            e.put("title", photo.getTitle());
            e.put("image", Base64.getEncoder().encodeToString(photo.getImage().getData()));
            jsonObjects.add(e);
            //m1.addAttribute("title", photo.getTitle());
            //m1.addAttribute("image", Base64.getEncoder().encodeToString(photo.getImage().getData()));
        }

        return ResponseEntity.ok(jsonObjects);
    }


}
