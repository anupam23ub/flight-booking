package com.navneet.flightbooking.entity;
import jakarta.persistence.*;
import lombok.*;
@Entity @Table(name="airports") @Getter @Setter @NoArgsConstructor
public class Airport {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(nullable=false,unique=true,length=3) private String code;
  @Column(nullable=false) private String name;
  @Column(nullable=false) private String city;
}