package com.lamcao1206.hcmut_ssps.controller;

import com.lamcao1206.hcmut_ssps.DTO.CustomerDTO;
import com.lamcao1206.hcmut_ssps.DTO.LoginDTO;
import com.lamcao1206.hcmut_ssps.DTO.RegisterDTO;
import com.lamcao1206.hcmut_ssps.core.ResponseFactory;
import com.lamcao1206.hcmut_ssps.service.CustomerService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
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
    public ResponseEntity<?> registerCustomer(@RequestBody @Valid RegisterDTO dto) throws BadRequestException {
        CustomerDTO customerResponseDTO = customerService.registerStudent(dto);
        return ResponseFactory.created("Customer register successfully", customerResponseDTO);
    }
    
    @PostMapping("/login/customer")
    public ResponseEntity<?> loginCustomer(@RequestBody @Valid LoginDTO dto) {
        return ResponseFactory.success("Login successfully",customerService.authenticateUser(dto));
    }
}
