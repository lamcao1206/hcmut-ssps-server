package com.lamcao1206.hcmut_ssps.service;

import com.lamcao1206.hcmut_ssps.dto.request.CustomerRegisterDTO;
import com.lamcao1206.hcmut_ssps.dto.request.UserLoginDTO;
import com.lamcao1206.hcmut_ssps.dto.response.JwtResponseDTO;
import com.lamcao1206.hcmut_ssps.dto.response.SPSOResponseDTO;
import com.lamcao1206.hcmut_ssps.entity.SPSO;
import com.lamcao1206.hcmut_ssps.repository.SPSORepository;
import com.lamcao1206.hcmut_ssps.security.JwtTokenProvider;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    
    public SPSOResponseDTO registerSPSO(CustomerRegisterDTO dto) throws Exception {
        Optional<SPSO> spso = spsoRepository.findByEmail(dto.email());
        
        if (spso.isPresent()) {
            throw new BadRequestException("Email " + dto.email() + " already registered!");
        }
        
        SPSO newSPSO = SPSO.builder()
                .email(dto.email())
                .name(dto.name())
                .password(passwordEncoder.encode(dto.password()))
                .lastLogin(null)
                .build();
        
        spsoRepository.save(newSPSO);
        
        return new SPSOResponseDTO(newSPSO.getId(), newSPSO.getEmail(), newSPSO.getName());
    }
    
    public JwtResponseDTO authenticateSPSO(UserLoginDTO loginDTO) throws Exception {
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
