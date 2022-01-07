package com.candan.mongo.swb;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Getter
@Setter
public class Max30102 {
    @Id
    private String id;
    private String status;
    private String hr;
    private String spo2;
    private String ired;
    private String red;
    private String diff;
    private String personName;
    private String personSurname;
    private Date date;

    public Max30102(String status, String hr, String spo2,String ired, String red, String diff, String personName, String personSurname, Date date) {
        this.status = status;
        this.hr = hr;
        this.spo2 = spo2;
        this.ired = ired;
        this.red = red;
        this.diff = diff;
        this.personName = personName;
        this.personSurname = personSurname;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Max30102{" +
                "id='" + id + '\'' +
                ", type='" + status + '\'' +
                ", hr='" + hr + '\'' +
                ", spo2='" + spo2 + '\'' +
                ", diff='" + diff + '\'' +
                ", personName='" + personName + '\'' +
                ", personSurname='" + personSurname + '\'' +
                ", date=" + date +
                '}';
    }
}
