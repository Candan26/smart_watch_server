package com.candan.mongo.swb;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Setter
@Getter
public class UserInfo {
    @Id
    private String id;
    private String name;
    private String surname;
    private String email;

    private  Long age;
    private  Long weight;
    private  Long height;
    private Date date;

    public UserInfo(String name, String surname, String email, Long age, Long weight, Long height) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.age = age;
        this.weight = weight;
        this.height = height;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", height=" + height +
                '}';
    }
}
