package com.navneet.flightbooking.service;
import com.navneet.flightbooking.dto.*;
import com.navneet.flightbooking.entity.*;
import com.navneet.flightbooking.exception.BookingException;
import com.navneet.flightbooking.repository.UserRepository;
import com.navneet.flightbooking.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class AuthService{
 private final UserRepository users; private final PasswordEncoder encoder; private final JwtService jwt;
 public AuthService(UserRepository u,PasswordEncoder e,JwtService j){users=u;encoder=e;jwt=j;}
 public void register(RegisterRequest r){
  String email=r.email().toLowerCase();
  if(users.existsByEmail(email)) throw new BookingException("Email already registered");
  User u=new User();u.setName(r.name());u.setEmail(email);u.setPassword(encoder.encode(r.password()));u.setRole(Role.USER);users.save(u);
 }
 public AuthResponse login(LoginRequest r){
  User u=users.findByEmail(r.email().toLowerCase()).orElseThrow(()->new BookingException("Invalid email or password"));
  if(!encoder.matches(r.password(),u.getPassword())) throw new BookingException("Invalid email or password");
  return new AuthResponse(jwt.generate(u.getEmail(),u.getRole().name()),u.getRole().name());
 }
}