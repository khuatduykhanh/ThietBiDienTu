package com.example.thietbidientu.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class DetailCartDto {
    private Long id;
    @Min(1)
    private int price;
    private String name;
    @Min(1)
    private int quantity;
    private Long productId;
}
