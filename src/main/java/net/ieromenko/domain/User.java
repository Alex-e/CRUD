package net.ieromenko.domain;



import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Date;

import static javax.persistence.GenerationType.IDENTITY;


/**
 * Created by Alex Ieromenko on 03.11.14.
 */

@Entity
@Table(name = "User")
public class User implements Serializable {
    private Long id;
    private String name;
    private int age;
    private boolean isAdmin;
    private Date createdDate;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotEmpty(message = "Имя не может быть пустым")
    @Size(min = 3, max = 30, message = "Имя должно иметь минимум 3 символа. Максимум 30")
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Digits(integer = 3, fraction = 0, message = "Нужно указать целое число не длиннее 3")
    @Min(value = 1, message = "Возраст не может быть меньше 1 года")
    @Column(name = "age")
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Column(name = "isadmin")
    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "createdDate")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String toString() {
        return "User id: " + id + ", name: " + name + ", age: " + age + ",isAdmin: " + isAdmin + ", date: " + createdDate;
    }
}
