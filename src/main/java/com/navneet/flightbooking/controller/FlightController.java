package com.navneet.flightbooking.controller;
import com.navneet.flightbooking.dto.*;
import com.navneet.flightbooking.service.FlightService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
@RestController @RequestMapping("/api/flights")
public class FlightController{
 private final FlightService service;public FlightController(FlightService s){service=s;}
 @GetMapping("/search") List<FlightResponse> search(@RequestParam String source,@RequestParam String destination,@RequestParam LocalDate date){return service.search(source,destination,date);}
 @GetMapping("/{id}") FlightResponse get(@PathVariable Long id){return service.get(id);}
 @PostMapping ResponseEntity<FlightResponse> create(@Valid @RequestBody FlightRequest r){return ResponseEntity.status(201).body(service.create(r));}
 @PutMapping("/{id}") FlightResponse update(@PathVariable Long id,@Valid @RequestBody FlightRequest r){return service.update(id,r);}
 @DeleteMapping("/{id}") ResponseEntity<?> delete(@PathVariable Long id){service.delete(id);return ResponseEntity.noContent().build();}
}