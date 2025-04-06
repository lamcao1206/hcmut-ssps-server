package com.lamcao1206.hcmut_ssps.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/customer")
public class CustomerController {
    @GetMapping("/")
    String sayHello() {
        return "Hello, customer";
    }
    
    
}
