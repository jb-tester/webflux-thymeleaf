package com.mytests.spring.reactive.webfluxthymeleaf.controllers;

import com.mytests.spring.reactive.webfluxthymeleaf.data.FooData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * *******************************
 * Created by Irina.Petrovskaya on 3/10/2020.
 * Project: webflux-thymeleaf
 * https://youtrack.jetbrains.com/issue/IDEA-234548
 * https://youtrack.jetbrains.com/issue/IDEA-234927
 * https://youtrack.jetbrains.com/issue/IDEA-234929
 * https://youtrack.jetbrains.com/issue/IDEA-234930
 * *******************************
 */
@Configuration
public class ReactiveControllerCompositePredicates {

    @Bean
    public RouterFunction<ServerResponse> routeOk(){
        Map<String, String> map = new HashMap<>();
        map.put("ok_attr","ok!");

        RequestPredicate predicate1 = GET("/ok/test_foo").or(GET("/ok/test_bar"));
        // model attributes not resolved - https://youtrack.jetbrains.com/issue/IDEA-234548
        return route(predicate1,
                req -> ok().render("ok_view", map));
    }
    @Bean
    public RouterFunction<ServerResponse> routeOk2(){
        // model attributes not resolved - https://youtrack.jetbrains.com/issue/IDEA-234548
        return route(GET("/ok2"),
                req -> ok().render("ok2_view", new FooData("ok!!!")));
    }
    @Bean
    public RouterFunction<ServerResponse> routeComposedPredicate(){
        // not shown in Endpoints - https://youtrack.jetbrains.com/issue/IDEA-234927
        return route(method(HttpMethod.GET).and(path("/test_03")),
                this::process03);
    }
    @Bean
    public RouterFunction<ServerResponse> routeComposedPredicatePathVarsAndParams(){
        // not shown in Endpoints - https://youtrack.jetbrains.com/issue/IDEA-234927
        // path variable is not resolved - https://youtrack.jetbrains.com/issue/IDEA-234929
        return route(method(HttpMethod.GET).and(path("/test_00/{my_var00}").and(queryParam("my_param0", "222"))),
                this::process00);
    }
    @Bean
    public RouterFunction<ServerResponse> routeQueryParam(){
        // no reference for query param - https://youtrack.jetbrains.com/issue/IDEA-234930
        return route(GET("/test_02").and(queryParam("my_param2", "333")),
                this::process02);
    }
    @Bean
    public RouterFunction<ServerResponse> routePathVar(){

        return route(GET("/test_01/{my_var01}"),
                this::process01);
    }

   public Mono<ServerResponse> process00(ServerRequest req){
       Map<String, String> map = new HashMap<>();
       map.put("_attr",req.pathVariable("my_var00")+" "+(req.queryParam("my_param0").isPresent()? req.queryParam(
               "my_param0").get():""));
        return ServerResponse.ok().render("my_view", map);
   }
    public Mono<ServerResponse> process01(ServerRequest req){
        // model attributes not resolved - https://youtrack.jetbrains.com/issue/IDEA-234548
        Map<String, String> map = new HashMap<>();
        map.put("_attr",req.pathVariable("my_var01"));
        return ServerResponse.ok().render("my_view", map);
    }
    public Mono<ServerResponse> process03(ServerRequest req){
        Map<String, String> map = new HashMap<>();
        map.put("_attr","test_0");
        return ServerResponse.ok().render("my_view", map);
    }
    public Mono<ServerResponse> process02(ServerRequest req){
        Map<String, String> map = new HashMap<>();
        map.put("_attr",(req.queryParam("my_param2").isPresent()? req.queryParam(
                "my_param2").get():"???"));
        return ServerResponse.ok().render("my_view", map);
    }
}
