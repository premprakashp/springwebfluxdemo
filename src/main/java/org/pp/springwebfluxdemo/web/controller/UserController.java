package org.pp.springwebfluxdemo.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.pp.springwebfluxdemo.web.model.AppUser;
import org.pp.springwebfluxdemo.web.model.ErrorResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequestMapping("/v1/app")
public class UserController {

    @Operation(summary = "Get users", responses = {
            @ApiResponse(description = "Successful operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppUser.class))),
            @ApiResponse(description = "403 Forbidden", responseCode = "403", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(description = "401 Unauthorized", responseCode = "401", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping(value = "/users" , produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<AppUser> getUsers() {
        log.info("getUsers called in controller");
        AppUser user1 = AppUser.builder().name("user1").department("dept1").build();
        AppUser user2 = AppUser.builder().name("user2").department("dept2").build();
        return Flux.just(user1, user2);
    }

    @Operation(summary = "Create user", responses = {
            @ApiResponse(description = "Successful operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppUser.class))),
            @ApiResponse(description = "403 Forbidden", responseCode = "403", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(description = "401 Unauthorized", responseCode = "401", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping(value = "/user" , produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<AppUser> create(@RequestBody AppUser user){
        log.info("create user called in controller, {}", user);
        return Mono.just(user);
    }
}
