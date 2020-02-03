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

    public Long getId_skin() {
        return id_skin;
    }

    public void setId_skin(Long id_skin) {
        this.id_skin = id_skin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private Date date;
}
