package com.lamcao1206.hcmut_ssps.controller;

import com.lamcao1206.hcmut_ssps.DTO.PrinterDTO;
import com.lamcao1206.hcmut_ssps.DTO.SystemConfigDTO;
import com.lamcao1206.hcmut_ssps.core.ApiResponse;
import com.lamcao1206.hcmut_ssps.core.ResponseFactory;
import com.lamcao1206.hcmut_ssps.entity.SPSO;
import com.lamcao1206.hcmut_ssps.security.CustomUserDetails;
import com.lamcao1206.hcmut_ssps.service.PrinterService;
import com.lamcao1206.hcmut_ssps.service.SystemConfigService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1/api/spso/")
public class SPSOController {
    @Autowired
    SystemConfigService systemConfigService;
    
    @Autowired
    PrinterService printerService;
    
    @GetMapping("/")
    String sayHello() {
        return "Hello, SPSO";
    }
    
    @PostMapping("/system_config")
    ResponseEntity<?> modifySystemConfig(@RequestBody SystemConfigDTO dto) throws IOException  {
        return ResponseFactory.success("Modify successfully", systemConfigService.modifySystemConfig(dto));
    }
    
    @PostMapping("/printer")
    ResponseEntity<?> addPrinter(
            @RequestBody PrinterDTO printerDTO,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) throws BadRequestException {
        SPSO spso = (SPSO) userDetails.getUser();
        return ResponseFactory.created("Printer created!", printerService.create(printerDTO, spso));
    }

    @GetMapping("/printer")
    ResponseEntity<?> getAllPrinter(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) throws BadRequestException {
        List<PrinterDTO> printers = printerService.getAllPrinter();
        ApiResponse<List<PrinterDTO>> response = ApiResponse.of(200, "Success", printers);
        return ResponseEntity.ok(response);
    }
    
    

    
}
