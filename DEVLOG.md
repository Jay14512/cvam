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

 ---

## Session 2 — July 9, 2026

### What we built
- Added constructor validation to all model classes
- `User.java` — validates `firstName` and `lastName` not null/empty; `email` must contain `@`
- `Citizen.java` — validates `phoneNumber` not null and must start with `+`; `birthDate` required (not null)
- `Doctor.java` — validates `doctorId` not null/empty
- `Staff.java` — validates `staffCode` and `doctorId` not null/empty
- Tested bad input in `Main` — confirmed `IllegalArgumentException` fires correctly with stack trace

### Key concepts learned
- `throw new IllegalArgumentException("message")` — how to reject invalid input in Java
- Stack trace: read bottom to top to find where the error originated
- Validate **before** assigning to `this.field` — never store a bad value even briefly
- `null` vs `""` — two different invalid cases, both must be checked
- `LocalDate` can only be checked with `!= null`, not `.isEmpty()` (it's not a String)
- `startsWith("+")` — String method to check the beginning of a value
- Warnings (yellow) still compile; errors (red) do not — warnings about unused fields go away once you use getters in Main
- Debugging loop: read the error message → identify the cause → fix it yourself

### Where we left off
All model classes have validation. `Main` runs cleanly with labeled output for all four types.

### Next session — pick up here
- Start the **service layer** in `src/main/java/org/example/service/`
- Create `AppointmentService.java` — first real business logic class
- Learn about `List` — storing multiple appointments in memory
- First method: book an appointment and check for scheduling conflicts (same doctor, same time slot)

---

## Session 3 — July 9, 2026 (part 2)

### What we built
- Learned about `List<T>` and `ArrayList` — Java's equivalent of PHP arrays
- Created three `Appointment` objects with unique IDs and time slots in `Main`
- Added all three to a `List<Appointment>` and looped over them with a `for-each` loop
- Used method chaining inside the loop: `appt.getCitizen().getFirstName()`
- Created `src/main/java/org/example/service/` folder
- Created empty `AppointmentService.java` skeleton ready for next session

### Key concepts learned
- `List<Appointment> appointments = new ArrayList<>()` — typed list, only holds one type
- `.add(item)` — adds an object to the list
- `for (Appointment appt : appointments)` — for-each loop, same idea as PHP `foreach`
- You cannot declare two variables with the same name in the same `{}` block
- `/* ... */` = block comment in Java (Ctrl+Shift+/ in IntelliJ)
- `Ctrl+D` = duplicate line in IntelliJ
- Service layer folder = business logic (not data, not storage — just rules)

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
    service/
        AppointmentService.java   (empty skeleton, ready for next session)
```

### Next session — pick up here
- Implement `AppointmentService`:
  - Add a `List<Appointment>` field inside the class
  - Write `bookAppointment(Appointment appointment)` method
  - Check if the doctor is already booked at that time — if yes, throw exception
  - If free, add to the list
- Test it from `Main` with a deliberate conflict to prove the check works

