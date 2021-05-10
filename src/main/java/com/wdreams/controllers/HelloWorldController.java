package com.wdreams.controllers;

import io.swagger.annotations.ApiImplicitParam;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {


    @ApiImplicitParam(name = "Authorization", value = "Request needs Authorization Token", paramType = "header", dataTypeClass = String.class, required = false)
    @GetMapping("/hello")
    @Secured("ROLE_CIUDA")
    public String hello() {

        return "Hola Mundo!";
    }
}
