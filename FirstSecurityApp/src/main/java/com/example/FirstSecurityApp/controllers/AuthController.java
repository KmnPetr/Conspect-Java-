package com.example.FirstSecurityApp.controllers;

import com.example.FirstSecurityApp.models.Person;
import com.example.FirstSecurityApp.services.RegistrationServise;
import com.example.FirstSecurityApp.util.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final PersonValidator personValidator;
    private final RegistrationServise registrationServise;
    @Autowired
    public AuthController(PersonValidator personValidator, RegistrationServise registrationServise) {
        this.personValidator = personValidator;
        this.registrationServise = registrationServise;
    }

    /**
     * страница аутентификации
     */
    @GetMapping("/login")
    public String loginPage(){
        return "auth/login";
    }
    /**
     * страница регистрации нового юзера
     */
    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person")Person person/*полож.пустого чел.в модель*/){
        return "auth/registration";
    }
    /**
     * принимаем данные после регистрации
     */
    @PostMapping("/registration")
    public String performRegistration(@Valid @ModelAttribute("person")Person person/*получ.данные с формы*/,
                                      BindingResult bindingResult){
        personValidator.validate(person,bindingResult);
        if (bindingResult.hasErrors())return "auth/registration";


        registrationServise.register(person);
        return "redirect:/auth/login";
    }
}
