# Citizen Vaccine Appointment Manager (CVAM)

Java learning project to model and manage vaccine appointment booking with object-oriented design and a small service layer.

> This is my first Java project as I transition from PHP to Java. It is intentionally public while under development to demonstrate progress, coding style, and learning over time.

## Project Status

- Current stage: Work in progress (MVP foundation complete)
- Focus: Core domain modeling, validation, and booking business rules
- Last updated milestone: Session 4 (July 2026)

## What This Project Demonstrates

- OOP fundamentals in Java (`abstract` base class, inheritance, encapsulation)
- Basic domain-driven structure (`model` + `service` packages)
- Input validation using `IllegalArgumentException`
- In-memory booking logic with scheduling conflict checks
- Filtering appointments by citizen identifier (`fiscalCode`)

## Current Features

- Domain models:
  - `User` (abstract base)
  - `Citizen`, `Doctor`, `Staff`
  - `Appointment`
- Validation rules in constructors (required fields, basic email and phone checks)
- `AppointmentService`:
  - `bookAppointment(Appointment appointment)`
  - `getAppointments()`
  - `getAppointmentsForCitizen(String fiscalCode)`
- `Main` runner that creates sample data and demonstrates service usage

## In Progress / Next Steps

- Add `getAppointmentsForDoctor(String doctorId)`
- Add appointment cancellation flow
- Improve display formatting (`toString()` for domain objects)
- Add `try/catch` handling in `Main` for cleaner error handling
- Add unit tests for model validation and service logic

## Tech Stack

- Java 21
- Maven
- Standard library only (`java.time`, collections)

## Project Structure

```text
src/main/java/org/example/
  Main.java
  model/
    User.java
    Citizen.java
    Doctor.java
    Staff.java
    Appointment.java
  service/
    AppointmentService.java
```

## How To Run

### Prerequisites

- JDK 21
- Maven 3.9+

### Run from project root

```bash
mvn clean compile
java -cp target/classes org.example.Main
```

You can also run `Main` directly from your IDE.

## Why This Repo Is Public Early

I am using this repository as a transparent learning portfolio while transitioning from PHP to Java. The goal is to show:

- how I structure code,
- how I evolve features over time,
- and how I improve quality as the project grows.

## Notes

- This project currently uses in-memory data (no database yet).
- This is a practice/portfolio project, not production software.

## License

Licensed under the MIT License. See `LICENSE`.


