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


}
