package com.navneet.flightbooking.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
public record FlightRequest(
 @NotBlank String flightNumber,@NotNull Long airlineId,@NotNull Long sourceAirportId,@NotNull Long destinationAirportId,
 @NotNull LocalDateTime departureTime,@NotNull LocalDateTime arrivalTime,@NotNull @Min(1) Integer totalSeats,
 @NotNull @DecimalMin("0.0") BigDecimal price){}