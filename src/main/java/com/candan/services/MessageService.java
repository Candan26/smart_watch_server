package com.candan.services;

import com.candan.exceptions.BadResourceException;
import com.candan.interfaces.MessageRepository;
import com.candan.interfaces.PhotoRepository;
import com.candan.mongo.swb.SkinResistance;
import com.candan.utils.message.Message;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private MessageRepository messageRepository;

    public String save(Message skinResistance) {
        try {
            return messageRepository.save(skinResistance).getId();
        } catch (Exception ex) {
            logger.error("Exception on", ex);
            return null;
        }
    }

    public void update(Message message) throws BadResourceException, org.springframework.data.rest.webmvc.ResourceNotFoundException {
        try {
            Message tmpObj = messageRepository.findById(message.getId()).get();
            getClonedData(tmpObj, message);
            messageRepository.save(tmpObj);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
        }
    }

    private void getClonedData(Message tmpObj, Message message) {
        tmpObj.setFooter(message.getFooter());
        tmpObj.setHeader(message.getHeader());
        tmpObj.setMessageList(message.getMessageList());
    }

    public Message findById(String id) {
        try {
            return messageRepository.findById(id).get();
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return null;
        }
    }

    public void deleteById(String id) {
        try {
            messageRepository.deleteById(id);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
        }
    }

    public void deleteByIdList(List<String> ids) {
        try {
            for (String id : ids)
                messageRepository.deleteById(id);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
        }
    }
}
