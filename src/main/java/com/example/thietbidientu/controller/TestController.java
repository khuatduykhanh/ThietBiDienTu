package com.example.thietbidientu.controller;

import com.example.thietbidientu.dto.SigninDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/private")
    public ResponseEntity<String> privatePage() {
        return  ResponseEntity.ok("hello");
    }
}
