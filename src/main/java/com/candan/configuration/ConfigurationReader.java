package com.candan.configuration;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationReader {

    @Bean
    MyConfig myBean() {
        return new MyConfig();
    }

    public static class MyConfig {

        @Value("${mail.thread.timeInSec}")
        private Long mailThreadTimeInSec;
        @Value("${mail.website.maxMailCount}")
        private Long websiteMaxMailCount;
        @Value("${controller.sensorInfo.rowPerPage}")
        private Long rowPerPageSensorInfo;
        @Value("${controller.skin.rowPerPage}")
        private Long rowPerPageSkin;
        @Value("${controller.userInfo.rowPerPage}")
        private Long rowPerPageUserInfo;

        @Value("${mail.user.port}")
        private int mailUserPort;

        @Value("${mail.user.name}")
        private String mailUserName;
        @Value("${mail.user.password}")
        private String mailUserPassword;
        @Value("${mail.user.host}")
        private String mailUserHost;
        @Value("${excel.path}")
        private String excelPath;

        public String getMailUserName() { return mailUserName; }

        public String getMailUserPassword() { return mailUserPassword; }

        public String getMailUserHost() { return mailUserHost;}

        public String getExcelPath() { return excelPath;  }

        public int getMailUserPort() { return mailUserPort; }

        public Long getMailThreadTimeInSec() {
            return mailThreadTimeInSec;
        }

        public Long getWebsiteMaxMailCount() {
            return websiteMaxMailCount;
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
