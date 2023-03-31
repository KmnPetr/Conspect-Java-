package com.example.springrest.util;

import com.example.springrest.DAO.DAO_with_Template;
import com.example.springrest.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * здесь содержатся проверки валидации требующии запроса в БД
 */
@Component
public class PersonValidator implements Validator {

    private final DAO_with_Template dao;
    @Autowired
    public PersonValidator(DAO_with_Template dao) {this.dao = dao;}

    @Override
    public boolean supports(Class<?> clazz) {
        //метод не позволит принимать и валидировать другие классы кроме Person
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person=(Person) target;

        //проверить уникальность email в БД
        if(dao.show(person.getEmail()).isPresent()/*метод из Optional*/){
            errors.rejectValue("email"/*на каком поле*/,
                    "",
                    "This email is already taken"/*сообщение*/);
        }

        //вторая проверка
        //Проверяем, что у человека имя начинается с литеры
        if (false){/*тут мог бы быть код, но эта фигня не требует обращения к БД*/}
    }
}
