package com.navneet.flightbooking.dto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
public record FlightResponse(Long id,String flightNumber,String airline,String source,String destination,
 LocalDateTime departureTime,LocalDateTime arrivalTime,Integer totalSeats,Integer availableSeats,BigDecimal price){}