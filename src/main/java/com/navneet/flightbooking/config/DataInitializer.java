package com.navneet.flightbooking.config;
import com.navneet.flightbooking.entity.*;
import com.navneet.flightbooking.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Configuration
public class DataInitializer{
 @Bean CommandLineRunner seed(UserRepository users,AirlineRepository airlines,AirportRepository airports,FlightRepository flights,PasswordEncoder encoder){
  return args->{
   if(users.findByEmail("admin@flight.com").isEmpty()){
    User u=new User();u.setName("System Admin");u.setEmail("admin@flight.com");u.setPassword(encoder.encode("Admin@123"));u.setRole(Role.ADMIN);users.save(u);
   }
   Airport del=airports.findByCode("DEL").orElseGet(()->airport(airports,"DEL","Indira Gandhi International Airport","Delhi"));
   Airport bom=airports.findByCode("BOM").orElseGet(()->airport(airports,"BOM","Chhatrapati Shivaji Maharaj International Airport","Mumbai"));
   Airport blr=airports.findByCode("BLR").orElseGet(()->airport(airports,"BLR","Kempegowda International Airport","Bengaluru"));
   Airline air=airlines.findAll().stream().findFirst().orElseGet(()->{Airline a=new Airline();a.setName("Demo Airways");return airlines.save(a);});
   if(flights.count()==0){flights.save(flight("DA101",air,del,bom,50,new BigDecimal("5500")));flights.save(flight("DA102",air,bom,del,50,new BigDecimal("5200")));flights.save(flight("DA201",air,del,blr,60,new BigDecimal("6200")));}
  };
 }
 private Airport airport(AirportRepository r,String c,String n,String city){Airport a=new Airport();a.setCode(c);a.setName(n);a.setCity(city);return r.save(a);}
 private Flight flight(String no,Airline a,Airport s,Airport d,int seats,BigDecimal price){
  Flight f=new Flight();f.setFlightNumber(no);f.setAirline(a);f.setSourceAirport(s);f.setDestinationAirport(d);
  LocalDateTime dep=LocalDateTime.now().plusDays(7).withHour(10).withMinute(0).withSecond(0).withNano(0);
  f.setDepartureTime(dep);f.setArrivalTime(dep.plusHours(2).plusMinutes(30));f.setTotalSeats(seats);f.setAvailableSeats(seats);f.setPrice(price);return f;
 }
}