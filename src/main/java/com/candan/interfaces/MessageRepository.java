package com.candan.interfaces;

import com.candan.utils.img.Photo;
import com.candan.utils.message.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message, String> {
}

