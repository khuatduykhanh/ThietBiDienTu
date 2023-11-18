package com.example.thietbidientu.controller;

import com.example.thietbidientu.dto.ProductDto;
import com.example.thietbidientu.dto.ProductResponse;
import com.example.thietbidientu.dto.SupplierDto;
import com.example.thietbidientu.dto.SupplierResponse;
import com.example.thietbidientu.service.SupplierService;
import com.example.thietbidientu.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @GetMapping()
    public SupplierResponse getAllSupplier(@RequestParam(name = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
                                           @RequestParam(name = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
                                           @RequestParam(name = "sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
                                           @RequestParam(name = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIR,required = false) String sortDir // sortDir sắp xếp tăng hay giảm
    ){
        return supplierService.getAllSupplier(pageNo,pageSize,sortBy,sortDir);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<SupplierDto> createSupplier(@Valid @RequestBody SupplierDto supplierDto){
        return new ResponseEntity<>(supplierService.createSupplier(supplierDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierDto> getSupplierById(@PathVariable("id") Long id){
        return ResponseEntity.ok(supplierService.getSupplierById(id));
    }
    @GetMapping("/name/{name}")
    public ResponseEntity<SupplierDto> getSupplierByName(@PathVariable("name") String name){
        return ResponseEntity.ok(supplierService.getSupplierByName(name));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<SupplierDto> updateSupplier(@PathVariable("id") Long id,@Valid @RequestBody SupplierDto supplierDto){
        SupplierDto newSupplier = supplierService.updateSupplier(supplierDto,id);
        return ResponseEntity.ok(newSupplier);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSupplier(@PathVariable("id") Long id){
        supplierService.deleteSupplier(id);
        return new ResponseEntity<>("Delete successfully", HttpStatus.OK);
    }
}
