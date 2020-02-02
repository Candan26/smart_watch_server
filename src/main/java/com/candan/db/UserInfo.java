package com.candan.db;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


import javax.persistence.*;

@Entity
@Table(name ="user_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserInfo {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;

    private  Long age;
    private  Long weight;
    private  Long height;



}
