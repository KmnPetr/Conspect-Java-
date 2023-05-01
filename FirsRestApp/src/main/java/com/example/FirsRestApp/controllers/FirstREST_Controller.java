package com.example.FirsRestApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController//@Controller + @ResponseBody над каждым методом
@RequestMapping("/api")
public class FirstREST_Controller {
//    @ResponseBody//аннотация говорит что метод возвращает не форму а данные
    @GetMapping("/hello")
    public String sayHello(){
        return "Hello World!";//любой обьект будет конвертироваться в json
    }
}
