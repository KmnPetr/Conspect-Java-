package com.example.FirsRestApp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class PersonDTO {
    @NotEmpty(message = "Name should not be empty.")
    @Size(min = 2,max = 30,message = "Name should be betveen 2 and 30 characters.")
    private String name;
    @Min(value = 0,message = "Age should be greater than 0.")
    private int age;
    @Email(message = "Email should be valid")
    @NotEmpty(message = "Name should not be empty.")
    private String email;

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public int getAge() {return age;}
    public void setAge(int age) {this.age = age;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
}
