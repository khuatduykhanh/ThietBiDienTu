package com.example.thietbidientu.service.impl;

import com.example.thietbidientu.entity.ImageData;
import com.example.thietbidientu.entity.Product;
import com.example.thietbidientu.exception.ResourceNotFoundException;
import com.example.thietbidientu.repository.ImageRepository;
import com.example.thietbidientu.repository.ProductRepository;
import com.example.thietbidientu.service.ImageService;
import com.example.thietbidientu.service.ProductService;
import com.example.thietbidientu.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ProductRepository productRepository;
    public String uploadImage(Long productId, MultipartFile file) throws IOException {
        Product product =  productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product","id",String.valueOf(productId)));
        ImageData imageData = imageRepository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).product(product).build());
        if (imageData != null) {
            return "file uploaded successfully : " + file.getOriginalFilename();
        }
        return null;
    }

    public byte[] downloadImage(Long productId){
        ImageData image = imageRepository.findAllByProductId(productId).orElseThrow(()-> new ResourceNotFoundException("Product","id",String.valueOf(productId)));
        byte[] images=ImageUtils.decompressImage(image.getImageData());
        return images;
    }
}
