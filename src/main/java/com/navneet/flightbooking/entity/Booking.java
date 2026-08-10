package com.navneet.flightbooking.entity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
@Entity @Table(name="bookings") @Getter @Setter @NoArgsConstructor
public class Booking {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(nullable=false,unique=true) private String bookingReference;
  @ManyToOne(fetch=FetchType.LAZY,optional=false) private User user;
  @ManyToOne(fetch=FetchType.LAZY,optional=false) private Flight flight;
  @Column(nullable=false) private Integer seatsBooked;
  @Column(nullable=false,precision=12,scale=2) private BigDecimal totalAmount;
  @Enumerated(EnumType.STRING) @Column(nullable=false) private BookingStatus status;
  @Column(nullable=false) private LocalDateTime bookedAt;
  @OneToMany(mappedBy="booking",cascade=CascadeType.ALL,orphanRemoval=true)
  private List<Passenger> passengers=new ArrayList<>();
}