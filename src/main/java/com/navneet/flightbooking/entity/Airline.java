package com.navneet.flightbooking.entity;
import jakarta.persistence.*;
import lombok.*;
@Entity @Table(name="airlines") @Getter @Setter @NoArgsConstructor
public class Airline {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(nullable=false,unique=true) private String name;
}