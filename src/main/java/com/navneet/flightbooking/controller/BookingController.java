package com.navneet.flightbooking.controller;
import com.navneet.flightbooking.dto.*;
import com.navneet.flightbooking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/bookings")
public class BookingController{
 private final BookingService service;public BookingController(BookingService s){service=s;}
 @PostMapping ResponseEntity<BookingResponse> book(Authentication a,@Valid @RequestBody BookingRequest r){return ResponseEntity.status(201).body(service.book(a.getName(),r));}
 @GetMapping("/my") List<BookingResponse> mine(Authentication a){return service.mine(a.getName());}
 @GetMapping("/{id}") BookingResponse get(Authentication a,@PathVariable Long id){boolean admin=isAdmin(a);return service.get(a.getName(),id,admin);}
 @DeleteMapping("/{id}") ResponseEntity<?> cancel(Authentication a,@PathVariable Long id){service.cancel(a.getName(),id,isAdmin(a));return ResponseEntity.noContent().build();}
 private boolean isAdmin(Authentication a){return a.getAuthorities().stream().anyMatch(x->x.getAuthority().equals("ROLE_ADMIN"));}
}