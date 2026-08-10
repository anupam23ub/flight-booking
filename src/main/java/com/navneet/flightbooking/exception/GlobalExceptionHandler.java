package com.navneet.flightbooking.exception;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;
@RestControllerAdvice
public class GlobalExceptionHandler{
 @ExceptionHandler(ResourceNotFoundException.class) ResponseEntity<?> nf(ResourceNotFoundException e){return ResponseEntity.status(404).body(Map.of("error",e.getMessage()));}
 @ExceptionHandler(BookingException.class) ResponseEntity<?> be(BookingException e){return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));}
 @ExceptionHandler(IllegalArgumentException.class) ResponseEntity<?> ia(IllegalArgumentException e){return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));}
 @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<?> ve(MethodArgumentNotValidException e){
  String m=e.getBindingResult().getFieldErrors().stream().map(x->x.getField()+": "+x.getDefaultMessage()).collect(Collectors.joining(", "));
  return ResponseEntity.badRequest().body(Map.of("error",m));
 }
 @ExceptionHandler(Exception.class) ResponseEntity<?> all(Exception e){return ResponseEntity.status(500).body(Map.of("error","Unexpected server error"));}
}