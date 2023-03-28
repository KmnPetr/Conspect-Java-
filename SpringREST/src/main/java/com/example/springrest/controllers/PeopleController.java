package com.example.springrest.controllers;

import com.example.springrest.DAO.DAO;
import com.example.springrest.DAO.DAO_with_Template;
import com.example.springrest.model.Person;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

//небольшая зависимость "thymeleaf-layout-dialect" оживила валидацию
//также в видео употребляются зависимости "thymeleaf-spring5" и "hibernate-validator", но я их не использовал
@Controller
@RequestMapping("/people")
public class PeopleController {

    private final DAO DAO;

    @Autowired
    public PeopleController(@Qualifier("DAO_with_Template")DAO DAO) {this.DAO = DAO;}

    @GetMapping()
    public String index(Model model){
        //передаст всех people из ДАО на view
        model.addAttribute("people", DAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id")int id,Model model){
        //Получим 1 человека из ДАО на view
        model.addAttribute("person", DAO.show(id));
        return "people/show";
    }
    /**
     * метод возвращает форму html для заполнения полей данных нового человека
     */
    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("person",new Person());
        return "people/new";
    }
    /**
     * модель принимает POST запрос с формы "people/new.html",
     * берет из него данные и добавляет новый обьект в базу данных.
     * А также делает редирект на страницу "/people".
     */
    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "people/new"; //если поступивший обьект имеет невалидные поля,то возвращаем пользователю форму заново переписывать
        }
        DAO.save(person);
        return "redirect:/people";
    }

    /**
     * @param model
     * @param id человека, чьи данные нужно изменить
     * @return вернет html форму для обновления данных человека
     */
    @GetMapping("/{id}/edit")
    public String edit(Model model,@PathVariable("id")int id){
        model.addAttribute("person", DAO.show(id));
        return "people/edit";
    }

    /**
     * метод принимает http запрос на обновление данных в БД
     * @param person обьект с уже измененными данными
     * @param id обьекта
     * @return перенаправляет пользователя на страницу со списком обьектов с уже обновленными данными
     */
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person")@Valid Person person,
                         BindingResult bindingResult,
                         @PathVariable("id")int id){
        if (bindingResult.hasErrors())
            return "people/edit";
        DAO.update(id,person);
        return "redirect:/people";
    }

    /**
     * метод принимает запрос на удаление обьекта
     * @param id обьекта на удаление
     * @return перенаправляет пользователя на первую страницу со списком
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id")int id){
        DAO.delete(id);
        return "redirect:/people";
    }
}


