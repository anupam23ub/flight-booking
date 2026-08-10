package com.navneet.flightbooking.entity;
import jakarta.persistence.*;
import lombok.*;
@Entity @Table(name="passengers") @Getter @Setter @NoArgsConstructor
public class Passenger {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @ManyToOne(fetch=FetchType.LAZY,optional=false) private Booking booking;
  @Column(nullable=false) private String name;
  @Column(nullable=false) private Integer age;
  @Enumerated(EnumType.STRING) @Column(nullable=false) private Gender gender;
  @Column(nullable=false) private String seatNumber;
}