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
    private Long id;

    private  String type;
    private  String data;

    private Date date;

    private String person;

    @Override
    public String toString() {
        return "Heart{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", data='" + data + '\'' +
                ", date=" + date +
                ", person "+ person +
                '}';
    }

    public Long getId() {
        return id;
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

    public String getPerson() { return person; }

    public void setId(Long id_heart_rate) {
        this.id = id_heart_rate;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setData(String data) { this.data = data; }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPerson(String user) { this.person = user; }
}
