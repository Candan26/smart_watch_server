package com.candan.mongo.swb;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Setter
@Getter
public class SkinResistanceReal {
    @Id
    private String id;
    private String status;
    private Integer srValue;
    private String personName;
    private String personSurname;
    private Date date;

    public SkinResistanceReal(String status, Integer srValue, String personName, String personSurname, Date date) {
        this.status = status;
        this.srValue = srValue;
        this.personName = personName;
        this.personSurname = personSurname;
        this.date = date;
    }

    @Override
    public String toString() {
        return "SkinResistance{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                ", srValue='" + srValue + '\'' +
                ", personName='" + personName + '\'' +
                ", personSurname='" + personSurname + '\'' +
                ", date=" + date +
                '}';
    }
}
