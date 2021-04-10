package com.candan.mongo.swb;


import org.springframework.data.annotation.Id;

import java.sql.Date;

public class Si7021 {
    @Id
    private String id;
    private String status;
    private String humidity;
    private String temperature;
    private String personName;
    private String personSurname;
    private Date date;

    public Si7021(String status, String humidity, String temperature, String personName, String personSurname, Date date) {
        this.status = status;
        this.humidity = humidity;
        this.temperature = temperature;
        this.personName = personName;
        this.personSurname = personSurname;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonSurname() {
        return personSurname;
    }

    public void setPersonSurname(String personSurname) {
        this.personSurname = personSurname;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Si7021{" +
                "id='" + id + '\'' +
                ", type='" + status + '\'' +
                ", humidity='" + humidity + '\'' +
                ", temperature='" + temperature + '\'' +
                ", personName='" + personName + '\'' +
                ", personSurname='" + personSurname + '\'' +
                ", date=" + date +
                '}';
    }
}
