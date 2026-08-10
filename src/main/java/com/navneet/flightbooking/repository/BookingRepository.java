package com.navneet.flightbooking.repository;
import com.navneet.flightbooking.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface BookingRepository extends JpaRepository<Booking,Long>{
  List<Booking> findByUserIdOrderByBookedAtDesc(Long userId);
  List<Booking> findAllByOrderByBookedAtDesc();
  boolean existsByBookingReference(String reference);
}