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

    private Date date;
}
