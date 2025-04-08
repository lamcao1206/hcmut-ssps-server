package com.lamcao1206.hcmut_ssps.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseSSPSUserDTO {
    private Long id;
    private String name;
    private String email;
}
