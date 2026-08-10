# Flight Booking Service

Spring Boot 3 + Java 17 + MySQL + JPA/Hibernate + Spring Security + JWT.

Features:
- Registration/login with JWT
- USER/ADMIN roles
- Airport/airline management
- Flight CRUD and search
- Passenger booking and automatic seat assignment
- Cancellation with seat restoration
- Pessimistic locking to prevent concurrent overbooking
- Validation and global exception handling

## Prerequisites
- Java 17+ (JDK, not just a JRE)
- Maven 3.9+ (`mvn -version` to check)
- A running MySQL server (8.x recommended)

## Run
1. Start MySQL and create the database:
   ```sql
   CREATE DATABASE flight_booking;
   ```
2. Update the MySQL credentials in `src/main/resources/application.properties` if your local
   MySQL username/password differ from the defaults (`root` / `root`).
3. Build: `mvn clean package`
4. Run: `mvn spring-boot:run`
   (or `java -jar target/flight-booking-service-1.0.0.jar` after step 3)

The app starts on `http://localhost:8080`. On first run, `DataInitializer` seeds a demo admin
user plus a couple of demo airports/airline/flights so you have something to query immediately.

Demo admin: admin@flight.com / Admin@123
Change credentials and JWT secret before production use.

Example search:
GET /api/flights/search?source=DEL&destination=BOM&date=2026-08-17

Example booking:
POST /api/bookings
Authorization: Bearer <JWT>
{
  "flightId": 1,
  "passengers": [
    {"name":"Rahul Kumar","age":22,"gender":"MALE"},
    {"name":"Aman Kumar","age":24,"gender":"MALE"}
  ]
}
