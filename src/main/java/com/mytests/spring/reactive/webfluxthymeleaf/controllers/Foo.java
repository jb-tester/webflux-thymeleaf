package com.mytests.spring.reactive.webfluxthymeleaf.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * *
 * <p>Created by irina on 2/9/2022.</p>
 * <p>Project: webflux-thymeleaf</p>
 * https://youtrack.jetbrains.com/issue/IDEA-288392 - check exception
 * *
 */
@Configuration
public class Foo {

    @Bean
    public RouterFunction<ServerResponse> routePathVar(){

        return route(GET("/test_007/{my_var07}"),
                this::process01);
    }

    public Mono<ServerResponse> process01(ServerRequest req){
        Map<String, String> map = new HashMap<>();
        map.put("_attr",req.pathVariable("my_var07"));
        return ServerResponse.ok().render("my_view", map);
    }
}
