package com.lamcao1206.hcmut_ssps.service;

import com.lamcao1206.hcmut_ssps.DTO.*;
import com.lamcao1206.hcmut_ssps.entity.Customer;
import com.lamcao1206.hcmut_ssps.exception.NotFoundException;
import com.lamcao1206.hcmut_ssps.repository.CustomerRepository;
import com.lamcao1206.hcmut_ssps.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;
    
    @Value("${ssps.customer.default_page}")
    private int DEFAULT_PAGE_BALANCE;
    
    @Autowired
    private ModelMapper modelMapper;
    
    public CustomerDTO registerStudent(RegisterDTO dto) throws BadRequestException {
        Optional<Customer> customer = customerRepository.findByEmail(dto.getEmail());
        
        if (customer.isPresent()) {
            throw new BadRequestException("Email" + dto.getEmail() + " already registered!");
        }
        
        Customer newCustomer = Customer.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .pageBalance(DEFAULT_PAGE_BALANCE)
                .lastLogin(null)
                .build();
        
        customerRepository.save(newCustomer);
        
        return modelMapper.map(newCustomer, CustomerDTO.class);
    }
    
    public CustomerDTO findCustomerById(Long id) throws RuntimeException{
        Optional<Customer> customer = customerRepository.findById(id);
        
        if (customer.isEmpty()) {
            throw new NotFoundException("Customer with id " + id + " not found!");
        }
        
        return modelMapper.map(customer, CustomerDTO.class);
        
    }
}
