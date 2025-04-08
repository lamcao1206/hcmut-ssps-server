package com.lamcao1206.hcmut_ssps.service;

import com.lamcao1206.hcmut_ssps.DTO.*;
import com.lamcao1206.hcmut_ssps.entity.BaseSSPSUser;
import com.lamcao1206.hcmut_ssps.entity.Customer;
import com.lamcao1206.hcmut_ssps.entity.SPSO;
import com.lamcao1206.hcmut_ssps.payload.AuthenticationResponse;
import com.lamcao1206.hcmut_ssps.repository.SPSORepository;
import com.lamcao1206.hcmut_ssps.security.CustomUserDetails;
import com.lamcao1206.hcmut_ssps.security.JwtTokenProvider;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SPSOService {
    @Autowired
    private SPSORepository spsoRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    
    @Autowired
    private ModelMapper modelMapper;
    
    public SpsoDTO registerSPSO(RegisterDTO dto) throws Exception {
        Optional<SPSO> spso = spsoRepository.findByEmail(dto.getEmail());
        
        if (spso.isPresent()) {
            throw new BadRequestException("Email " + dto.getEmail() + " already registered!");
        }
        
        SPSO newSPSO = SPSO.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .lastLogin(null)
                .build();
        
        spsoRepository.save(newSPSO);

        return modelMapper.map(newSPSO, SpsoDTO.class);
    }

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

        BaseSSPSUserDTO userDTO;
        if (user instanceof Customer customer) {
            userDTO = modelMapper.map(customer, CustomerDTO.class);
        } else if (user instanceof SPSO spso) {
            userDTO = modelMapper.map(spso, SpsoDTO.class);
        } else {
            throw new IllegalStateException("Unknown user type");
        }

        return new AuthenticationResponse<BaseSSPSUserDTO>(token, userDTO);
    }
}
