package com.navneet.flightbooking.repository;
import com.navneet.flightbooking.entity.Flight;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.*;
public interface FlightRepository extends JpaRepository<Flight,Long>{
  @Query("select f from Flight f join fetch f.airline join fetch f.sourceAirport join fetch f.destinationAirport " +
         "where f.sourceAirport.code=:source and f.destinationAirport.code=:destination " +
         "and f.departureTime>=:start and f.departureTime<:end and f.availableSeats>0 order by f.departureTime")
  List<Flight> search(@Param("source") String source,@Param("destination") String destination,
                      @Param("start") LocalDateTime start,@Param("end") LocalDateTime end);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select f from Flight f join fetch f.airline join fetch f.sourceAirport join fetch f.destinationAirport where f.id=:id")
  Optional<Flight> findByIdForUpdate(@Param("id") Long id);
}