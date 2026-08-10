package com.navneet.flightbooking.repository;
import com.navneet.flightbooking.entity.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AirlineRepository extends JpaRepository<Airline,Long>{}