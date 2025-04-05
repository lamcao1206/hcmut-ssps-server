package com.lamcao1206.hcmut_ssps.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @GetMapping("/")
    String sayHello() {
        return "Welcome to HCMUT SSPS APIs!";
    }
}
