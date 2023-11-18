package com.example.thietbidientu.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    String uploadImage(Long productId,MultipartFile file) throws IOException;

     byte[] downloadImage(Long productId);
}
