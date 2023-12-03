package com.example.thietbidientu.service.impl;

import com.example.thietbidientu.dto.CartResponse;
import com.example.thietbidientu.dto.DetailCartDto;
import com.example.thietbidientu.dto.DetailDto;
import com.example.thietbidientu.entity.Cart;
import com.example.thietbidientu.entity.DetailCart;
import com.example.thietbidientu.entity.Product;
import com.example.thietbidientu.entity.User;
import com.example.thietbidientu.exception.ResourceNotFoundException;
import com.example.thietbidientu.repository.CartRepository;
import com.example.thietbidientu.repository.DetailCartRepository;
import com.example.thietbidientu.repository.ProductRepository;
import com.example.thietbidientu.repository.UserRepository;
import com.example.thietbidientu.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private DetailCartRepository detailCartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CartResponse createCartDetail(Long userId, DetailCartDto detailDto) {
        Cart check = cartRepository.findByUserId(userId);
        if(check != null){
            DetailCart detailCart = convertDetailCart(detailDto);
            List<DetailCart> listDetail = detailCartRepository.findAllByCartId(check.getId());
            Product product =  productRepository.findById(detailDto.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product","id",String.valueOf(detailDto.getProductId())));;
            detailCart.setProduct3(product);
            detailCart.setCart(check);
            DetailCart newDetail =  detailCartRepository.save(detailCart);
            check.setTotal(check.getTotal() + (detailDto.getPrice() * detailDto.getQuantity()));
            Cart newcart = cartRepository.save(check);
            listDetail.add(newDetail);
            List<DetailCartDto> newList = new ArrayList<>();
            for (DetailCart detail : listDetail) {
                newList.add(convertDetailDto(detail));
            }
            CartResponse newResponse = new CartResponse();
            newResponse.setTotal(newcart.getTotal());
            newResponse.setUserId(userId);
            newResponse.setDetailCart(newList);
            return newResponse;
        } else {
            DetailCart detailCart1 = convertDetailCart(detailDto);
            User user =  userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","id",String.valueOf(userId)));
            Cart cart = new Cart();
            cart.setTotal(detailDto.getPrice() * detailDto.getQuantity());
            cart.setUser1(user);
            cart.setCarts(new HashSet<>());
            Cart newCart =  cartRepository.save(cart);
            Long productId = detailDto.getProductId();
            Product product =  productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product","id",String.valueOf(productId)));;
            detailCart1.setProduct3(product);
            detailCart1.setCart(newCart);
            List<DetailCartDto> listDetail = new ArrayList<>();
            DetailCart newDetail =  detailCartRepository.save(detailCart1);
            DetailCartDto detailDto1 = convertDetailDto(newDetail);
            listDetail.add(detailDto1);
            CartResponse newResponse = new CartResponse();
            newResponse.setUserId(userId);
            newResponse.setTotal(newDetail.getPrice() * newDetail.getQuantity());
            newResponse.setDetailCart(listDetail);
            return newResponse;
        }
    }

    @Override
    public CartResponse getCart(Long userId) {
        Cart cart = cartRepository.findByUserId1(userId).orElseThrow(() -> new ResourceNotFoundException("Cart","id",String.valueOf(userId)));
        List<DetailCart> listDetail = detailCartRepository.findAllByCartId(cart.getId());
        List<DetailCartDto> detailCartDto = new ArrayList<DetailCartDto>();
        for (DetailCart detail : listDetail) {
            detailCartDto.add(convertDetailDto(detail));
        }
        CartResponse cartRes = new CartResponse();
        cartRes.setUserId(userId);
        cartRes.setTotal(cart.getTotal());
        cartRes.setDetailCart(detailCartDto);
        return cartRes;
    }

    @Override
    public CartResponse updateCartDetail(Long userId, DetailCartDto detailDto) {
        Cart cart = cartRepository.findByUserId1(userId).orElseThrow(() -> new ResourceNotFoundException("Cart","id",String.valueOf(userId)));
        List<DetailCart> listDetail = detailCartRepository.findAllByCartId(cart.getId());
        for (DetailCart detail : listDetail) {
            if(Objects.equals(detail.getProduct3().getId(), detailDto.getProductId())){
                if(detail.getQuantity() > detailDto.getQuantity()) {
                    cart.setTotal(cart.getTotal() - (detail.getQuantity()-detailDto.getQuantity()) * detail.getPrice());
                }else{
                    cart.setTotal(cart.getTotal() + (detailDto.getQuantity() - detail.getQuantity()) * detail.getPrice());
                }
                detail.setQuantity(detailDto.getQuantity());
                detailCartRepository.save(detail);
                cartRepository.save(cart);
            }
        }
        Cart cart1 = cartRepository.findByUserId1(userId).orElseThrow(() -> new ResourceNotFoundException("Cart","id",String.valueOf(userId)));
        List<DetailCart> listDetail1 = detailCartRepository.findAllByCartId(cart.getId());
        List<DetailCartDto> detailCartDto = new ArrayList<DetailCartDto>();
        for (DetailCart detail : listDetail1) {
            detailCartDto.add(convertDetailDto(detail));
        }
        CartResponse cartRes = new CartResponse();
        cartRes.setUserId(userId);
        cartRes.setTotal(cart1.getTotal());
        cartRes.setDetailCart(detailCartDto);
        return cartRes;
    }

    @Override
    public String deleteCartDetail(Long userId, Long productId) {
        Cart cart = cartRepository.findByUserId1(userId).orElseThrow(() -> new ResourceNotFoundException("Cart","id",String.valueOf(userId)));
        List<DetailCart> listDetail = detailCartRepository.findAllByCartId(cart.getId());
        for (DetailCart detail : listDetail) {
            System.out.println("Id"+detail.getProduct3().getId());
            if(Objects.equals(detail.getProduct3().getId(), productId)){
                cart.setTotal(cart.getTotal() - detail.getPrice() * detail.getQuantity());
                cartRepository.save(cart);
                detailCartRepository.delete(detail);
                return "Delete Successfully";
            }
        }
        return "Id product not exit in cart";
    }

    private DetailCart convertDetailCart(DetailCartDto detailDto){
        return modelMapper.map(detailDto,DetailCart.class);
    }
    private DetailCartDto convertDetailDto(DetailCart detailCart){
        return modelMapper.map(detailCart,DetailCartDto.class);
    }
}
