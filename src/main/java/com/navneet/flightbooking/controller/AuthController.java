package com.navneet.flightbooking.controller;
import com.navneet.flightbooking.dto.*;
import com.navneet.flightbooking.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/auth")
public class AuthController{
 private final AuthService service;public AuthController(AuthService s){service=s;}
 @PostMapping("/register") ResponseEntity<?> register(@Valid @RequestBody RegisterRequest r){service.register(r);return ResponseEntity.status(201).body("Registration successful");}
 @PostMapping("/login") AuthResponse login(@Valid @RequestBody LoginRequest r){return service.login(r);}
}