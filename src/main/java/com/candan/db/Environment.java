package com.candan.db;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name ="environment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Environment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id_environment;

    private  String type;
    private  String data;

    public Long getId_environment() {
        return id_environment;
    }

    public void setId_environment(Long id_environment) {
        this.id_environment = id_environment;
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
