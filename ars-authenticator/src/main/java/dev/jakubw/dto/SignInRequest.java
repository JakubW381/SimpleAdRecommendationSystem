package dev.jakubw.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignInRequest (

        @NotBlank(message = "Email cannot be empty.")
        @Email(message = "Email has to be in correct format.")
        String email,

        @NotBlank(message = "Password cannot be empty.")
        @Size(min = 8, max = 100,message = "Password has to be at least 8 characters long")
        String password
){}
