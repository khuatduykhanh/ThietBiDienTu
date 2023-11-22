package com.example.thietbidientu.service;

import com.example.thietbidientu.dto.BillDto;
import com.example.thietbidientu.dto.BillResponse;

public interface BillService {
    BillDto createBill(Long userId, BillDto billDto);
    BillResponse getAllBill(int pageNo, int pageSize, String sortBy, String sortDir);
    BillResponse getAllBillByUserId(Long userId,int pageNo, int pageSize, String sortBy, String sortDir);
    BillDto getBillById(Long userId, Long billId);
//    BillDto updateBill(Long userId, Long billId, BillDto billDto);
    void deleteBill(Long userId, Long billId);
}
