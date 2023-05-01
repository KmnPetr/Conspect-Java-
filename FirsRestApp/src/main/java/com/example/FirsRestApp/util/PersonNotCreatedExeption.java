package com.example.FirsRestApp.util;

public class PersonNotCreatedExeption extends RuntimeException{
    public PersonNotCreatedExeption(String msg) {
        super(msg);
    }
}
