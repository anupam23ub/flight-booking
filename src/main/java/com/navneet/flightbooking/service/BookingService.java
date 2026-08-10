package com.navneet.flightbooking.service;
import com.navneet.flightbooking.dto.*;
import com.navneet.flightbooking.entity.*;
import com.navneet.flightbooking.exception.*;
import com.navneet.flightbooking.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
@Service
public class BookingService{
 private final BookingRepository bookings;private final FlightRepository flights;private final UserRepository users;
 public BookingService(BookingRepository b,FlightRepository f,UserRepository u){bookings=b;flights=f;users=u;}

 @Transactional
 public BookingResponse book(String email,BookingRequest r){
  User user=users.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User not found"));
  Flight f=flights.findByIdForUpdate(r.flightId()).orElseThrow(()->new ResourceNotFoundException("Flight not found"));
  int n=r.passengers().size();
  if(f.getDepartureTime().isBefore(LocalDateTime.now()))throw new BookingException("Cannot book a departed flight");
  if(f.getAvailableSeats()<n)throw new BookingException("Only "+f.getAvailableSeats()+" seats are available");

  Booking b=new Booking();b.setBookingReference(reference());b.setUser(user);b.setFlight(f);b.setSeatsBooked(n);
  b.setTotalAmount(f.getPrice().multiply(BigDecimal.valueOf(n)));b.setStatus(BookingStatus.CONFIRMED);b.setBookedAt(LocalDateTime.now());

  int first=f.getTotalSeats()-f.getAvailableSeats()+1;
  for(int i=0;i<n;i++){
   PassengerRequest pr=r.passengers().get(i);Passenger p=new Passenger();p.setBooking(b);p.setName(pr.name());
   p.setAge(pr.age());p.setGender(pr.gender());p.setSeatNumber(seat(first+i));b.getPassengers().add(p);
  }
  f.setAvailableSeats(f.getAvailableSeats()-n);flights.save(f);
  return response(bookings.save(b));
 }

 @Transactional(readOnly=true)
 public List<BookingResponse> mine(String email){
  User u=users.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User not found"));
  return bookings.findByUserIdOrderByBookedAtDesc(u.getId()).stream().map(this::response).toList();
 }

 @Transactional(readOnly=true)
 public BookingResponse get(String email,Long id,boolean admin){
  Booking b=bookings.findById(id).orElseThrow(()->new ResourceNotFoundException("Booking not found"));
  if(!admin&&!b.getUser().getEmail().equalsIgnoreCase(email))throw new BookingException("You cannot access this booking");
  return response(b);
 }

 @Transactional
 public void cancel(String email,Long id,boolean admin){
  Booking b=bookings.findById(id).orElseThrow(()->new ResourceNotFoundException("Booking not found"));
  if(!admin&&!b.getUser().getEmail().equalsIgnoreCase(email))throw new BookingException("You cannot cancel this booking");
  if(b.getStatus()==BookingStatus.CANCELLED)throw new BookingException("Booking is already cancelled");
  if(b.getFlight().getDepartureTime().isBefore(LocalDateTime.now()))throw new BookingException("Cannot cancel after departure");
  Flight f=flights.findByIdForUpdate(b.getFlight().getId()).orElseThrow(()->new ResourceNotFoundException("Flight not found"));
  f.setAvailableSeats(Math.min(f.getTotalSeats(),f.getAvailableSeats()+b.getSeatsBooked()));flights.save(f);
  b.setStatus(BookingStatus.CANCELLED);bookings.save(b);
 }

 private String reference(){
  String x;do{x="FB-"+System.currentTimeMillis()+"-"+ThreadLocalRandom.current().nextInt(100,1000);}
  while(bookings.existsByBookingReference(x));return x;
 }
 private String seat(int n){return SeatUtil.seatNumber(n);}
 private BookingResponse response(Booking b){
  Flight f=b.getFlight();
  List<PassengerResponse> ps=b.getPassengers().stream().map(p->new PassengerResponse(p.getName(),p.getAge(),p.getGender().name(),p.getSeatNumber())).toList();
  return new BookingResponse(b.getId(),b.getBookingReference(),f.getFlightNumber(),f.getSourceAirport().getCode(),
   f.getDestinationAirport().getCode(),f.getDepartureTime(),b.getSeatsBooked(),b.getTotalAmount(),b.getStatus(),b.getBookedAt(),ps);
 }
}