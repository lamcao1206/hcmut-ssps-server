package com.lamcao1206.hcmut_ssps.service;

import com.lamcao1206.hcmut_ssps.DTO.PrintOrderDTO;
import com.lamcao1206.hcmut_ssps.DTO.PrinterDTO;
import com.lamcao1206.hcmut_ssps.entity.Printer;
import com.lamcao1206.hcmut_ssps.entity.SPSO;
import com.lamcao1206.hcmut_ssps.entity.enums.PrinterStatus;
import com.lamcao1206.hcmut_ssps.exception.NotFoundException;
import com.lamcao1206.hcmut_ssps.repository.PrinterRepository;
import com.lamcao1206.hcmut_ssps.repository.SPSORepository;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class PrinterService {
    @Autowired
    PrinterRepository printerRepository;
    
    @Autowired
    SPSORepository spsoRepository;
    
    @Autowired
    ModelMapper modelMapper;
    
    public List<PrinterDTO> getAllPrinter() throws RuntimeException {
        return printerRepository.findAll().stream()
                .map(printer -> new PrinterDTO(
                        printer.getId(),
                        printer.getName(),
                        printer.getDescription(),
                        printer.getRoom(),
                        printer.getStatus(),
                        null
                ))
                .collect(Collectors.toList());
    }
    
    public Printer create(PrinterDTO printerDTO, SPSO spso) throws RuntimeException, BadRequestException {
        Optional<Printer> printerHolder = printerRepository.findByName(printerDTO.getName());
        
        if (printerHolder.isPresent()) {
            throw new BadRequestException("Printer with name " + printerDTO.getName() + " exists!");
        }
        
        Printer printer = Printer.builder()
                .name(printerDTO.getName())
                .description(printerDTO.getDescription())
                .room(printerDTO.getRoom())
                .status(PrinterStatus.ON)
                .build();
        
        return printerRepository.save(printer);
    }
    
    public PrinterDTO findPrinterById(Long printerId) throws RuntimeException {
        Optional<Printer> printer = printerRepository.findById(printerId);
        
        if (printer.isEmpty()) {
            throw new NotFoundException("Printer with id " + printerId + " not found!");
        }

        List<PrintOrderDTO> printOrderDTOS =  printer.get().getPrintOrders().stream()
                .map(order -> modelMapper.map(order, PrintOrderDTO.class))
                .toList();
        
        return new PrinterDTO(
                printer.get().getId(),
                printer.get().getName(),
                printer.get().getDescription(),
                printer.get().getRoom(),
                printer.get().getStatus(),
                printOrderDTOS
        );
    }
    
    public PrinterDTO updatePrinter(PrinterDTO printerDTO, Long id) throws RuntimeException {
        Optional<Printer> printerOptional = printerRepository.findById(id);
        
        if (printerOptional.isEmpty()) {
            throw new NotFoundException("Printer with id " + printerDTO.getId() + " not found");
        }
        
        Printer printer = printerOptional.get();
        printer.setDescription(printerDTO.getDescription());
        printer.setRoom(printerDTO.getRoom());
        printer.setStatus(printerDTO.getStatus());
        
        printerRepository.save(printer);
        
        return new PrinterDTO(
                printer.getId(),
                printer.getName(),
                printer.getDescription(),
                printer.getRoom(),
                printer.getStatus(),
                null
        );
        
    }
}
