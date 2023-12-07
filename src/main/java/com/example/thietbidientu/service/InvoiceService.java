package com.example.thietbidientu.service;

import com.example.thietbidientu.dto.BillDto;
import com.example.thietbidientu.dto.BillResponse;
import com.example.thietbidientu.dto.InvoiceDto;
import com.example.thietbidientu.dto.InvoiceResponse;

public interface InvoiceService {
    InvoiceDto createInvoice(InvoiceDto invoiceDto);
    InvoiceResponse getAllInvoice(int pageNo, int pageSize, String sortBy, String sortDir);
    InvoiceResponse getAllInvoiceBySupplierId(Long supplierId,int pageNo, int pageSize, String sortBy, String sortDir);
    InvoiceDto getInvoiceById(Long supplierId, Long invoiceId);

    InvoiceDto getInvoiceByInvoiceId(Long invoiceId);
}
