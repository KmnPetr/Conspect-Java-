package com.example.FirsRestApp.controllers;

import com.example.FirsRestApp.models.Person;
import com.example.FirsRestApp.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;
    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping()
    public List<Person> getPeople(){
        return peopleService.findAll();//Jackson конвертирует эти обьекты в JSON
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable("id")int id){
        return peopleService.findOne(id);//Jacson сконвертирует в json
    }
}
