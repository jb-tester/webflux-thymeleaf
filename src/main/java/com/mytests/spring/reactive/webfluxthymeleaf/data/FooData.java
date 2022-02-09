package com.mytests.spring.reactive.webfluxthymeleaf.data;


public class FooData {

    String id;

    public FooData(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "FooData{'" +
                id +
                '}';
    }
}
