package com.lamcao1206.hcmut_ssps.service;

import com.lamcao1206.hcmut_ssps.repository.PrintOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PrintOrderService {
    @Autowired
    PrintOrderRepository printOrderRepository;
    
//    @Autowired
//    SecurityContextHolder securityContextHolder;
    
}
