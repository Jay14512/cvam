# Dev Log — Citizen Vaccine Appointment Manager

## Session 1 — July 8, 2026

### What we built
- Designed and implemented the core domain model from scratch
- `User.java` — abstract base class with shared fields: `firstName`, `lastName`, `fiscalCode`, `email`
- `Citizen.java` — extends User, adds `phoneNumber` (String) and `birthDate` (LocalDate)
- `Doctor.java` — extends User, adds `doctorId` (String)
- `Staff.java` — extends User, adds `staffCode` and `doctorId` (staff is tied to one specific doctor)
- `Appointment.java` — independent class (does NOT extend User), holds a `Citizen`, a `Doctor`, `LocalDateTime`, `appointmentId`, and `vaccineType`
- `Main.java` — test runner that instantiates all four concrete types and prints labeled output

### Key concepts learned
- `abstract` class = cannot be instantiated directly; forces use of subclasses
- Inheritance: `extends` means "is a" — only use it when that relationship is true
- `super(...)` must be the first line in a subclass constructor
- Use `String` for identifiers/codes (never `int`) — leading zeros, `+` prefix, no arithmetic
- Use `LocalDate` for dates, `LocalDateTime` for date + time (both from `java.time`)
- Method chaining: `appointment.getCitizen().getFirstName()`
- Warnings (yellow) ≠ errors (red) — warnings compile fine, errors don't
- Model folder = classes that represent data entities

### All files location
```
src/main/java/org/example/
    Main.java
    model/
        User.java         (abstract)
        Citizen.java
        Doctor.java
        Staff.java
        Appointment.java
```

### Where we left off
All model classes are complete and working. `Main` runs cleanly with labeled output for Staff, Citizen, Doctor, and Appointment.

### Next session — pick up here
- Add **validation** to constructors (e.g. throw exception if name is empty, email missing `@`)
- Start the **service layer** (`src/main/java/org/example/service/`)
- First service: `AppointmentService` — logic to book an appointment and check for scheduling conflicts
- Think about: what happens if a doctor is already booked at the requested time?

