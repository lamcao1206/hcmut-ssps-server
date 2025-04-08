package com.lamcao1206.hcmut_ssps.controller;


import com.lamcao1206.hcmut_ssps.DTO.CustomerDTO;
import com.lamcao1206.hcmut_ssps.core.ResponseFactory;
import com.lamcao1206.hcmut_ssps.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Console;

@RestController
@RequestMapping("/v1/api/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;
    
    @GetMapping("/")
    String sayHello() {
        return "Hello, customer";
    }
    
    @GetMapping("/{id}")
    ResponseEntity<?> findCustomerInfo(@PathVariable("id") Long id) {
        CustomerDTO customerResponseDTO = customerService.findCustomerById(id);
        return ResponseFactory.success("Fetch customer information success", customerResponseDTO);
    }
    
    
    
}
