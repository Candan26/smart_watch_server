package com.candan.mongo.swb;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class SkinResistance {
    @Id
    private String id;
    private String status;
    private String srValue;
    private String personName;
    private String personSurname;
    private Date date;

    public SkinResistance(String status, String srValue, String personName, String personSurname, Date date) {
        this.status = status;
        this.srValue = srValue;
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

    public String getSrValue() {
        return srValue;
    }

    public void setSrValue(String srValue) {
        this.srValue = srValue;
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
        return "SkinResistance{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                ", srValue='" + srValue + '\'' +
                ", personName='" + personName + '\'' +
                ", personSurname='" + personSurname + '\'' +
                ", date=" + date +
                '}';
    }
}
