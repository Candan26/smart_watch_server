package com.candan.mail;

import com.candan.configuration.ConfigurationReader;
import com.candan.cvs.ExportExcel;
import com.candan.db.Environment;
import com.candan.db.Heart;
import com.candan.db.Skin;
import com.candan.db.UserInfo;
import com.candan.services.EnvironmentService;
import com.candan.services.HeartService;
import com.candan.services.SkinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Properties;

@RestController
@RequestMapping("/api")
public class MailController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private HeartService heartService;

    @Autowired
    private SkinService skinService;

    @Autowired
    private EnvironmentService environmentService;

    @Autowired
    private ConfigurationReader.MyConfig config;

    @Autowired
    private ExportExcel exportExcel;

    @Autowired
    private EmailServiceImpl emailService;

    private String mailFromServerText="This mail for sending raw data's of smart watch server database " +
            "for more information please check the excel files in attachment";
    private String mailFromServerSubject = "Server Data ";

    @PostMapping(value = "/email")
    public ResponseEntity<UserInfo> SendEmailToUser(@Valid @RequestBody EmailBody body){
        List<Skin> skinList=skinService.findAll(body.getSkinPageNumber(),body.getSkinRowPerPage());
        List<Environment> environmentList = environmentService.findAll(body.getEnvPageNumber(),body.getEnvRowPerPage());
        List<Heart> heartList = heartService.findAll(body.getHeartPageNumber(),body.getHeartRowPerPage());
        logger.info("getting Database values and saving them on excel file");
        exportExcel.WriteDataToExcelFile(skinList,environmentList,heartList);
        emailService.sendMessageWithAttachment(body.getAddress(),mailFromServerSubject,mailFromServerText,config.getExcelPath());
        return  ResponseEntity.ok(null);
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
