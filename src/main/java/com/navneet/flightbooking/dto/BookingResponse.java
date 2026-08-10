package com.navneet.flightbooking.dto;
import com.navneet.flightbooking.entity.BookingStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
public record BookingResponse(Long id,String bookingReference,String flightNumber,String source,String destination,
 LocalDateTime departureTime,Integer seatsBooked,BigDecimal totalAmount,BookingStatus status,
 LocalDateTime bookedAt,List<PassengerResponse> passengers){}