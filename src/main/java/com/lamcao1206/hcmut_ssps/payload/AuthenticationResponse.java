package com.lamcao1206.hcmut_ssps.payload;

import com.lamcao1206.hcmut_ssps.DTO.BaseSSPSUserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthenticationResponse<T extends BaseSSPSUserDTO> {
    private String token;
    private T user;
}
