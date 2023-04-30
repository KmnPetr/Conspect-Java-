package com.example.FirstSecurityApp.controllers;

import com.example.FirstSecurityApp.security.PersonDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("/hello")
    public String sayHello(){
        return "hello";
    }
    @GetMapping("/showUserInfo")
    public String showUserInfo(){
        //внимательно на импорт
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails=(PersonDetails) authentication.getPrincipal();
        System.out.println(personDetails.getPerson());
        return  "/hello";
    }
    @GetMapping("/admin")
    public String adminPage(){
        return "admin";
    }

    //это метод для проверки аннотации @PreAuthorize
    /*@PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SOMEOTHER')")//если пользователь имеет одну из ролей
//    @PreAuthorize("hasRole('ROLE_ADMIN') and hasRole('ROLE_SOMEOTHER')")//если пользователь имеет одновременно обе роли
    public void doAdminStaff(){
        System.out.println("only admin here");
    }*/
}
