package com.example.thietbidientu.controller;

import com.example.thietbidientu.dto.BillDto;
import com.example.thietbidientu.dto.BillResponse;
import com.example.thietbidientu.service.BillService;
import com.example.thietbidientu.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BillController {
    @Autowired
    private BillService billService;
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/user/{userId}/bill")
    public ResponseEntity<BillDto> createBill(@PathVariable("userId") Long userId, @Valid @RequestBody BillDto billDto){
        return new ResponseEntity<>(billService.createBill(userId, billDto), HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ROLE_USER') and hasRole('ROLE_ADMIN')")
    @GetMapping("/user/{userId}/bills")
    public BillResponse getAllBillByUserId(@PathVariable("userId") Long userId, @RequestParam(name = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
                                              @RequestParam(name = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
                                              @RequestParam(name = "sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
                                              @RequestParam(name = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIR,required = false) String sortDir ){
        return billService.getAllBillByUserId(userId,pageNo,pageSize,sortBy,sortDir);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/bills")
    public BillResponse getAllBill(@RequestParam(name = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
                                              @RequestParam(name = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
                                              @RequestParam(name = "sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
                                              @RequestParam(name = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIR,required = false) String sortDir ){
        return billService.getAllBill(pageNo,pageSize,sortBy,sortDir);
    }
    @PreAuthorize("hasRole('ROLE_USER') and hasRole('ROLE_ADMIN')")
    @GetMapping("/user/{userId}/bill/{billId}")
    public ResponseEntity<BillDto> getBillById(@PathVariable("userId") Long userId,@PathVariable("billId") Long billId){
        return ResponseEntity.ok(billService.getBillById(userId, billId));
    }

//    @PutMapping("/user/{userId}/bill/{billId}")
//    public ResponseEntity<BillDto> updateBill(@PathVariable("userId") Long userId,@PathVariable("billId") Long billId,@Valid @RequestBody BillDto billDto){
//        return ResponseEntity.ok(billService.updateBill(userId, billId, billDto));
//    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/user/{userId}/bill/{billId}")
    public ResponseEntity<String> deleteComment(@PathVariable("userId") Long userId,@PathVariable("billId") Long billId){
        billService.deleteBill(userId,billId);
        return ResponseEntity.ok("Delete successfully");
    }

}
