package com.lamcao1206.hcmut_ssps.controller;

import com.lamcao1206.hcmut_ssps.DTO.PrinterDTO;
import com.lamcao1206.hcmut_ssps.core.ResponseFactory;
import com.lamcao1206.hcmut_ssps.entity.SPSO;
import com.lamcao1206.hcmut_ssps.security.CustomUserDetails;
import com.lamcao1206.hcmut_ssps.service.PrinterService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/printer")
public class PrinterController {
    @Autowired
    PrinterService printerService;
    
    @PostMapping
    ResponseEntity<?> addPrinter(
            @RequestBody PrinterDTO printerDTO,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) throws BadRequestException {
        SPSO spso = (SPSO) userDetails.getUser();
        return ResponseFactory.created("Printer created!", printerService.create(printerDTO, spso));
    }

    @GetMapping
    ResponseEntity<?> findAllPrinter(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) throws BadRequestException {
        return ResponseFactory.success("", printerService.getAllPrinter());
    }

    @GetMapping("/{id}")
    ResponseEntity<?> findPrinterById(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) throws BadRequestException {
        return ResponseFactory.success("Find printer successfully!", printerService.findPrinterById(id));
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updatePrinterById(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody PrinterDTO dto
    ) throws BadRequestException {
        return ResponseFactory.success("Update printer with id " + id + " successfully", printerService.updatePrinter(dto, id));
    }
}
