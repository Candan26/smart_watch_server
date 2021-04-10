package com.candan.mongo.swb;

import org.springframework.data.annotation.Id;

import java.sql.Date;

public class Max3003 {
    @Id
    private String id;
    private String status;
    private String ecg;
    private String rr;
    private String personName;
    private String personSurname;
    private Date date;

    public Max3003(String status, String ecg, String rr, String personName, String personSurname, Date date) {
        this.status = status;
        this.ecg = ecg;
        this.rr = rr;
        this.personName = personName;
        this.personSurname = personSurname;
        this.date = date;
    }

    public String getRr() {
        return rr;
    }

    public void setRr(String rr) {
        this.rr = rr;
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

    public String getEcg() {
        return ecg;
    }

    public void setEcg(String ecg) {
        this.ecg = ecg;
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

