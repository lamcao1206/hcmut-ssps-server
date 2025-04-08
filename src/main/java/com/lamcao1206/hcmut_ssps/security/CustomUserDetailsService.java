package com.lamcao1206.hcmut_ssps.security;

import com.lamcao1206.hcmut_ssps.entity.Customer;
import com.lamcao1206.hcmut_ssps.entity.SPSO;
import com.lamcao1206.hcmut_ssps.repository.CustomerRepository;
import com.lamcao1206.hcmut_ssps.repository.SPSORepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

// Username in CustomUserDetail is email in SPSO and Customer entities
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private SPSORepository spsoRepository;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private HttpServletRequest request;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String requestUrl = request.getRequestURI();
        if (requestUrl.contains("spso")) {
            Optional<SPSO> spso = spsoRepository.findByEmail(username);
            if (spso.isPresent()) {
                return new CustomUserDetails(
                        spso.get(),
                        spso.get().getPassword(),
                        "ROLE_SPSO"
                );
            }
        } else if (requestUrl.contains("customer")) {
            Optional<Customer> customer = customerRepository.findByEmail(username);
            if (customer.isPresent()) {
                return new CustomUserDetails(
                        customer.get(),
                        customer.get().getPassword(),
                        "ROLE_CUSTOMER"
                );
            }
        }

        Optional<SPSO> spso = spsoRepository.findByEmail(username);
        if (spso.isPresent()) {
            return new CustomUserDetails(
                    spso.get(),
                    spso.get().getPassword(),
                    "ROLE_SPSO"
            );
        }

        Optional<Customer> customer = customerRepository.findByEmail(username);
        if (customer.isPresent()) {
            return new CustomUserDetails(
                    customer.get(),
                    customer.get().getPassword(),
                    "ROLE_CUSTOMER"
            );
        }
        
        throw new UsernameNotFoundException("User not found with email: " + username);
    }
}
