package com.candan.configuration;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//http://localhost:8080/api/contacts?page=1
@Configuration
public class ConfigurationReader {

    @Bean
    MyConfig myBean() {
        return new MyConfig();
    }

    public static class MyConfig {

        @Value("${controller.environment.rowPerPage}")
        private Long rowPerPageEnvironment;
        @Value("${controller.heart.rowPerPage}")
        private Long rowPerPageHeart;
        @Value("${controller.sensorInfo.rowPerPage}")
        private Long rowPerPageSensorInfo;
        @Value("${controller.skin.rowPerPage}")
        private Long rowPerPageSkin;
        @Value("${controller.userInfo.rowPerPage}")
        private Long rowPerPageUserInfo;

        public Long getRowPerPageEnvironment() {
            return rowPerPageEnvironment;
        }

        public Long getRowPerPageHeart() {
            return rowPerPageHeart;
        }

        public Long getRowPerPageSensorInfo() {
            return rowPerPageSensorInfo;
        }

        public Long getRowPerPageSkin() {
            return rowPerPageSkin;
        }

        public Long getRowPerPageUserInfo() {
            return rowPerPageUserInfo;
        }
    }

}
