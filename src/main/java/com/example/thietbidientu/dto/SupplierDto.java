package com.example.thietbidientu.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierDto {

    private Long id;
    @NotEmpty()
    @Size(min = 1,message = "Please re-enter the name")
    private String name;
    @NotEmpty()
    @Size(min = 1,message = "Please re-enter the address")
    private String address;
    @NotEmpty()
    @Size(min = 1,message = "Please re-enter the contact")
    private String contact;
}
