package com.navneet.flightbooking.entity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity @Table(name="flights") @Getter @Setter @NoArgsConstructor
public class Flight {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(nullable=false,unique=true) private String flightNumber;
  @ManyToOne(fetch=FetchType.LAZY,optional=false) private Airline airline;
  @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="source_airport_id") private Airport sourceAirport;
  @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="destination_airport_id") private Airport destinationAirport;
  @Column(nullable=false) private LocalDateTime departureTime;
  @Column(nullable=false) private LocalDateTime arrivalTime;
  @Column(nullable=false) private Integer totalSeats;
  @Column(nullable=false) private Integer availableSeats;
  @Column(nullable=false,precision=12,scale=2) private BigDecimal price;
  @Version private Long version;
}