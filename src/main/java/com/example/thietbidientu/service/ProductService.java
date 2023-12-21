package com.example.thietbidientu.service;

import com.example.thietbidientu.dto.ProductDto;
import com.example.thietbidientu.dto.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    ProductResponse getAllProduct(int pageNo, int pageSize, String sortBy, String sortDir);
    ProductResponse getAllProduct2(int pageNo, int pageSize, String sortBy, String sortDir);
    ProductResponse getAllProductByCategory(int pageNo, int pageSize, String sortBy, String sortDir, String category);
    ProductResponse getAllProductByBrand(int pageNo, int pageSize, String sortBy, String sortDir, String brand);
    ProductDto getProductById(Long id);
    ProductResponse searchProduct(int pageNo, int pageSize, String sortBy, String sortDir, String name);
    ProductDto updateProduct(ProductDto postDto,Long id);
    void  deleteProduct(Long id);

}
