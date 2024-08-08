package org.example.furnituresaleproject.controller;

import org.example.furnituresaleproject.config.jwt.JwtTokenUtil;
import org.example.furnituresaleproject.request.AuthRequest;
import org.example.furnituresaleproject.service.AccountService.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private IAccountService accountService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        accountService.login(authRequest);

        return new ResponseEntity<>(accountService.login(authRequest), HttpStatus.OK);
    }
}
