package com.mytests.spring.reactive.webfluxthymeleaf.controllers;

import com.mytests.spring.reactive.webfluxthymeleaf.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.result.view.Rendering;

/**
 * *******************************
 * Created by irina on 2/14/2020.
 * Project: webflux-thymeleaf
 * *******************************
 */
@Controller
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/")
    public Rendering getAll(){
        return Rendering.view("home")
                .modelAttribute("home","hello!")
                .modelAttribute("all", personRepository.findAll())
                .build();
    }

    @RequestMapping("/next")
    public String next(Model model) {
        model.addAttribute("next_attr1", "something");
        return "next";
    } 
    
}
