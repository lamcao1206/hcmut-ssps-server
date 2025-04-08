package com.lamcao1206.hcmut_ssps.controller;

import com.lamcao1206.hcmut_ssps.DTO.CustomerDTO;
import com.lamcao1206.hcmut_ssps.DTO.LoginDTO;
import com.lamcao1206.hcmut_ssps.DTO.RegisterDTO;
import com.lamcao1206.hcmut_ssps.DTO.SpsoDTO;
import com.lamcao1206.hcmut_ssps.core.ResponseFactory;
import com.lamcao1206.hcmut_ssps.service.AuthService;
import com.lamcao1206.hcmut_ssps.service.CustomerService;
import com.lamcao1206.hcmut_ssps.service.SPSOService;
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
    
    @Autowired
    private SPSOService spsoService;
    
    @Autowired
    private AuthService authService;
    
    @PostMapping("/register/customer")
    public ResponseEntity<?> registerCustomer(@RequestBody @Valid RegisterDTO dto) throws Exception {
        CustomerDTO customerResponseDTO = customerService.registerStudent(dto);
        return ResponseFactory.created("Customer register successfully", customerResponseDTO);
    }
    
    @PostMapping("/register/spso")
    public ResponseEntity<?> registerSPSO(@RequestBody @Valid RegisterDTO dto) throws Exception {
        SpsoDTO spsoResponseDTO = spsoService.registerSPSO(dto);
        return ResponseFactory.created("SPSO register successfully", spsoResponseDTO);
    }
    
    @PostMapping("/login/customer")
    public ResponseEntity<?> loginCustomer(@RequestBody @Valid LoginDTO dto) {
        return ResponseFactory.success("Login successfully",authService.authenticateUser(dto));
    }

    @PostMapping("/login/spso")
    public ResponseEntity<?> loginSPSO(@RequestBody @Valid LoginDTO dto) {
        return ResponseFactory.success("Login successfully",authService.authenticateUser(dto));
    }
}
