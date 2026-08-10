package com.navneet.flightbooking.security;
import com.navneet.flightbooking.entity.User;
import com.navneet.flightbooking.repository.UserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
 private final JwtService jwt; private final UserRepository users;
 public JwtAuthenticationFilter(JwtService jwt,UserRepository users){this.jwt=jwt;this.users=users;}
 protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain)throws ServletException,IOException{
  String h=req.getHeader("Authorization");
  if(h!=null&&h.startsWith("Bearer ")){
   String token=h.substring(7);
   if(jwt.isValid(token)){String email=jwt.extractEmail(token);users.findByEmail(email).ifPresent(this::authenticate);}
  }
  chain.doFilter(req,res);
 }
 private void authenticate(User u){
  var a=List.of(new SimpleGrantedAuthority("ROLE_"+u.getRole().name()));
  SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(u.getEmail(),null,a));
 }
}