package com.lamcao1206.hcmut_ssps.service;

import com.lamcao1206.hcmut_ssps.DTO.SystemConfigDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DefaultPropertiesPersister;

import java.io.*;
import java.util.Properties;

@Service
public class SystemConfigService {
    @Value("${ssps.file.allow_extensions}")
    private String allowExtensions;
    
    @Value("${ssps.customer.default_page}")
    private int defaultPage;

    private static final String PROPERTIES_FILE = "src/main/resources/application.properties";
    
    public SystemConfigDTO getSystemConfig() {
        return new SystemConfigDTO(defaultPage, allowExtensions);
    }

    public SystemConfigDTO modifySystemConfig(SystemConfigDTO dto) throws IOException {
        
        Properties props = new Properties();
        File file = new File(PROPERTIES_FILE);

        // Load existing properties
        try (InputStream inputStream = new FileInputStream(file)) {
            props.load(inputStream);
        }

        // Modify or add properties
        props.setProperty("ssps.customer.default_page", String.valueOf(dto.getPage()));
        props.setProperty("ssps.file.allow_extensions", dto.getAllowExtentions());

        // Save updated properties
        try (OutputStream outputStream = new FileOutputStream(file)) {
            DefaultPropertiesPersister persister = new DefaultPropertiesPersister();
            persister.store(props, outputStream, "Updated by SystemConfigService");
        }

        return dto;
    }
}
