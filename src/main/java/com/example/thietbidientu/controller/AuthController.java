package com.example.thietbidientu.controller;

import com.example.thietbidientu.dto.*;
import com.example.thietbidientu.entity.Bill;
import com.example.thietbidientu.entity.Role;
import com.example.thietbidientu.entity.User;
import com.example.thietbidientu.entity.RefreshToken;
import com.example.thietbidientu.exception.APIException;
import com.example.thietbidientu.repository.RoleRepository;
import com.example.thietbidientu.repository.UserRepository;
import com.example.thietbidientu.security.JwtProvider;
import com.example.thietbidientu.security.RefreshTokenService;
import com.example.thietbidientu.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class AuthController {

   @Autowired
   private AuthService authService;

    @PostMapping("/auth/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){
        return new ResponseEntity<>(authService.signin(loginDto),HttpStatus.OK);
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<?> registerUser(@RequestBody SigninDto signinDto){
        return new ResponseEntity<>(Collections.singletonMap("message",authService.signup(signinDto)),HttpStatus.OK);
    }

    @PostMapping("/auth/refreshToken")
    public ResponseEntity<JWTAuthResponse> refreshToken(@RequestBody RefreshTokenReq refreshTokenRequest) {
        return  ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("userId")
    public ResponseEntity<UserResponse> getUserIdByEmail(@RequestParam(name = "email") String email){
        return ResponseEntity.ok(authService.getUserByEmail(email));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("getUserId")
    public int getIdByEmail(@RequestParam(name = "email") String email){
        return authService.getIdByEmail(email);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("updateUser")
    public String updateUser(@RequestParam(name = "email") String email,@RequestBody UserUpdate userUpdate){
        return authService.updateUser(email,userUpdate);
    }

}