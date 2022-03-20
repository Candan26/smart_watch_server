package com.candan.mail;

import com.candan.configuration.ConfigurationReader;
import com.candan.cvs.ExportExcel;
import com.candan.mongo.swb.*;
import com.candan.services.Max3003Service;
import com.candan.services.Max30102Service;
import com.candan.services.Si7021Service;
import com.candan.services.SkinResistanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Properties;

@RestController
@RequestMapping("/api")
public class MailController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SkinResistanceService skinResistanceService;

    @Autowired
    private Max3003Service max3003Service;

    @Autowired
    private Max30102Service max30102Service;

    @Autowired
    private Si7021Service si7021Service;

    @Autowired
    private ConfigurationReader.MyConfig config;

    @Autowired
    private ExportExcel exportExcel;

    @Autowired
    private EmailServiceImpl emailService;

    private String mailFromServerText = "This mail for sending raw data's of smart watch server database " +
            "for more information please check the excel files in attachment";
    private String mailFromServerSubject = "Server Data ";

    @PostMapping(value = "/email")
    public ResponseEntity<UserInfo> SendEmailToUser(@Valid @RequestBody EmailBody body) {
        List<Max3003> max3003List = max3003Service.findListByMaxNumber(body.getSkinResistanceMaxNumber());
        List<Max30102> max30102List = max30102Service.findListByMaxNumber(body.getMax30102MaxNumber());
        List<Si7021> si7021List = si7021Service.findListByMaxNumber(body.getMaxSi7021MaxNumber());
        List<SkinResistance> skinResistanceList = skinResistanceService.findListByMaxNumber(body.getSkinResistanceMaxNumber());

        logger.info("getting Database values and saving them on excel file");
        exportExcel.WriteDataToExcelFile(max3003List, max30102List, si7021List,skinResistanceList);
        emailService.sendMessageWithAttachment(body.getAddress(), mailFromServerSubject, mailFromServerText, config.getExcelPath());
        return ResponseEntity.ok(null);
    }

    @GetMapping(value = "/email/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmailSensorDTO> findAll(
            @RequestParam(value = "name", defaultValue = "") String   name,
            @RequestParam(value = "surname", defaultValue = "") String surname,
            @RequestParam(value = "email_address", defaultValue = "") String  email_address) {
        try {
            logger.info("name " + name + " surname " + surname + " email_address "+ email_address);
            // call all service data and send mail
            List<Max3003> dataMax3003 = max3003Service.findListByNameSurnameFromReal(name, surname);
            List<Max30102> dataMax300102 = max30102Service.findListByNameSurnameFromReal(name, surname);
            List<SkinResistance> dataSR = skinResistanceService.findListByNameSurnameFromReal(name, surname);
            List<Si7021> dataSi7021 = si7021Service.findListByNameSurnameFromReal(name, surname);
            exportExcel.WriteDataToExcelFile(dataMax3003, dataMax300102, dataSi7021,dataSR);
            emailService.sendMessageWithAttachment(email_address, mailFromServerSubject, mailFromServerText, config.getExcelPath());
            EmailSensorDTO emailSensorDTO = new EmailSensorDTO(dataMax3003, dataMax300102,dataSR, dataSi7021);
            return ResponseEntity.ok().body(emailSensorDTO);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }
    @GetMapping(value = "/email/byDate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmailSensorDTO> findByDate(
            @RequestParam(value = "name", defaultValue = "") String   name,
            @RequestParam(value = "surname", defaultValue = "") String surname,
            @RequestParam(value = "email_address", defaultValue = "") String  email_address,
            @RequestParam(value = "date_from", defaultValue = "") String date_from,
            @RequestParam(value = "date_to", defaultValue = "") String date_to
            ) {
        try {
            logger.info("name " + name + " surname " + surname + " email_address "+ email_address);
            // call all service data and send mail
            List<Max3003> dataMax3003 = max3003Service.findListByNameSurnameAndDateFromReal(name, surname,date_from, date_to);
            List<Max30102> dataMax300102 = max30102Service.findListByNameSurnameAndDateFromReal(name, surname,date_from, date_to);
            List<SkinResistance> dataSR = skinResistanceService.findListByNameSurnameAndDateFromReal(name, surname,date_from, date_to);
            List<Si7021> dataSi7021 = si7021Service.findListByNameSurnameAndDateFromReal(name, surname,date_from, date_to);
            exportExcel.WriteDataToExcelFile(dataMax3003, dataMax300102, dataSi7021,dataSR);
            emailService.sendMessageWithAttachment(email_address, mailFromServerSubject, mailFromServerText, config.getExcelPath());
            EmailSensorDTO emailSensorDTO = new EmailSensorDTO(dataMax3003, dataMax300102,dataSR, dataSi7021);
            return ResponseEntity.ok().body(emailSensorDTO);
        } catch (Exception ex) {
            logger.error("Exception on ", ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }

    @Lazy
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(config.getMailUserHost());
        mailSender.setPort(config.getMailUserPort());
        mailSender.setUsername(config.getMailUserName());
        mailSender.setPassword(config.getMailUserPassword());
        logger.info("Setting parameters for email");
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        return mailSender;
    }
}
