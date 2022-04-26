package com.mytests.spring.reactive.webfluxthymeleaf.controllers;

import com.mytests.spring.reactive.webfluxthymeleaf.data.Person;
import com.mytests.spring.reactive.webfluxthymeleaf.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping({ "/index", "index.html"})
public class IndexController {
    @Autowired
    private PersonRepository personRepository;
    private static final String LOGGED_IN_USER = "british_user";

    @GetMapping
    public Mono<String> index(Model model, String country) {


        return getUser("UK")
                .map(u -> model.addAttribute(LOGGED_IN_USER, u.getFirstName())).thenReturn("index");
    }

    public  Mono<Person> getUser(String country) {
        return Mono.just(personRepository.findFirstByAddressContains(country))
                ;
    }
}