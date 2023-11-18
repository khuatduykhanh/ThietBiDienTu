package com.example.thietbidientu.service.impl;

import com.example.thietbidientu.dto.SupplierDto;
import com.example.thietbidientu.dto.SupplierResponse;
import com.example.thietbidientu.entity.Supplier;
import com.example.thietbidientu.exception.ResourceNotFoundException;
import com.example.thietbidientu.repository.SupplierRepository;
import com.example.thietbidientu.service.SupplierService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public SupplierDto createSupplier(SupplierDto supplierDto) {
        Supplier supplier = convertSupplier(supplierDto);
        Supplier newSupplier = supplierRepository.save(supplier);
        return convertSupplierDto(newSupplier);
    }

    @Override
    public SupplierResponse getAllSupplier(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Supplier> supplierList =  supplierRepository.findAll(pageable);

        List<Supplier> listOfSupplier = supplierList.getContent();

        List<SupplierDto> content = listOfSupplier.stream().map(this::convertSupplierDto).toList();
        SupplierResponse supplierResponse = new  SupplierResponse();
        supplierResponse.setContent(content);
        supplierResponse.setPageNo(pageNo);
        supplierResponse.setPageSize(pageSize);
        supplierResponse.setTotalElement(supplierList.getTotalElements());
        supplierResponse.setTotalPage(supplierList.getTotalPages());
        supplierResponse.setLast(supplierList.isLast());
        return supplierResponse;
    }

    @Override
    public SupplierDto getSupplierById(Long id) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Supplier","id",String.valueOf(id)));
        return convertSupplierDto(supplier);
    }

    @Override
    public SupplierDto getSupplierByName(String name) {
        Supplier supplier = supplierRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Supplier","name",name));
        return convertSupplierDto(supplier);
    }

    @Override
    public SupplierDto updateSupplier(SupplierDto supplierDto, Long id) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Supplier","id",String.valueOf(id)));
        supplier.setName(supplierDto.getName());
        supplier.setAddress(supplierDto.getAddress());
        supplier.setContact(supplierDto.getContact());
        Supplier newSupplier = supplierRepository.save(supplier);
        return convertSupplierDto(supplier);
    }

    @Override
    public void deleteSupplier(Long id) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Supplier","id",String.valueOf(id)));
        supplierRepository.delete(supplier);
    }

    private Supplier convertSupplier(SupplierDto supplierDto) {
        return modelMapper.map(supplierDto,Supplier.class);
    }
    private SupplierDto convertSupplierDto(Supplier supplier) {
        return modelMapper.map(supplier,SupplierDto.class);
    }
}
