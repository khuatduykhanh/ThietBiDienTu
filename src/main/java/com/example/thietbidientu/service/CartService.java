package com.example.thietbidientu.service;

import com.example.thietbidientu.dto.CartResponse;
import com.example.thietbidientu.dto.DetailCartDto;
import com.example.thietbidientu.dto.DetailDto;
import com.example.thietbidientu.entity.DetailCart;

public interface CartService {
    CartResponse createCartDetail(Long userId, DetailCartDto detailDto);
    CartResponse getCart(Long userid);
    CartResponse updateCartDetail(Long userId,DetailCartDto detailDto);
    String deleteCartDetail(Long userId,Long productId);
    String deleteAllCart(Long userId);
}
