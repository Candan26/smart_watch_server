package com.candan.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class EmailServiceImpl  implements EmailService {

    @Autowired
    public JavaMailSender emailSender;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            emailSender.send(message);
        } catch (MailException e) {
            logger.error("Exception on ",e);
        }
    }

    @Override
    public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) {
        try {
            logger.info("Sending email to "+ to +" with subject "+subject );
            MimeMessage message = emailSender.createMimeMessage();
            // pass 'true' to the constructor to create a multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            helper.addAttachment( new FileSystemResource(pathToAttachment+"/skin.xlsx").getFilename(), new FileSystemResource(pathToAttachment+"/skin.xlsx"));
            helper.addAttachment( new FileSystemResource(pathToAttachment+"/max3003.xlsx").getFilename(), new FileSystemResource(pathToAttachment+"/max3003.xlsx"));
            helper.addAttachment( new FileSystemResource(pathToAttachment+"/max30102.xlsx").getFilename(), new FileSystemResource(pathToAttachment+"/max30102.xlsx"));
            helper.addAttachment( new FileSystemResource(pathToAttachment+"/Si7021.xlsx").getFilename(), new FileSystemResource(pathToAttachment+"/Si7021.xlsx"));
            emailSender.send(message);
            logger.info("Email send successfully");
        } catch ( MessagingException e) {
            logger.error("Exception on ",e);
        }
    }
}
