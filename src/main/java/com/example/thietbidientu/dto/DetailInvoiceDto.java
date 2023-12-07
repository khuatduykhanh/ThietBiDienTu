package com.example.thietbidientu.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailInvoiceDto {
    private Long id;
    @Min(1)
    private int price;
    @Min(1)
    private int quantity;
    private Long productId;
    private Long invoiceId;
}
