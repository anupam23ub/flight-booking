package com.navneet.flightbooking.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
@Entity @Table(name="users") @Getter @Setter @NoArgsConstructor
public class User {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(nullable=false) private String name;
  @Column(nullable=false,unique=true) private String email;
  @JsonIgnore @Column(nullable=false) private String password;
  @Enumerated(EnumType.STRING) @Column(nullable=false) private Role role=Role.USER;
  private LocalDateTime createdAt;
  @PrePersist void onCreate(){createdAt=LocalDateTime.now();}
}