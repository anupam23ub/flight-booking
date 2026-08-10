package com.navneet.flightbooking.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;
public record BookingRequest(@NotNull Long flightId,@NotEmpty @Size(max=9) List<@Valid PassengerRequest> passengers){}