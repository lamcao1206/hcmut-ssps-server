package com.lamcao1206.hcmut_ssps.service;

import com.lamcao1206.hcmut_ssps.dto.request.CustomerRegisterDTO;
import com.lamcao1206.hcmut_ssps.dto.response.CustomerResponseDTO;
import com.lamcao1206.hcmut_ssps.entity.Customer;
import com.lamcao1206.hcmut_ssps.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Value("${ssps.customer.default_page}")
    private int DEFAULT_PAGE_BALANCE;
    
    public CustomerResponseDTO registerStudent(CustomerRegisterDTO dto) throws RuntimeException{
        Optional<Customer> customer = customerRepository.findByEmail(dto.email());
        
        if (customer.isPresent()) {
            throw new RuntimeException("Email" + dto.email() + " already registered!");
        }
        
        Customer newCustomer = Customer.builder()
                .name(dto.name())
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .pageBalance(DEFAULT_PAGE_BALANCE)
                .lastLogin(null)
                .build();
        
        customerRepository.save(newCustomer);
        
        return new CustomerResponseDTO(newCustomer.getId(), newCustomer.getEmail(), newCustomer.getName());
    }
}
