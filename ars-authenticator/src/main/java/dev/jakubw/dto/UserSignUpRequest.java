package dev.jakubw.dto;

import dev.jakubw.model.AdTag;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record UserSignUpRequest(

        @NotBlank(message = "Username cannot be empty.")
        @Size(min = 3, max = 32, message = "Username must be between 3 and 32 characters long.")
        String username,

        @NotBlank(message = "Email cannot be empty.")
        @Email(message = "Email has to be in correct format.")
        String email,

        @NotBlank(message = "Password cannot be empty.")
        @Size(min = 8, max = 100,message = "Password has to be at least 8 characters long")
        String password,

        @NotEmpty(message = "Must enter at least 3 tags.")
        @Size(message = "At least 3 tags required.")
        Set<AdTag>tags
){}
