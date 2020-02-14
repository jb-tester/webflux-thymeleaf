package com.mytests.spring.reactive.webfluxthymeleaf.repositories;

import com.mytests.spring.reactive.webfluxthymeleaf.data.Person;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

/**
 * *******************************
 * Created by irina on 2/14/2020.
 * Project: webflux-thymeleaf
 * *******************************
 */
@RepositoryDefinition(domainClass = Person.class, idClass = Long.class)
public interface PersonRepository {
    
    List<Person> findAll();
    List<Person> findAllByFirstNameIn(List<String> firstName);
    Person findFirstByAddressContains(String address);
}
