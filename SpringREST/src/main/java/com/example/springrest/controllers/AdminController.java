package com.example.springrest.controllers;

import com.example.springrest.DAO.DAO;
import com.example.springrest.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    public final DAO dao;
@Autowired
    public AdminController(@Qualifier("DAO_with_Template") DAO dao) {this.dao = dao;}

    @GetMapping()
    public String adminPage(Model model, @ModelAttribute("person")Person person){
    model.addAttribute("people",dao.index());
    return "admin/adminPage";
    }
    @PatchMapping("/add")
    public String makeAdmin(@ModelAttribute("person")Person person){
        System.out.println(person.getId());//остальные поля имени емэйла показать нельзя таккак они с формы с выпадающего списка не приходят
        dao.addAdmin(person);
        return "redirect:/people";
    }
}
