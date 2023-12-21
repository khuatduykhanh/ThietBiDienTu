package com.example.thietbidientu.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdate {
    @NotEmpty(message = "fullname is not empty")
    private String fullname;
    @NotEmpty(message = "address is not empty")
    private String address;
    @NotEmpty(message = "yourphone is not empty")
    private String yourphone;
}
