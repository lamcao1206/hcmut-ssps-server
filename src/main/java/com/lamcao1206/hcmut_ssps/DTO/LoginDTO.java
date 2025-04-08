package com.lamcao1206.hcmut_ssps.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    private String email;
    
    @NotBlank(message = "Password is required")
    @Min(value = 5, message = "Password must have at least 5 characters!")
    private String password;
}
