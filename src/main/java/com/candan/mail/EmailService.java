package com.candan.mail;

public interface EmailService {
    void sendMessageWithAttachment(String to,
                                   String subject,
                                   String text,
                                   String pathToAttachment) ;
}
