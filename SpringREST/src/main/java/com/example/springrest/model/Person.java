package com.example.springrest.model;


import jakarta.validation.constraints.*;

public class Person {
    private int id;
    @NotEmpty(message = "Name should not empty")
    @Size(min = 2,max = 30,message = "Name should be betveen 2 and 30 characters")
    private String name;
    @Min(value = 1,message = "Age should be greated than 1")
    private int age;
    @NotEmpty(message = "Email should not empty")
    @Email(message = "Email should be valid")
    private String email;
    @NotEmpty(message = "Address should not empty")
    @Pattern(regexp = "[A-Z]\\w+, [A-Z]\\w+, \\d{6}",message = "You address should be this format: Country, City, Postal code(6 digits)")//Страна, Город, Индекс(6 цифр)
    private String address;

    public Person() {}

    public Person(int id,String name, int age, String email, String address) {
        this.id=id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.address = address;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {return address;}

    public void setAddress(String address) {this.address = address;}
}
