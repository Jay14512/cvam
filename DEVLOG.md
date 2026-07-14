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

---

## Session 4 — July 10, 2026

### What we built
- Implemented `AppointmentService` with full booking logic
- `bookAppointment(Appointment appointment)` — checks for doctor + time slot conflict before adding
- `getAppointments()` — returns the full internal list
- `getAppointmentsForCitizen(String fiscalCode)` — filters appointments by citizen's unique fiscal code
- Refactored `Main` to use the service instead of a manual `ArrayList`
- Tested conflict detection — confirmed `IllegalArgumentException` fires on duplicate doctor + time
- Tested filter method — correctly returns only Mario's appointment when queried by fiscal code

### Key concepts learned
- Class fields vs constructor vs methods — fields live at the top, outside methods, for the object's whole life
- A getter lives where the data lives — `getAppointments()` belongs in `AppointmentService` because that's where the list is
- `==` compares object references in Java; `.equals()` compares values — always use `.equals()` for objects
- Java has no `===` because it's strictly typed — type mismatches are caught at compile time
- Filtering a list: create empty `result` list → loop → check condition → add matches → return result
- Unique identifiers (like `fiscalCode`) should be used to look up people, not names or birthdates
- Data travels with the object — each `Appointment` carries its own `Citizen` and `Doctor` inside it

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
        AppointmentService.java
```

### Next session — pick up here
- Add `getAppointmentsForDoctor(String doctorId)` — same filter pattern, different field
- Think about `cancelAppointment` — how do you remove from a list in Java?
- Consider adding a `toString()` method to `Appointment` for cleaner printing
- Eventually: `try/catch` in `Main` to handle exceptions gracefully instead of crashing


---

## Session 5 — July 12, 2026

### What we built
- Published the repository publicly to showcase the Java transition and current project level
- Added a recruiter-friendly `README.md` that explains project purpose, current features, and roadmap
- Added `LICENSE` (MIT) at project root and linked license info in the README
- Implemented `getAppointmentsForDoctor(String doctorId)` in `AppointmentService`
- Verified doctor filtering behavior from `Main` for both matching and non-matching doctor IDs

### Key concepts learned
- Public portfolio repos can be intentionally marked as WIP and still be valuable for recruiters
- `doctorId` is the correct unique identifier for doctor-based lookup (same pattern as citizen filtering by fiscal code)
- Method-local variables (`result`) are scoped per method and must be declared in each method
- Compile and run are separate checks: compile validates syntax/types, run validates behavior

### Where we left off
Doctor filtering is working and tested. Repository is public with documentation and license in place.

### Next session — pick up here
- Add `cancelAppointment(...)` in `AppointmentService`
- Decide whether cancellation should be by object or by `appointmentId`
- Test success and not-found cancellation scenarios in `Main`

---

## Session 6 — July 14, 2026

### What we built
- Set up Maven on Windows and verified local build commands (`mvn -v`, `mvn clean compile`)
- Practiced full manual build flow to understand what Maven automates (`javac`, classpath run, manual jar creation)
- Implemented `cancelAppointment(Appointment appointment)` in `AppointmentService`
- Wired cancellation call in `Main` and verified the app compiles/runs cleanly

### Key concepts learned
- Maven is not a framework; it automates build/test/package tasks but does not replace Core Java logic
- Manual compile loop is useful for fundamentals, Maven is useful for repeatable workflow
- Common Java bug pattern: `=` vs `==` in `if` conditions
- `List.remove(...)` returns a boolean indicating whether an element was actually removed

### Current status
- `bookAppointment`, `getAppointments`, `getAppointmentsForCitizen`, `getAppointmentsForDoctor`, and `cancelAppointment` are present in `AppointmentService`
- Build currently succeeds with `mvn clean compile`
- Remaining warning: unused `import java.sql.Array;` in `AppointmentService`

### Next session — pick up here
- Remove unused import and do small cleanup (`appointments` can be `final`)
- Evaluate switching cancel logic to `cancelAppointment(String appointmentId)` for ID-based behavior
- Add basic tests under `src/test/java` for booking conflict, doctor filter, and cancellation

---

## Session 7 — July 14, 2026 (part 2)

### What we built
- Refactored cancellation logic to ID-based behavior: `cancelAppointment(String appointmentId)` in `AppointmentService`
- Implemented list traversal with index-based removal and early method exit when a match is found
- Added first `try/catch` blocks in `Main` to handle cancellation outcomes without crashing
- Tested both cancellation paths:
  - Existing appointment (`APPT002`) -> successful cancellation message
  - Non-existing appointment (`APPT999`) -> graceful error message

### Key concepts learned
- ID-based operations are more realistic than object-reference-based operations for business actions
- `try/catch` lets the program continue running after expected failures
- Throw in service layer, catch in caller (`Main`) is a clean separation of responsibilities
- Remove-from-list while iterating is safest with index-based loop in this learning stage

### Current status
- `AppointmentService` now includes:
  - `bookAppointment(Appointment appointment)`
  - `cancelAppointment(String appointmentId)`
  - `getAppointments()`
  - `getAppointmentsForCitizen(String fiscalCode)`
  - `getAppointmentsForDoctor(String doctorId)`
- `Main` demonstrates both success and failure flows for cancellation using `try/catch`

### Next session — pick up here
- Clean output formatting in `Main` (keep only behavior-verification prints)
- Add basic tests under `src/test/java` for conflict booking, doctor filter, and cancel by ID
- Start planning a light CLI-style interaction layer before moving to Spring Boot
