package com.example.FirsRestApp.controllers;

import com.example.FirsRestApp.dto.PersonDTO;
import com.example.FirsRestApp.models.Person;
import com.example.FirsRestApp.services.PeopleService;
import com.example.FirsRestApp.util.PersonErrorResponse;
import com.example.FirsRestApp.util.PersonNotCreatedExeption;
import com.example.FirsRestApp.util.PersonNotFoundExeption;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;
    private final ModelMapper modelMapper;
    @Autowired
    public PeopleController(PeopleService peopleService,
                            ModelMapper modelMapper) {
        this.peopleService = peopleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<PersonDTO> getPeople(){
        return peopleService.findAll()
                .stream()
                .map(this::convertToPersonDTO)
                .collect(Collectors.toList());//Jackson конвертирует эти обьекты в JSON
    }

    @GetMapping("/{id}")
    public PersonDTO getPerson(@PathVariable("id")int id){
        return convertToPersonDTO(peopleService.findOne(id));//Jacson сконвертирует в json
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid PersonDTO personDTO,
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
        peopleService.save(convertToPerson(personDTO));
        return ResponseEntity.ok(HttpStatus.OK);//отправляем http с пустым телом и со статусом 200
    }

    /**
     * конвертирует DTO в Person
     */
    private Person convertToPerson(PersonDTO personDTO) {
        //ModelMapper modelMapper=new ModelMapper();//лучше не делать так- лишнее создание обьекта лучше внедрить бин сингелтон
        return modelMapper.map(personDTO, Person.class);
        //как это делать без маппера
        /*Person person=new Person();

        person.setName(personDTO.getName());
        person.setAge(personDTO.getAge());
        person.setEmail(personDTO.getEmail());
        return person;*/
    }

    /**
     * конвертирует Person в DTO
     */
    private PersonDTO convertToPersonDTO(Person person){
        return modelMapper.map(person, PersonDTO.class);
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
