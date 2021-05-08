package com.candan.mongo.swb;


import org.springframework.data.annotation.Id;

import java.util.Date;

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

    public String getIred() {
        return ired;
    }

    public void setIred(String ired) {
        this.ired = ired;
    }

    public String getRed() {
        return red;
    }

    public void setRed(String red) {
        this.red = red;
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

    public String getHr() {
        return hr;
    }

    public void setHr(String hr) {
        this.hr = hr;
    }

    public String getSpo2() {
        return spo2;
    }

    public void setSpo2(String spo2) {
        this.spo2 = spo2;
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
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
