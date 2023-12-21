package com.example.thietbidientu.controller;

import com.example.thietbidientu.dto.ProductDto;
import com.example.thietbidientu.dto.ProductResponse;
import com.example.thietbidientu.service.ImageService;
import com.example.thietbidientu.service.ProductService;
import com.example.thietbidientu.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ImageService imageService;
    @GetMapping()
    public ProductResponse getAllProduct(@RequestParam(name = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
                                      @RequestParam(name = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
                                      @RequestParam(name = "sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
                                      @RequestParam(name = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIR,required = false) String sortDir // sortDir sắp xếp tăng hay giảm
    ){
        return productService.getAllProduct(pageNo,pageSize,sortBy,sortDir);
    }
    @GetMapping("/v2")
    public ProductResponse getAllProduct2(@RequestParam(name = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
                                         @RequestParam(name = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
                                         @RequestParam(name = "sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
                                         @RequestParam(name = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIR,required = false) String sortDir // sortDir sắp xếp tăng hay giảm
    ){
        return productService.getAllProduct2(pageNo,pageSize,sortBy,sortDir);
    }
    @GetMapping("/category")
    public ProductResponse getAllProductByCategory(@RequestParam(name = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
                                      @RequestParam(name = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
                                      @RequestParam(name = "sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
                                      @RequestParam(name = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIR,required = false) String sortDir, // sortDir sắp xếp tăng hay giảm
                                      @RequestParam(name = "category") String category
    ){
        return productService.getAllProductByCategory(pageNo,pageSize,sortBy,sortDir,category);
    }

    @GetMapping("/brand")
    public ProductResponse getAllProductByBrand(@RequestParam(name = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
                                                @RequestParam(name = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
                                                @RequestParam(name = "sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
                                                @RequestParam(name = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIR,required = false) String sortDir, // sortDir sắp xếp tăng hay giảm
                                                @RequestParam(name = "brand") String brand
    ){
        return productService.getAllProductByBrand(pageNo,pageSize,sortBy,sortDir,brand);
    }
    @GetMapping("/search")
    public ProductResponse searchProduct(@RequestParam(name = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
                                                @RequestParam(name = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
                                                @RequestParam(name = "sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
                                                @RequestParam(name = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIR,required = false) String sortDir, // sortDir sắp xếp tăng hay giảm
                                                @RequestParam(name = "search") String name
    ){
        return productService.searchProduct(pageNo,pageSize,sortBy,sortDir,name);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping()
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) throws IOException {
        return new ResponseEntity<>(productService.createProduct(productDto), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct (@PathVariable("id") Long id,@Valid @RequestBody ProductDto productDto){
        ProductDto newProduct = productService.updateProduct(productDto,id);
        return ResponseEntity.ok(newProduct);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id){
        productService.deleteProduct(id);
        return new ResponseEntity<>("Delete successfully", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{productId}/upload")
    public ResponseEntity<?> uploadImage(@PathVariable("productId") Long productId,@RequestParam("image")MultipartFile file) throws IOException {
        String uploadImage = imageService.uploadImage(productId,file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    @GetMapping("/getImage/{productId}")
    public ResponseEntity<?> downloadImage(@PathVariable Long productId){
        byte[] imageData= imageService.downloadImage(productId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);

    }

}
