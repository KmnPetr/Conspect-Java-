package com.example.springrest.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Name should not empty")
    @Size(min = 2,max = 30,message = "Name should be betveen 2 and 30 characters")
    @Column(name = "name")
    private String name;

    @Min(value = 1,message = "Age should be greated than 1")
    @Column(name = "age")
    private int age;

    @NotEmpty(message = "Email should not empty")
    @Email(message = "Email should be valid")
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "Address should not empty")
    @Pattern(regexp = "[A-Z]\\w+, [A-Z]\\w+, \\d{6}",message = "You address should be this format: Country, City, Postal code(6 digits)")//Страна, Город, Индекс(6 цифр)
    @Column(name = "address")
    private String address;

    @Column(name = "dateOfBirth")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy"/*MM большие потомучто не минуты*/)
    //можно добавить валидацию
    private Date dateOfBirth;

    @Column(name = "createdAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public Person() {}

    public Person(String name, int age, String email, String address) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.address = address;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id=id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public int getAge() {return age;}
    public void setAge(int age) {this.age = age;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}
    public Date getDateOfBirth() {return dateOfBirth;}
    public void setDateOfBirth(Date dateOfBirth) {this.dateOfBirth = dateOfBirth;}
    public Date getCreatedAt() {return createdAt;}
    public void setCreatedAt(Date createdAt) {this.createdAt = createdAt;}
}
