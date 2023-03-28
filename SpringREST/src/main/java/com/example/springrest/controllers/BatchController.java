package com.example.springrest.controllers;

import com.example.springrest.DAO.DAO;
import com.example.springrest.DAO.DAO_with_Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test-batch-update")
public class BatchController {

    private final DAO_with_Template dao;
    @Autowired
    public BatchController(DAO_with_Template dao) {this.dao = dao;}
    /**
     * @return возвращает страницу с двумя ссылками на два способа вставки в базу данных
     */
    @GetMapping()
    public String index(){
        return "batch/index";
    }

    /**
     * метод делает запрос на insert 1000 полей в БД каждый запрос по отдельности
     * @return возвращает пользователя на страницу со списком из БД
     */
    @GetMapping("/without")
    public String withoutBatch(){
        dao.testMultipleUpdate();
        return "redirect:/people";
    }

    /**
     * метод делает запрос на insert 1000 полей в БД способом BatchUpdate
     * @return возвращает пользователя на страницу со списком из БД
     */
    @GetMapping("/with")
    public String withBatch(){
        dao.testBatchUpdate();
        return "redirect:/people";
    }
}
