package com.example.FirsRestApp.controllers;

import com.example.FirsRestApp.models.Person;
import com.example.FirsRestApp.services.PeopleService;
import com.example.FirsRestApp.util.PersonErrorResponse;
import com.example.FirsRestApp.util.PersonNotCreatedExeption;
import com.example.FirsRestApp.util.PersonNotFoundExeption;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid Person person,
                                             BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            StringBuilder errorMsg=new StringBuilder();

            List<FieldError>errors=bindingResult.getFieldErrors();
            for(FieldError error:errors){
                errorMsg.append(error.getField())
                        .append("--").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new PersonNotCreatedExeption(errorMsg.toString());
        }
        peopleService.save(person);
        return ResponseEntity.ok(HttpStatus.OK);//отправляем http с пустым телом и со статусом 200
    }

    @ExceptionHandler//добавится в методы в качестве ответ-ошибки
    private ResponseEntity<PersonErrorResponse>handlerExeption(PersonNotFoundExeption e){
        PersonErrorResponse response=new PersonErrorResponse(
                "Person with this id wasn't found!",
                System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);//NOT_FOUND - 404 статус
    }

    @ExceptionHandler//добавится в методы в качестве ответ-ошибки
    private ResponseEntity<PersonErrorResponse>handlerExeption(PersonNotCreatedExeption e){
        PersonErrorResponse response=new PersonErrorResponse(
                e.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
