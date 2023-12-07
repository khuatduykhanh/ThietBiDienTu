package com.example.thietbidientu.controller;

import com.example.thietbidientu.dto.BillDto;
import com.example.thietbidientu.dto.BillResponse;
import com.example.thietbidientu.dto.InvoiceDto;
import com.example.thietbidientu.dto.InvoiceResponse;
import com.example.thietbidientu.service.BillService;
import com.example.thietbidientu.service.InvoiceService;
import com.example.thietbidientu.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping()
    public ResponseEntity<InvoiceDto> createInvoice(@Valid @RequestBody InvoiceDto invoiceDto){
        return new ResponseEntity<>(invoiceService.createInvoice(invoiceDto), HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{supplierId}")
    public InvoiceResponse getAllBillBySupplierId(@PathVariable("supplierId") Long supplierId, @RequestParam(name = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
                                              @RequestParam(name = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
                                              @RequestParam(name = "sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
                                              @RequestParam(name = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIR,required = false) String sortDir ){
        return invoiceService.getAllInvoiceBySupplierId(supplierId,pageNo,pageSize,sortBy,sortDir);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/invoices")
    public InvoiceResponse getAllInvoice(@RequestParam(name = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
                                   @RequestParam(name = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
                                   @RequestParam(name = "sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
                                   @RequestParam(name = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIR,required = false) String sortDir ){
        return invoiceService.getAllInvoice(pageNo,pageSize,sortBy,sortDir);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{supplierId}/invoice/{invoiceId}")
    public ResponseEntity<InvoiceDto> getInvoiceById(@PathVariable("supplierId") Long supplierId,@PathVariable("invoiceId") Long invoiceId){
        return ResponseEntity.ok(invoiceService.getInvoiceById(supplierId, invoiceId));
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{invoiceId}")
    public ResponseEntity<InvoiceDto> getInvoiceByInvoiceId(@PathVariable("invoiceId") Long invoiceId){
        return ResponseEntity.ok(invoiceService.getInvoiceByInvoiceId(invoiceId));
    }
}
