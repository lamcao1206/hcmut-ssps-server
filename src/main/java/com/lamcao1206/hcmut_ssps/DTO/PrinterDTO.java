package com.lamcao1206.hcmut_ssps.DTO;

import com.lamcao1206.hcmut_ssps.entity.enums.PrinterStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrinterDTO {
    public Long id;
    
    public String name;
    
    public String description;
    
    public String room;
    
    public PrinterStatus status;
    
    public List<PrintOrderDTO> printOrderDTOS;
}
