package com.lamcao1206.hcmut_ssps.service;

import com.lamcao1206.hcmut_ssps.dto.request.CustomerRegisterDTO;
import com.lamcao1206.hcmut_ssps.dto.request.UserLoginDTO;
import com.lamcao1206.hcmut_ssps.dto.response.CustomerResponseDTO;
import com.lamcao1206.hcmut_ssps.dto.response.JwtResponseDTO;
import com.lamcao1206.hcmut_ssps.entity.Customer;
import com.lamcao1206.hcmut_ssps.repository.CustomerRepository;
import com.lamcao1206.hcmut_ssps.security.JwtTokenProvider;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
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
    
    public CustomerResponseDTO registerStudent(CustomerRegisterDTO dto) throws BadRequestException {
        Optional<Customer> customer = customerRepository.findByEmail(dto.email());
        
        if (customer.isPresent()) {
            throw new BadRequestException("Email" + dto.email() + " already registered!");
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

    public JwtResponseDTO authenticateUser(UserLoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.email(),
                        loginDTO.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtTokenProvider.generateToken(userDetails);

        return new JwtResponseDTO(token);
    }
}
