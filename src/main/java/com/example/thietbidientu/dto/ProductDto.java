package com.example.thietbidientu.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long id;
    @NotEmpty(message = "Name cannot be blank")
    private String name;
    @NotEmpty(message = "category cannot be blank")
    private String category;
    @NotEmpty(message = "brand cannot be blank")
    private String brand;
    @NotEmpty(message = "description cannot be blank")
    private String description;
    private String urlImage;
    @Min(1)
    private int price;
    @Min(1)
    private int cost;
    @Min(0)
    private int quantity;
    private boolean status;

}
