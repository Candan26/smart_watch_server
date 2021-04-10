package com.candan.services;

import com.candan.utils.img.Photo;
import com.candan.interfaces.WebsiteRepository;
import com.candan.mongo.web.Website;
import com.candan.utils.message.Message;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Lazy
public class WebsiteService {
    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private WebsiteRepository websiteRepository;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private MessageService messageService;

    public void updatePeople(Website website){
        try {
            Website tmpObj = websiteRepository.findById(website.getId()).get();
            getClonedData(tmpObj, website);
            websiteRepository.save(tmpObj);
        }catch (Exception ex){
            logger.error("Exception on", ex);
        }
    }

    private void getClonedData(Website tmpObj, Website website) {
        tmpObj.setDate(website.getDate());
        tmpObj.setUserName(website.getUserName());
        tmpObj.setActivePublishedMessageIdList(website.getActivePublishedMessageIdList());
        tmpObj.setReceivedMessageIdList(website.getReceivedMessageIdList());
        tmpObj.setActivePhotoIdList(website.getActivePhotoIdList());
    }

    public void addImage(String username,String title, MultipartFile file){
        try {
            Website website = websiteRepository.findWebSiteByUserName(username).get(0);
            List<String> tmpList = website.getActivePhotoIdList();
            tmpList.add(photoService.addPhoto(title,file));
            website.setActivePhotoIdList(tmpList);
            websiteRepository.save(website);
        }catch (Exception ex){
            logger.error("Exception on", ex);
        }
    }

    public List<Photo> getPhotoList(String username){
        try{
            Website website = websiteRepository.findWebSiteByUserName(username).get(0);
            List<String> photoListFromMongoDB = website.getActivePhotoIdList();
            List<Photo> photoListForReturn = new ArrayList<>();
            for(String photoId : photoListFromMongoDB){
                photoListForReturn.add(photoService.getPhoto(photoId));
            }
            return photoListForReturn;
        }catch ( Exception ex){
            logger.error("Exception on", ex);
            return null;
        }
    }

    public Website addReceivedMessageFromWebsite(String message, String username){
        try{
            Website website = websiteRepository.findWebSiteByUserName(username).get(0);
            Message messageToDb =new Message();
            messageToDb.setReceivedMessage(message);
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            Date date = new Date(System.currentTimeMillis());
            messageToDb.setDateString(formatter.format(date));
            String tempMessageId = messageService.save(messageToDb);
            if(website.getReceivedMessageIdList() == null){
                website.setReceivedMessageIdList(new ArrayList<>());
            }
            website.getReceivedMessageIdList().add(tempMessageId);
            this.updatePeople(website);
            return website;
        }catch (Exception ex){
            logger.error("Exception on", ex);
            return null;
        }

    }

    public Website save(Website ws) {
        try {
            return websiteRepository.save(ws);
        } catch (Exception ex) {
            logger.error("Exception on", ex);
            return null;
        }
    }
}
