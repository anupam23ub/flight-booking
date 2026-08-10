package com.navneet.flightbooking.service;
import com.navneet.flightbooking.dto.*;
import com.navneet.flightbooking.entity.*;
import com.navneet.flightbooking.exception.ResourceNotFoundException;
import com.navneet.flightbooking.repository.*;
import org.springframework.stereotype.Service;
import java.time.*;
import java.util.*;
@Service
public class FlightService{
 private final FlightRepository flights;private final AirlineRepository airlines;private final AirportRepository airports;
 public FlightService(FlightRepository f,AirlineRepository a,AirportRepository ap){flights=f;airlines=a;airports=ap;}
 public FlightResponse create(FlightRequest r){
  validate(r); Flight f=new Flight(); fill(f,r); f.setAvailableSeats(r.totalSeats()); return toResponse(flights.save(f));
 }
 public FlightResponse update(Long id,FlightRequest r){
  validate(r); Flight f=flights.findById(id).orElseThrow(()->new ResourceNotFoundException("Flight not found"));
  int booked=f.getTotalSeats()-f.getAvailableSeats();
  if(r.totalSeats()<booked) throw new IllegalArgumentException("Total seats cannot be below already booked seats");
  fill(f,r);f.setAvailableSeats(r.totalSeats()-booked);return toResponse(flights.save(f));
 }
 private void fill(Flight f,FlightRequest r){
  f.setFlightNumber(r.flightNumber());
  f.setAirline(airlines.findById(r.airlineId()).orElseThrow(()->new ResourceNotFoundException("Airline not found")));
  f.setSourceAirport(airports.findById(r.sourceAirportId()).orElseThrow(()->new ResourceNotFoundException("Source airport not found")));
  f.setDestinationAirport(airports.findById(r.destinationAirportId()).orElseThrow(()->new ResourceNotFoundException("Destination airport not found")));
  f.setDepartureTime(r.departureTime());f.setArrivalTime(r.arrivalTime());f.setTotalSeats(r.totalSeats());f.setPrice(r.price());
 }
 private void validate(FlightRequest r){
  if(!r.arrivalTime().isAfter(r.departureTime()))throw new IllegalArgumentException("Arrival time must be after departure time");
  if(r.sourceAirportId().equals(r.destinationAirportId()))throw new IllegalArgumentException("Source and destination cannot be same");
 }
 public void delete(Long id){if(!flights.existsById(id))throw new ResourceNotFoundException("Flight not found");flights.deleteById(id);}
 public FlightResponse get(Long id){return toResponse(flights.findById(id).orElseThrow(()->new ResourceNotFoundException("Flight not found")));}
 public List<FlightResponse> search(String s,String d,LocalDate date){
  LocalDateTime start=date.atStartOfDay();
  return flights.search(s.toUpperCase(),d.toUpperCase(),start,start.plusDays(1)).stream().map(this::toResponse).toList();
 }
 public FlightResponse toResponse(Flight f){return new FlightResponse(f.getId(),f.getFlightNumber(),f.getAirline().getName(),
  f.getSourceAirport().getCode(),f.getDestinationAirport().getCode(),f.getDepartureTime(),f.getArrivalTime(),
  f.getTotalSeats(),f.getAvailableSeats(),f.getPrice());}
}