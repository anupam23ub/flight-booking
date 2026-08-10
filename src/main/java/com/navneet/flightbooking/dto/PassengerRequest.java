package com.navneet.flightbooking.dto;
import com.navneet.flightbooking.entity.Gender;
import jakarta.validation.constraints.*;
public record PassengerRequest(@NotBlank String name,@Min(1) @Max(120) Integer age,@NotNull Gender gender){}