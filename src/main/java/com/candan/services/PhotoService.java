package com.candan.services;

import com.candan.utils.img.Photo;
import com.candan.interfaces.PhotoRepository;
import org.apache.log4j.Logger;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class PhotoService {
    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private PhotoRepository photoRepo;

    public String addPhoto(String title, MultipartFile file) throws IOException {
        try{
            Photo photo = new Photo(title);
            photo.setImage(
                    new Binary(BsonBinarySubType.BINARY, file.getBytes()));
            photo = photoRepo.insert(photo);
            return photo.getId();
        }catch (Exception ex){
            logger.error("Exception on ", ex);
            return  null;
        }
    }

    public Photo getPhoto(String id) {
        try {
            return photoRepo.findById(id).get();
        }catch (Exception ex){
            logger.error("Exception on ", ex);
            return null;
        }
    }

    public void deleteById(List<String> ids) {
        try {
            for (String id : ids)
                photoRepo.deleteById(id);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
        }
    }

}