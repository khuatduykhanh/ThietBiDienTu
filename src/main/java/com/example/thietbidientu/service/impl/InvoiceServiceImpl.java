package com.example.thietbidientu.service.impl;

import com.example.thietbidientu.dto.*;
import com.example.thietbidientu.entity.*;
import com.example.thietbidientu.exception.APIException;
import com.example.thietbidientu.exception.ResourceNotFoundException;
import com.example.thietbidientu.repository.DetailInvoiceRepository;
import com.example.thietbidientu.repository.InvoiceRepository;
import com.example.thietbidientu.repository.ProductRepository;
import com.example.thietbidientu.repository.SupplierRepository;
import com.example.thietbidientu.service.InvoiceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private DetailInvoiceRepository detailInvoiceRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public InvoiceDto createInvoice( InvoiceDto invoiceDto) {
        int total = invoiceDto.getTotal();
        int totalDetail = 0;
        List<DetailInvoiceDto> detail = invoiceDto.getDetail();
        for (int i = 0; i < detail.toArray().length; i++) {
            totalDetail = totalDetail + (detail.get(i).getPrice() * detail.get(i).getQuantity());
        }
        if( total != totalDetail ){
            throw new APIException(HttpStatus.BAD_REQUEST,"Please check again");
        }
        Invoice invoice = new Invoice();
        invoice.setStatus(invoiceDto.isStatus());
        invoice.setTotal(invoiceDto.getTotal());
        Date currentDate = new Date();
        invoice.setDateTime(currentDate);
        Supplier supplier = supplierRepository.findById(invoiceDto.getSupplierId()).orElseThrow(()-> new ResourceNotFoundException("Supplier","id",String.valueOf(invoiceDto.getSupplierId())));
        invoice.setSupplier(supplier);
        Invoice newInvoice = invoiceRepository.save(invoice);
        InvoiceDto newDto = new InvoiceDto();
        newDto.setId(newInvoice.getId());
        newDto.setTotal(newInvoice.getTotal());
        newDto.setStatus(newInvoice.isStatus());
        newDto.setDateTime(newInvoice.getDateTime());
        newDto.setSupplierId(newInvoice.getSupplier().getId());
        List<DetailInvoiceDto> newDetail = new ArrayList<>();;
        for (DetailInvoiceDto detailInvoice :detail) {
            Long productId = detailInvoice.getProductId();
            Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("product","id",String.valueOf(productId)));
            DetailInvoice db = convertDetailInvoice(detailInvoice);
            db.setProduct4(product);
            db.setInvoice(newInvoice);
            DetailInvoice newDb = detailInvoiceRepository.save(db);
            newDetail.add(convertDetailInvoiceDto(newDb));
        }
        newDto.setDetail(newDetail);
        return newDto;
    }

    @Override
    public InvoiceResponse getAllInvoice(int pageNo, int pageSize, String sortBy, String sortDir) {
        // câu lệnh này sẽ xem xét sortDir là asc hay des
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);  // set up số trang, số lượng trong một trang và sắp xếp theo miền nào

        Page<Invoice> invoiceList =  invoiceRepository.findAll(pageable); // findAll với số trang và số lượng trang sẽ trong về Page

        List<Invoice> listOfBill = invoiceList.getContent(); // lấy danh sách trang đã được phân trong postList

        List<InvoiceDto> content = listOfBill.stream().map(this::convertInvoiceDto).toList();
        for (InvoiceDto invoiceDto:content ) {
            List<DetailInvoice> detailInvoice =  detailInvoiceRepository.findAllByInvoiceId(invoiceDto.getId());
            List<DetailInvoiceDto> detail = new ArrayList<>();
            for (DetailInvoice detailinvoice : detailInvoice) {
                detail.add(convertDetailInvoiceDto(detailinvoice));
            }
            invoiceDto.setDetail(detail);
        }
        InvoiceResponse invoiceResponse = new InvoiceResponse();
        invoiceResponse.setContent(content);
        invoiceResponse.setPageNo(pageNo);
        invoiceResponse.setPageSize(pageSize);
        invoiceResponse.setTotalElement(invoiceList.getTotalElements());
        invoiceResponse.setTotalPage(invoiceList.getTotalPages());
        invoiceResponse.setLast(invoiceList.isLast());
        return invoiceResponse;
    }

    @Override
    public InvoiceResponse getAllInvoiceBySupplierId(Long supplierId, int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Invoice> invoiceList =  invoiceRepository.findAllBySupplierId(pageable,supplierId);

        List<Invoice> listOfInvoice = invoiceList.getContent();

        List<InvoiceDto> content = listOfInvoice.stream().map(this::convertInvoiceDto).toList();
        for (InvoiceDto invoiceDto:content ) {
            List<DetailInvoice> detailInvoice =  detailInvoiceRepository.findAllByInvoiceId(invoiceDto.getId());
            List<DetailInvoiceDto> detail = new ArrayList<>();
            for (DetailInvoice detailinvoice : detailInvoice) {
                detail.add(convertDetailInvoiceDto(detailinvoice));
            }
            invoiceDto.setDetail(detail);
        }
        InvoiceResponse invoiceResponse = new InvoiceResponse();
        invoiceResponse.setContent(content);
        invoiceResponse.setPageNo(pageNo);
        invoiceResponse.setPageSize(pageSize);
        invoiceResponse.setTotalElement(invoiceList.getTotalElements());
        invoiceResponse.setTotalPage(invoiceList.getTotalPages());
        invoiceResponse.setLast(invoiceList.isLast());
        return invoiceResponse;
    }

    @Override
    public InvoiceDto getInvoiceById(Long supplierId, Long invoiceId) {
        Supplier supplier = supplierRepository.findById(supplierId).orElseThrow(()-> new ResourceNotFoundException("Supplier","id",String.valueOf(supplierId)));
        Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow(()-> new ResourceNotFoundException("Invoice","id",String.valueOf(invoiceId)));
        List<DetailInvoice> detailInvoice =  detailInvoiceRepository.findAllByInvoiceId(invoiceId);
        List<DetailInvoiceDto> detail = new ArrayList<>();
        for (DetailInvoice detailinvoice : detailInvoice) {
            detail.add(convertDetailInvoiceDto(detailinvoice));
        }
        InvoiceDto invoiceDto =  convertInvoiceDto(invoice);
        invoiceDto.setDetail(detail);
        if(!invoice.getSupplier().getId().equals(supplier.getId())){
            throw new APIException(HttpStatus.BAD_REQUEST,"Invoice does not belong to supplier");
        }
        return invoiceDto;
    }

    @Override
    public InvoiceDto getInvoiceByInvoiceId(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow(()-> new ResourceNotFoundException("Invoice","id",String.valueOf(invoiceId)));
        List<DetailInvoice> detailInvoice =  detailInvoiceRepository.findAllByInvoiceId(invoiceId);
        List<DetailInvoiceDto> detail = new ArrayList<>();
        for (DetailInvoice detailinvoice : detailInvoice) {
            detail.add(convertDetailInvoiceDto(detailinvoice));
        }
        InvoiceDto invoiceDto =  convertInvoiceDto(invoice);
        invoiceDto.setDetail(detail);
        return invoiceDto;
    }


    private InvoiceDto convertInvoiceDto(Invoice invoice){
        return modelMapper.map(invoice,InvoiceDto.class);
    }
    private Invoice convertInvoice(InvoiceDto invoiceDto){
        return modelMapper.map(invoiceDto,Invoice.class);
    }
    private DetailInvoice convertDetailInvoice(DetailInvoiceDto detailInvoiceDto){
        return modelMapper.map(detailInvoiceDto,DetailInvoice.class);
    }
    private DetailInvoiceDto convertDetailInvoiceDto(DetailInvoice detailInvoice){
        return modelMapper.map(detailInvoice,DetailInvoiceDto.class);
    }
}
