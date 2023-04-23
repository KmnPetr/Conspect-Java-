package com.example.FirstSecurityApp.util;


import com.example.FirstSecurityApp.models.Person;
import com.example.FirstSecurityApp.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * класс проверяет на уникальность регистрации имени нового человека
 */
@Component
public class PersonValidator implements Validator {
    private final PersonDetailsService personDetailsService;
    @Autowired
    public PersonValidator(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    /**
     * нужен для определения того класса который хотим валидировать
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    /**
     * здесь нам было лень создавать PersonServise и при помощи Optional проверять наличие человека,
     * поэтому мы воспользовались старым кодом кот.выбрасывает исключение если человека нет
     */
    @Override
    public void validate(Object target, Errors errors) {
        Person person=(Person)target;
        try{
            personDetailsService.loadUserByUsername(((Person) target).getUsername());
        }catch (UsernameNotFoundException ignore){
            return;//все ок пользователь не найден
        }
        //если таки ошибки не выпало, то вернем другую ошибку но уже в валидацию:)
        errors.rejectValue("username","","человек с таким именем пользователя уже существует");
    }
}
