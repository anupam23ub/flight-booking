package com.navneet.flightbooking.controller;
import com.navneet.flightbooking.entity.*;
import com.navneet.flightbooking.repository.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/admin")
public class AdminController{
 private final UserRepository users;private final BookingRepository bookings;private final AirlineRepository airlines;private final AirportRepository airports;
 public AdminController(UserRepository u,BookingRepository b,AirlineRepository a,AirportRepository ap){users=u;bookings=b;airlines=a;airports=ap;}
 @GetMapping("/users") List<User> users(){return users.findAll();}
 @GetMapping("/bookings") List<Booking> bookings(){return bookings.findAllByOrderByBookedAtDesc();}
 @PostMapping("/airlines") Airline addAirline(@RequestBody Airline a){return airlines.save(a);}
 @PostMapping("/airports") Airport addAirport(@RequestBody Airport a){a.setCode(a.getCode().toUpperCase());return airports.save(a);}
}