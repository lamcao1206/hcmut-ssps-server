package com.lamcao1206.hcmut_ssps.service;

import com.lamcao1206.hcmut_ssps.DTO.BaseSSPSUserDTO;
import com.lamcao1206.hcmut_ssps.DTO.CustomerDTO;
import com.lamcao1206.hcmut_ssps.DTO.LoginDTO;
import com.lamcao1206.hcmut_ssps.DTO.SpsoDTO;
import com.lamcao1206.hcmut_ssps.entity.BaseSSPSUser;
import com.lamcao1206.hcmut_ssps.entity.Customer;
import com.lamcao1206.hcmut_ssps.entity.SPSO;
import com.lamcao1206.hcmut_ssps.payload.AuthenticationResponse;
import com.lamcao1206.hcmut_ssps.repository.CustomerRepository;
import com.lamcao1206.hcmut_ssps.repository.SPSORepository;
import com.lamcao1206.hcmut_ssps.security.CustomUserDetails;
import com.lamcao1206.hcmut_ssps.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Transactional
public class AuthService {
    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    SPSORepository spsoRepository;
    
    @Autowired
    CustomerRepository customerRepository;
    
    public AuthenticationResponse<?> authenticateUser(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        BaseSSPSUser user = userDetails.getUser();
        String token = jwtTokenProvider.generateToken(userDetails);

        if (user instanceof Customer customer) {
            customer.setLastLogin(new Date());
            customerRepository.save(customer);
        } else if (user instanceof SPSO spso) {
            spso.setLastLogin(new Date());
            spsoRepository.save(spso);
        } else {
            throw new IllegalStateException("Unknown user type");
        }

        BaseSSPSUserDTO userDTO;
        if (user instanceof Customer customer) {
            userDTO = modelMapper.map(customer, CustomerDTO.class);
        } else {
            SPSO spso = (SPSO) user;
            userDTO = modelMapper.map(spso, SpsoDTO.class);
        }

        return new AuthenticationResponse<BaseSSPSUserDTO>(token, userDTO);
    }
}
