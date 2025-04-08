package com.lamcao1206.hcmut_ssps.controller;

import com.lamcao1206.hcmut_ssps.core.ResponseFactory;
import com.lamcao1206.hcmut_ssps.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @Autowired
    SystemConfigService systemConfigService;
    
    @GetMapping("/")
    String sayHello() {
        return "Welcome to HCMUT SSPS APIs!";
    }
    
    @GetMapping("/v1/api/system")
    ResponseEntity<?> getSystemConfiguration() {
        return ResponseFactory.success("Fetch system configuration successfully", systemConfigService.getSystemConfig());
    }
}
