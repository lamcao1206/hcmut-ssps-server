package com.lamcao1206.hcmut_ssps.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpsoDTO extends BaseSSPSUserDTO {
    @NotEmpty(message = "Username is required")
    public String name;

    @Email(message = "Invalid Email")
    public String email;

    @NotNull(message = "page balance required!")
    public int pageBalance;

    public Date lastLogin;
}
