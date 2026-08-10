package com.navneet.flightbooking.repository;
import com.navneet.flightbooking.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface AirportRepository extends JpaRepository<Airport,Long>{Optional<Airport> findByCode(String code);}