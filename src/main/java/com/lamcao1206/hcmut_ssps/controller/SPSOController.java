package com.lamcao1206.hcmut_ssps.controller;

import com.lamcao1206.hcmut_ssps.DTO.SystemConfigDTO;
import com.lamcao1206.hcmut_ssps.core.ResponseFactory;
import com.lamcao1206.hcmut_ssps.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/v1/api/spso/")
public class SPSOController {
    @Autowired
    SystemConfigService systemConfigService;
    
    @GetMapping("/")
    String sayHello() {
        return "Hello, SPSO";
    }
    
    @PostMapping("/system_config")
    ResponseEntity<?> modifySystemConfig(@RequestBody SystemConfigDTO dto) throws IOException  {
        System.out.println(dto);
        return ResponseFactory.success("Modify successfully", systemConfigService.modifySystemConfig(dto));
    }
}
