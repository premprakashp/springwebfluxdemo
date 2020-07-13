package org.pp.springwebfluxdemo.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.pp.springwebfluxdemo.web.model.AppUser;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequestMapping("/v1/app")
public class UserController {

    @GetMapping(value = "/users" , produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<AppUser> getUsers() {
        log.info("getUsers called in controller");
        AppUser user1 = AppUser.builder().name("user1").department("dept1").build();
        AppUser user2 = AppUser.builder().name("user2").department("dept2").build();
        return Flux.just(user1, user2);
    }

    @PostMapping(value = "/user" , produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<AppUser> create(@RequestBody AppUser user){
        log.info("create user called in controller, {}", user);
        return Mono.just(user);
    }
}
