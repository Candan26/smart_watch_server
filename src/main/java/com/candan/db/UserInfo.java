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
    private String email;

    private  Long age;
    private  Long weight;
    private  Long height;

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email "+email+
                ", age=" + age +
                ", weight=" + weight +
                ", height=" + height +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public Long getAge() {
        return age;
    }

    public Long getWeight() {
        return weight;
    }

    public Long getHeight() {
        return height;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public void setEmail(String email) { this.email= email;   }
}
