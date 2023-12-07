package com.example.thietbidientu.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto {
    private Long id;
    @Min(1)
    private int total;
    private Date dateTime;
    private boolean status;
    private Long supplierId;
    private List<DetailInvoiceDto> detail;
}
