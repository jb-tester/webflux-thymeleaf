package com.mytests.spring.reactive.webfluxthymeleaf.controllers;

import com.mytests.spring.reactive.webfluxthymeleaf.data.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;


import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;


@Configuration
public class ReactiveControllerWithNested {

    @Bean
    public RouterFunction<ServerResponse> routeNestedSimple(){
        return RouterFunctions.nest(path("/simple_nested"),
                route(GET("/aaa"),
                        req -> ok().body(fromValue("aaa")))
                           .andRoute(GET("/bbb"),
                                   req -> ok().body(fromValue("bbb")))
                   );
    }

    @Bean
    public RouterFunction<ServerResponse> routeComplexNestedInlinePredicates() {


        return nest(path("/multi"),
                nest(path("/multi_nested_level1"),
                        route(GET("/aaa"), req -> ok().body(fromValue("aaa")))
                                   .andRoute(GET("/zzz"), req -> ok().body(fromValue("zzz"))))
                           .andNest(path("/multi_nested_level2"),
                                 nest(path("/multi_nested_level21"),
                                        route(GET("/bbb"), req -> ok().body(fromValue("bbb")))
                                                   .andRoute(GET("/ccc"), req -> ok().body(fromValue("ccc"))))
                                            .andNest(path("/multi_nested_level22"),
                                                route(GET("/ddd"), req -> ok().body(fromValue("ddd")))
                                                           .andRoute(GET("/eee"), req -> ok().body(fromValue("eee")))

                                        ))
                           .andRoute(GET("/top"), req -> ok().render("nested_view"))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> routeComplexNestedInlineAltPredicates() {


        return nest(path("/inline/alt/"),
                nest(path("/altinline_nested_level1"),
                        route(GET("/aaa1").or(GET("/aaa2")),
                                req -> ok().render("nested_view")))
                        .andNest(path("/altinline_nested_level2"),
                                nest(path("/altinline_nested_level21"),
                                        route(GET("/bbb1").or(GET("/bbb2")),
                                                req -> ok().body(fromValue("bbb")))
                                                .andRoute(GET("/ccc1").or(GET("/ccc2")),
                                                        req -> ok().body(fromValue("ccc"))))
                                        .andNest(path("/altinline_nested_level22"),
                                                route(GET("/ddd1").or(GET("/ddd2")), req -> ok().body(fromValue("ddd")))

                                        )));
    }
    @Bean
    public RouterFunction<ServerResponse> routeComplexNested(){


        RequestPredicate predicate1 = GET("/aaa").or(GET("/bbb"));
        RequestPredicate predicate2 = GET("/ccc").or(GET("/ddd"));
        RequestPredicate predicate3 = GET("/eee").or(GET("/fff"));
        RequestPredicate predicate4 = GET("/ggg").or(GET("/hhh"));
        return  nest(  path("/with_vars"),
                route(predicate1,
                        req -> ok().render("nested_view"))
                        .andNest(path("/complexnested_nested_level1"),
                                route(predicate2, req -> ok().render("nested_view") )
                                        .andRoute(predicate3, req -> ok().render("nested_view") )
                                        .andNest(path("/complexnested_nested_level2"),
                                                route(predicate4, req -> ok().render("nested_view")))

                        ));
    }

    @Bean
    public RouterFunction<ServerResponse> routeSimpleNestedWithVars(){
        final RequestPredicate predicate1 = GET("/foo");
        final RequestPredicate predicate2 = GET("/bar");
        return RouterFunctions.nest(path("/simple_nested_with_vars"),
                route(predicate1,
                        req -> ok().body(fromValue("foo")))
                        .andRoute(predicate2,
                                req -> ok().body(fromValue("bar")))
        );

    }

}

