package com.api.zoo.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserRequestDto {
 
    @Email(message = "Email is not valid", regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
    @NotBlank(message = "The email cannot be blank")
    @NotEmpty(message = "The email cannot be empty")
    private String email;

    @NotBlank(message = "The password cannot be blank")
    @NotEmpty(message = "The password cannot be empty")
    private String password;
}
