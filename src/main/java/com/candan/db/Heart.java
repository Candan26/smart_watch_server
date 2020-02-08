package com.candan.db;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name ="heart")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Heart {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id_heart_rate;

    private  String type;
    private  String data;

    private Date date;

    @Override
    public String toString() {
        return "Heart{" +
                "id_heart_rate=" + id_heart_rate +
                ", type='" + type + '\'' +
                ", data='" + data + '\'' +
                ", date=" + date +
                '}';
    }

    public Long getId_heart_rate() {
        return id_heart_rate;
    }

    public String getType() {
        return type;
    }

    public String getData() {
        return data;
    }

    public Date getDate() {
        return date;
    }

    public void setId_heart_rate(Long id_heart_rate) {
        this.id_heart_rate = id_heart_rate;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
