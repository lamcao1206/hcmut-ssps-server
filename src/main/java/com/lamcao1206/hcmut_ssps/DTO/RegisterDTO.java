package com.lamcao1206.hcmut_ssps.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    @NotEmpty(message = "Username is required")
    public String name;

    @Email(message = "Invalid Email")
    public String email;
    
    @Min(value = 6)
    public String password;
}
    