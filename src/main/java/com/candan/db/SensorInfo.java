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

    public Long getId_sensor() {
        return id_sensor;
    }

    public void setId_sensor(Long id_sensor) {
        this.id_sensor = id_sensor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
