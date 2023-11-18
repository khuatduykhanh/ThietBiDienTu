package com.example.thietbidientu.service;

import com.example.thietbidientu.dto.SupplierDto;
import com.example.thietbidientu.dto.SupplierResponse;

public interface SupplierService {
    SupplierDto createSupplier(SupplierDto supplierDto);
    SupplierResponse getAllSupplier(int pageNo, int pageSize, String sortBy, String sortDir);
    SupplierDto getSupplierById(Long id);
    SupplierDto getSupplierByName(String name);
    SupplierDto updateSupplier(SupplierDto supplierDto, Long id);
    void deleteSupplier(Long id);


}
