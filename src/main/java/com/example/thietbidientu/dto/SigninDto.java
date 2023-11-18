package com.example.thietbidientu.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SigninDto {
    @NotEmpty(message = "name is not empty")
    private String fullname;
    @NotEmpty(message = "username is not empty")
    private String email;
    @Email(message = "not email")
    private String address;
    @NotEmpty(message = "username is not empty")
    private String yourphone;
    @NotEmpty
    @Size( min = 8, message = "password should have at least 10 characters")
    private String password;
}

