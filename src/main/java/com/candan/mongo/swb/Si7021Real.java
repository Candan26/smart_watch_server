package com.candan.mongo.swb;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Setter
@Getter
public class Si7021Real {

    @Id
    private String id;
    private String status;
    private float humidity;
    private float temperature;
    private String personName;
    private String personSurname;
    private Date date;

    public Si7021Real(String status, float humidity, float temperature, String personName, String personSurname, Date date) {
        this.status = status;
        this.humidity = humidity;
        this.temperature = temperature;
        this.personName = personName;
        this.personSurname = personSurname;
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
