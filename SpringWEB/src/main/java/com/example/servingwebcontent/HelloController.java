package com.example.servingwebcontent;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/first")
public class HelloController {
    @GetMapping("/hello")
    public String sayHello(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "first/hello";
    }
    @GetMapping("/goodbye")
    public String sayGoodbye(@RequestParam(name = "name",required = false,defaultValue = "World")String name,Model model){
        model.addAttribute("name",name);
        return "first/goodbye";
    }


}