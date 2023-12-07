package com.example.thietbidientu.service;

import com.example.thietbidientu.dto.*;
import com.example.thietbidientu.entity.User;

public interface AuthService {
    JWTAuthResponse signin(LoginDto loginDto);
    String signup(SigninDto signinDto);
    JWTAuthResponse refreshToken(RefreshTokenReq refreshTokenRequest);
    UserResponse getUserByEmail(String email);

}