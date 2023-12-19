package com.example.thietbidientu.service.impl;

import com.example.thietbidientu.dto.*;
import com.example.thietbidientu.entity.Product;
import com.example.thietbidientu.entity.RefreshToken;
import com.example.thietbidientu.entity.Role;
import com.example.thietbidientu.entity.User;
import com.example.thietbidientu.exception.APIException;
import com.example.thietbidientu.exception.ResourceNotFoundException;
import com.example.thietbidientu.repository.RoleRepository;
import com.example.thietbidientu.repository.UserRepository;
import com.example.thietbidientu.security.JwtProvider;
import com.example.thietbidientu.security.RefreshTokenService;
import com.example.thietbidientu.service.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider tokenProvider;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public JWTAuthResponse signin(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                ));

        if (authentication.isAuthenticated()) {
            String token = tokenProvider.generateToken(loginDto.getEmail());
            String refreshToken = refreshTokenService.createRefreshToken(loginDto.getEmail(),authentication);
            return new JWTAuthResponse(token,refreshToken);
        } else {
            throw new APIException(HttpStatus.BAD_REQUEST, "invalid user request !");
        }
    }

    @Override
    public String signup(SigninDto signinDto) {
        if(userRepository.existsByEmail(signinDto.getEmail())){
            return "email is already taken";
        }
        User user = new User();
        user.setFullname(signinDto.getFullname());
        user.setEmail(signinDto.getEmail());
        user.setAddress(signinDto.getAddress());
        user.setYourphone(signinDto.getYourphone());
        user.setPassword(passwordEncoder.encode(signinDto.getPassword()));
        Role role = roleRepository.findByName("ROLE_USER").get();
        user.setRoles(Collections.singleton(role));
        userRepository.save(user);
        return "User rigistered successfully";
    }

    @Override
    public JWTAuthResponse refreshToken(RefreshTokenReq refreshTokenRequest) {
        return refreshTokenService.findByToken(refreshTokenRequest.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                    String token = tokenProvider.generateToken(userInfo.getEmail());
                    return new JWTAuthResponse(token,refreshTokenRequest.getToken());
                }).orElseThrow(() -> new RuntimeException(
                        "Refresh token is not in database!"));

    }

    @Override
    public UserResponse getUserByEmail(String email) {
        User user =  userRepository.findUserByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User","email",email));
        return convertUserResponse(user);
    }

    @Override
    public int getIdByEmail(String email) {
        return userRepository.findIdByEmail(email);
    }

    private UserResponse convertUserResponse(User user){
        return modelMapper.map(user,UserResponse.class);
    }
}
