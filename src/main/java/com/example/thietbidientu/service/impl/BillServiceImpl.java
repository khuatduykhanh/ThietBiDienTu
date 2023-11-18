package com.example.thietbidientu.service.impl;

import com.example.thietbidientu.dto.*;
import com.example.thietbidientu.entity.Bill;
import com.example.thietbidientu.entity.Product;
import com.example.thietbidientu.entity.User;
import com.example.thietbidientu.exception.APIException;
import com.example.thietbidientu.exception.ResourceNotFoundException;
import com.example.thietbidientu.repository.BillRepository;
import com.example.thietbidientu.repository.UserRepository;
import com.example.thietbidientu.service.BillService;
//import com.example.thietbidientu.service.DetailBillService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BillServiceImpl implements BillService {
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    private DetailBillService detailBillService;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public BillDto createBill(Long userId, BillDto billDto) {
        Bill bill = convertBill(billDto);
        Date currentDate = new Date();
        bill.setDateTime(currentDate);
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",String.valueOf(userId)));
        bill.setUser(user);
        Bill newBill = billRepository.save(bill);
        return convertBillDto(newBill);
    }

    @Override
    public BillResponse getAllBill(int pageNo, int pageSize, String sortBy, String sortDir) {
        // câu lệnh này sẽ xem xét sortDir là asc hay des
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);  // set up số trang, số lượng trong một trang và sắp xếp theo miền nào

        Page<Bill> billList =  billRepository.findAll(pageable); // findAll với số trang và số lượng trang sẽ trong về Page

        List<Bill> listOfBill = billList.getContent(); // lấy danh sách trang đã được phân trong postList

        List<BillDto> content = listOfBill.stream().map(this::convertBillDto).toList();
//        for (BillDto billDto:content ) {
//            DetailBillResponse detailRes = detailBillService.getAllDetailByBillId(billDto.getId(),pageNo,pageSize,sortBy,sortDir);
//            billDto.setDetail(detailRes);
//        }
        BillResponse billResponse = new  BillResponse();
        billResponse.setContent(content);
        billResponse.setPageNo(pageNo);
        billResponse.setPageSize(pageSize);
        billResponse.setTotalElement(billList.getTotalElements());
        billResponse.setTotalPage(billList.getTotalPages());
        billResponse.setLast(billList.isLast());
        return billResponse;
    }

    @Override
    public BillResponse getAllBillByUserId(Long userId,int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Bill> billList =  billRepository.findAllByUserId(pageable,userId);

        List<Bill> listOfBill = billList.getContent();

        List<BillDto> content = listOfBill.stream().map(this::convertBillDto).toList();
//        for (BillDto billDto:content ) {
//            DetailBillResponse detailRes = detailBillService.getAllDetailByBillId(billDto.getId(),pageNo,pageSize,sortBy,sortDir);
//            billDto.setDetail(detailRes);
//        }
        System.out.println("content"+content);
        BillResponse billResponse = new  BillResponse();
        billResponse.setContent(content);
        billResponse.setPageNo(pageNo);
        billResponse.setPageSize(pageSize);
        billResponse.setTotalElement(billList.getTotalElements());
        billResponse.setTotalPage(billList.getTotalPages());
        billResponse.setLast(billList.isLast());
        return billResponse;
    }


    @Override
    public BillDto getBillById(Long userId, Long billId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",String.valueOf(userId)));
        Bill bill = billRepository.findById(billId).orElseThrow(()-> new ResourceNotFoundException("Bill","id",String.valueOf(billId)));
        if(!bill.getUser().getId().equals(user.getId())){
            throw new APIException(HttpStatus.BAD_REQUEST,"Bill does not belong to user");
        }
        return convertBillDto(bill);
    }

    @Override
    public BillDto updateBill(Long userId, Long billId, BillDto billDto) {
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",String.valueOf(userId)));
        Bill bill = billRepository.findById(billId).orElseThrow(()-> new ResourceNotFoundException("Bill","id",String.valueOf(billId)));
        if(!bill.getUser().getId().equals(user.getId())){
            throw new APIException(HttpStatus.BAD_REQUEST,"Bill does not belong to user");
        }
        bill.setTotal(billDto.getTotal());
        Date currentDate = new Date();
        bill.setDateTime(currentDate);
        bill.setStatus(billDto.isStatus());
        Bill newBill = billRepository.save(bill);
        return convertBillDto(newBill);
    }

    @Override
    public void deleteBill(Long userId, Long billId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",String.valueOf(userId)));
        Bill bill = billRepository.findById(billId).orElseThrow(()-> new ResourceNotFoundException("Bill","id",String.valueOf(billId)));
        if(!bill.getUser().getId().equals(user.getId())){
            throw new APIException(HttpStatus.BAD_REQUEST,"Bill does not belong to user");
        }
        billRepository.delete(bill);
    }

    private Bill convertBill(BillDto billDto){
        return modelMapper.map(billDto,Bill.class);
    }
    private BillDto convertBillDto(Bill bill){
        return modelMapper.map(bill,BillDto.class);
    }
}
