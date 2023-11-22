package com.example.thietbidientu.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailDto {

    private Long id;
    @Min(1)
    private int price;
    @Min(1)
    private int quantity;
    @NotEmpty
    private Long productId;
    @NotEmpty
    private Long billId;
}
