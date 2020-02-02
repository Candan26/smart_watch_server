package com.candan.db;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Table(name ="sensor_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SensorInfo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id_sensor;

    private  String type;

}
