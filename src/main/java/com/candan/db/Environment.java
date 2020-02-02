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

    private Date date;
}
