package com.example.thietbidientu.controller;

import com.example.thietbidientu.dto.JWTAuthResponse;
import com.example.thietbidientu.dto.LoginDto;
import com.example.thietbidientu.dto.RefreshTokenReq;
import com.example.thietbidientu.dto.SigninDto;
import com.example.thietbidientu.entity.Bill;
import com.example.thietbidientu.entity.Role;
import com.example.thietbidientu.entity.User;
import com.example.thietbidientu.entity.RefreshToken;
import com.example.thietbidientu.exception.APIException;
import com.example.thietbidientu.repository.RoleRepository;
import com.example.thietbidientu.repository.UserRepository;
import com.example.thietbidientu.security.JwtProvider;
import com.example.thietbidientu.security.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

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

    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                ));

        if (authentication.isAuthenticated()) {
            String token = tokenProvider.generateToken(loginDto.getEmail());
            String refreshToken = refreshTokenService.createRefreshToken(loginDto.getEmail(),authentication);
            return  ResponseEntity.ok(new JWTAuthResponse(token,refreshToken));
        } else {
            throw new APIException(HttpStatus.BAD_REQUEST, "invalid user request !");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SigninDto signinDto){
        if(userRepository.existsByEmail(signinDto.getEmail())){
            return new ResponseEntity<>("email is already taken", HttpStatus.BAD_REQUEST);
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

        return new ResponseEntity<>("User rigistered successfully", HttpStatus.CREATED);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<JWTAuthResponse> refreshToken(@RequestBody RefreshTokenReq refreshTokenRequest) {
        return refreshTokenService.findByToken(refreshTokenRequest.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                    String token = tokenProvider.generateToken(userInfo.getEmail());
                    return ResponseEntity.ok(new JWTAuthResponse(token,refreshTokenRequest.getToken()));
                }).orElseThrow(() -> new RuntimeException(
                        "Refresh token is not in database!"));

    }

    @GetMapping("userId")
    public ResponseEntity<Integer> getUserIdByEmail(@RequestParam(name = "email") String email){
        return ResponseEntity.ok(userRepository.findIdByEmail(email));
    }

}