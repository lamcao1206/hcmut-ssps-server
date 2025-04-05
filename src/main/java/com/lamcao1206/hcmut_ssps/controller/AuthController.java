package com.lamcao1206.hcmut_ssps.controller;

import com.lamcao1206.hcmut_ssps.dto.request.CustomerRegisterDTO;
import com.lamcao1206.hcmut_ssps.core.ResponseFactory;
import com.lamcao1206.hcmut_ssps.dto.response.CustomerResponseDTO;
import com.lamcao1206.hcmut_ssps.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/auth")
public class AuthController {
    @Autowired
    private CustomerService customerService;
    
    @PostMapping("/register/customer")
    public ResponseEntity<?> registerCustomer(@RequestBody @Valid CustomerRegisterDTO dto) {
        CustomerResponseDTO customerResponseDTO = customerService.registerStudent(dto);
        return ResponseFactory.created("Customer register successfully", customerResponseDTO);
    }
}
