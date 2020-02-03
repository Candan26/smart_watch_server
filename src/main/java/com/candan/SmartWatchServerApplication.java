package com.candan;


import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

//TODO ad log on system
// separate package of rest web service


@SpringBootApplication
public class SmartWatchServerApplication   {
    public static void main(String[] args) {

        DOMConfigurator.configureAndWatch("./conf/log4j.xml");
        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(SmartWatchServerApplication.class)
                .properties("spring.config.name:application", "spring.config.location:./conf/").build().run(args);
    }


}
