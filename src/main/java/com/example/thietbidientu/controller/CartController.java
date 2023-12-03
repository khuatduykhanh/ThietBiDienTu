package com.example.thietbidientu.controller;

import com.example.thietbidientu.dto.CartResponse;
import com.example.thietbidientu.dto.DetailCartDto;
import com.example.thietbidientu.dto.DetailDto;
import com.example.thietbidientu.entity.Product;
import com.example.thietbidientu.repository.ProductRepository;
import com.example.thietbidientu.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @PostMapping("/{userId}")
    public CartResponse createCart(@PathVariable("userId") Long userId,@Valid @RequestBody DetailCartDto detailDto){
        return cartService.createCartDetail(userId,detailDto);
    }
    @GetMapping("/{userId}")
    public CartResponse getCartDetail(@PathVariable("userId") Long userId){
        return cartService.getCart(userId);
    }

    @PutMapping("/{userId}")
    public CartResponse updateCartDetail(@PathVariable("userId") Long userId,@Valid @RequestBody DetailCartDto detailDto){
        return cartService.updateCartDetail(userId,detailDto);
    }

    @DeleteMapping("/{userId}/{productId}")
    public String deleteCartDetail(@PathVariable("userId") Long userId,@PathVariable("productId") Long productId){
        return cartService.deleteCartDetail(userId,productId);
    }
}
