package com.candan.db;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name ="skin")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Skin {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id_skin;

    private  String type;
    private  String data;

    private Date skin_time;

    @Override
    public String toString() {
        return "Skin{" +
                "id_skin=" + id_skin +
                ", type='" + type + '\'' +
                ", data='" + data + '\'' +
                ", date=" + skin_time +
                '}';
    }

    public Long getId_skin() {
        return id_skin;
    }

    public String getType() {
        return type;
    }

    public String getData() {
        return data;
    }

    public Date getSkin_time() {
        return skin_time;
    }

    public void setId_skin(Long id_skin) {
        this.id_skin = id_skin;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setSkin_time(Date date) {
        this.skin_time = date;
    }
}
