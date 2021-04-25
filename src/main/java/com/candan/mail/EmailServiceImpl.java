package com.candan.mail;

import com.candan.configuration.ConfigurationReader;
import com.candan.mongo.web.Website;
import com.candan.services.MessageService;
import com.candan.services.WebsiteService;
import com.candan.utils.message.Message;
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
import java.util.ArrayList;
import java.util.List;

@Component
public class EmailServiceImpl implements EmailService, Runnable {

    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    public WebsiteService websiteService;

    @Autowired
    public MessageService messageService;

    @Autowired
    private ConfigurationReader.MyConfig config;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    static boolean messageAvailable = false;
    private List<Message> messageList;

    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            emailSender.send(message);
        } catch (MailException e) {
            logger.error("Exception on ", e);
        }
    }

    @Override
    public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) {
        try {
            logger.info("Sending email to " + to + " with subject " + subject);
            MimeMessage message = emailSender.createMimeMessage();
            // pass 'true' to the constructor to create a multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            helper.addAttachment(new FileSystemResource(pathToAttachment + "/skin.xlsx").getFilename(), new FileSystemResource(pathToAttachment + "/skin.xlsx"));
            helper.addAttachment(new FileSystemResource(pathToAttachment + "/max3003.xlsx").getFilename(), new FileSystemResource(pathToAttachment + "/max3003.xlsx"));
            helper.addAttachment(new FileSystemResource(pathToAttachment + "/max30102.xlsx").getFilename(), new FileSystemResource(pathToAttachment + "/max30102.xlsx"));
            helper.addAttachment(new FileSystemResource(pathToAttachment + "/Si7021.xlsx").getFilename(), new FileSystemResource(pathToAttachment + "/Si7021.xlsx"));
            emailSender.send(message);
            logger.info("Email send successfully");
        } catch (MessagingException e) {
            logger.error("Exception on ", e);
        }
    }

    @Override
    public void checkUserMessageAndSendMail() {
        try {
            messageList = findMessages();
            if(messageList.size()<config.getWebsiteMaxMailCount()){
                return;
            }
            adjustMessageToMail(messageList);
            UpdateUser(messageList);

        } catch (Exception ex) {
            logger.error("Exception on ", ex);
        }

    }

    private void UpdateUser(List<Message> messageList) {
        for(Message m : messageList){
            messageService.deleteById(m.getId());
        }
        Website user = websiteService.findListByUserName("candan");
        user.setReceivedMessageIdList(new ArrayList<>());
        websiteService.updatePeople(user);
    }

    private void adjustMessageToMail(List<Message> messageList) throws MessagingException {
        String to = "cagricandan91@gmail.com";
        String subject = "user message from candan cv website";
        StringBuilder sb = new StringBuilder();
        if (messageAvailable) {
            logger.info("Sending email to " + to + " with subject " + subject);
            MimeMessage message = emailSender.createMimeMessage();
            // pass 'true' to the constructor to create a multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            parseString(sb,messageList);
            helper.setText(sb.toString());
            emailSender.send(message);
            messageAvailable = false;
        } else {
            sb.delete(0, sb.length());
        }
    }

    private void parseString(StringBuilder sb, List<Message> messageList) {
        for(Message m : messageList){
            sb.append(m.getDateString());
            sb.append("\n");
            sb.append(m.getReceivedMessage());
            sb.append("\n");
            sb.append("\n");
        }
    }

    private List<Message> findMessages() {
        Website user = websiteService.findListByUserName("candan");
        List<Message> receivedMessage = new ArrayList<>();

        if (user != null) {
            for ( String m : user.getReceivedMessageIdList()){
                Message message = messageService.findById(m);
                receivedMessage.add(message);
                messageAvailable = true;
            }
        }
        return receivedMessage;
    }

    @Override
    public void run() {
        boolean b = true;
        while (b) {
            try {
                logger.info("Checking user messages for email");
                Thread.sleep((config.getMailThreadTimeInSec() * 1000));
                checkUserMessageAndSendMail();
            } catch (InterruptedException ex) {
                logger.error("Exception on ", ex);
                b=false;
            }
        }
    }
}
