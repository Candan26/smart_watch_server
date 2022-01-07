package com.candan.mongo.swb;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Setter
@Getter
public class Max3003 {
    @Id
    private String id;
    private String status;
    private String ecg;
    private String rr;
    private String bpm;
    private String personName;
    private String personSurname;
    private Date date;

    public Max3003(String status, String ecg, String rr, String bpm,String personName, String personSurname, Date date) {
        this.status = status;
        this.ecg = ecg;
        this.rr = rr;
        this.bpm = bpm;
        this.personName = personName;
        this.personSurname = personSurname;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Max3003{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                ", ecg='" + ecg + '\'' +
                ", rr='" + rr + '\'' +
                ", personName='" + personName + '\'' +
                ", personSurname='" + personSurname + '\'' +
                ", date=" + date +
                '}';
    }
}

