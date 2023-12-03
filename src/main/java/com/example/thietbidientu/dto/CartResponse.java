package com.example.thietbidientu.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CartResponse {
    @Min(0)
    private int total;
    @NotEmpty
    private Long userId;
    private List<DetailCartDto> detailCart;
}
