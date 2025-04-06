package com.lamcao1206.hcmut_ssps.service;

import com.lamcao1206.hcmut_ssps.entity.Printer;
import com.lamcao1206.hcmut_ssps.entity.SPSO;
import com.lamcao1206.hcmut_ssps.repository.PrinterRepository;
import com.lamcao1206.hcmut_ssps.repository.SPSORepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

public class PrinterService {
    @Autowired
    PrinterRepository printerRepository;
    
    @Autowired
    SPSORepository spsoRepository;
    
    @Autowired
    SecurityContextHolder securityContextHolder;

    public List<Printer> getAllPrinterManangedBy(Long spsoId)  throws BadRequestException {
        Optional<SPSO> spso = spsoRepository.findById(spsoId);
        
        if (spso.isEmpty()) {
            throw new BadRequestException("SPSO with id " + spsoId + " not found!");
        }
        
        return List.copyOf(spso.get().getPrinters());
    }
}
