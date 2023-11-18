package com.example.thietbidientu.dto;

import com.example.thietbidientu.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillDto {

    private Long id;
    @Min(1)
    private int total;
    private Date dateTime;
    private boolean status;
    private Long userId;
//    private DetailBillResponse detail;
}
