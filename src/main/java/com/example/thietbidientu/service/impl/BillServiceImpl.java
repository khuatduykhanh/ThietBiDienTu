package com.example.thietbidientu.service.impl;

import com.example.thietbidientu.dto.*;
import com.example.thietbidientu.entity.Bill;
import com.example.thietbidientu.entity.DetailBill;
import com.example.thietbidientu.entity.Product;
import com.example.thietbidientu.entity.User;
import com.example.thietbidientu.exception.APIException;
import com.example.thietbidientu.exception.ResourceNotFoundException;
import com.example.thietbidientu.repository.BillRepository;
import com.example.thietbidientu.repository.DetailBillRepository;
import com.example.thietbidientu.repository.ProductRepository;
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

import java.util.*;

@Service
public class BillServiceImpl implements BillService {
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DetailBillRepository detailBillRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public BillDto createBill(Long userId, BillDto billDto) {
        int total = billDto.getTotal();
        int totalDetail = 0;
        List<DetailDto> detail = billDto.getDetail();
        for (int i = 0; i < detail.toArray().length; i++) {
            totalDetail = totalDetail + (detail.get(i).getPrice() * detail.get(i).getQuantity());
        }
        if( total != totalDetail ){
            throw new APIException(HttpStatus.BAD_REQUEST,"Please check again");
        }
        Bill bill = new Bill();
        bill.setStatus(billDto.isStatus());
        bill.setTotal(billDto.getTotal());
        Date currentDate = new Date();
        bill.setDateTime(currentDate);
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",String.valueOf(userId)));
        bill.setUser(user);
        Bill newBill = billRepository.save(bill);
        BillDto newDto = new BillDto();
        newDto.setId(newBill.getId());
        newDto.setTotal(newBill.getTotal());
        newDto.setStatus(newBill.isStatus());
        newDto.setDateTime(newBill.getDateTime());
        newDto.setUserId(newBill.getUser().getId());
        List<DetailDto> newDetail = new ArrayList<>();;
        for (DetailDto detailBill :detail) {
            Long productId = detailBill.getProductId();
           Product product = productRepository.find(productId);
            DetailBill db = convertDetailBill(detailBill);
            db.setProduct2(product);
            db.setBill(newBill);
            DetailBill newdb = detailBillRepository.save(db);
            newDetail.add(convertDetailDto(newdb));
        }
        newDto.setDetail(newDetail);
        return newDto;
    }

    @Override
    public BillResponse getAllBill(int pageNo, int pageSize, String sortBy, String sortDir) {
        // câu lệnh này sẽ xem xét sortDir là asc hay des
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);  // set up số trang, số lượng trong một trang và sắp xếp theo miền nào

        Page<Bill> billList =  billRepository.findAll(pageable); // findAll với số trang và số lượng trang sẽ trong về Page

        List<Bill> listOfBill = billList.getContent(); // lấy danh sách trang đã được phân trong postList

        List<BillDto> content = listOfBill.stream().map(this::convertBillDto).toList();
        for (BillDto billDto:content ) {
            List<DetailBill> detailBill =  detailBillRepository.findAllByBillId(billDto.getId());
            List<DetailDto> detail = new ArrayList<>();
            for (DetailBill detailbill : detailBill) {
                detail.add(convertDetailDto(detailbill));
            }
            billDto.setDetail(detail);
        }
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
        for (BillDto billDto:content ) {
            List<DetailBill> detailBill =  detailBillRepository.findAllByBillId(billDto.getId());
            System.out.println("detailBill" +detailBill );
            List<DetailDto> detail = new ArrayList<>();
            for (DetailBill detailbill : detailBill) {
                detail.add(convertDetailDto(detailbill));
            }
            billDto.setDetail(detail);
        }
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
        List<DetailBill> detailBill =  detailBillRepository.findAllByBillId(billId);
        List<DetailDto> detail = new ArrayList<>();
        for (DetailBill detailbill : detailBill) {
            detail.add(convertDetailDto(detailbill));
        }
        BillDto billDto =  convertBillDto(bill);
        billDto.setDetail(detail);
        if(!bill.getUser().getId().equals(user.getId())){
            throw new APIException(HttpStatus.BAD_REQUEST,"Bill does not belong to user");
        }
        return billDto;
    }

//    @Override
//    public BillDto updateBill(Long userId, Long billId, BillDto billDto) {
//        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",String.valueOf(userId)));
//        Bill bill = billRepository.findById(billId).orElseThrow(()-> new ResourceNotFoundException("Bill","id",String.valueOf(billId)));
//        if(!bill.getUser().getId().equals(user.getId())){
//            throw new APIException(HttpStatus.BAD_REQUEST,"Bill does not belong to user");
//        }
//        bill.setTotal(billDto.getTotal());
//        Date currentDate = new Date();
//        bill.setDateTime(currentDate);
//        bill.setStatus(billDto.isStatus());
//        Bill newBill = billRepository.save(bill);
//        return convertBillDto(newBill);
//    }

    @Override
    public void deleteBill(Long userId, Long billId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",String.valueOf(userId)));
        Bill bill = billRepository.findById(billId).orElseThrow(()-> new ResourceNotFoundException("Bill","id",String.valueOf(billId)));
        List<DetailBill> detailBill =  detailBillRepository.findAllByBillId(billId);
        for (DetailBill detailbill : detailBill) {
            detailBillRepository.delete(detailbill);
        }
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
    private DetailBill convertDetailBill(DetailDto detailDto){
        return modelMapper.map(detailDto,DetailBill.class);
    }
    private DetailDto convertDetailDto(DetailBill detailBill){
        return modelMapper.map(detailBill,DetailDto.class);
    }
}
