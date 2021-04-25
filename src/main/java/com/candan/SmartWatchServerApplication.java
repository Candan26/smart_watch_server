package com.candan;


import com.candan.mail.EmailServiceImpl;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;


// separate package of rest web service


@SpringBootApplication
public class SmartWatchServerApplication   {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SmartWatchServerApplication.class, args);
        applicationContext.getBean(EmailServiceImpl.class).run();
    }
}
