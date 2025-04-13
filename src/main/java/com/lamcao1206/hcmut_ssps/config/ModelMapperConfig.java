package com.lamcao1206.hcmut_ssps.config;

import com.lamcao1206.hcmut_ssps.DTO.PrinterDTO;
import com.lamcao1206.hcmut_ssps.entity.Printer;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        
        // Create some mapping rules
        modelMapper.typeMap(Printer.class, PrinterDTO.class)
                .addMappings(mapper -> mapper.skip(PrinterDTO::setPrintOrderDTOS));
        
        return modelMapper;
    }
}
