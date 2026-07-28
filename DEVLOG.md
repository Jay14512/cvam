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

---

## Session 8 — July 16, 2026

### What we built
- Created three test class skeletons with full JUnit5 setup:
  - `AppointmentServiceBookingTest.java` — for testing `bookAppointment()` conflict logic
  - `AppointmentServiceCancellationTest.java` — for testing `cancelAppointment()` success and failure
  - `AppointmentServiceFilterTest.java` — for testing `getAppointmentsForCitizen()` and `getAppointmentsForDoctor()`
- Set up proper imports in all test files: `@BeforeEach`, `@Test`, `assertEquals`, `assertThrows`
- Test class bodies are empty scaffolds, ready for test method implementation

### Key concepts learned
- Test structure: one test class per service method or logical group
- `@BeforeEach` runs setup code before each test (perfect for creating fresh test data)
- `@Test` marks a method as a test that JUnit will automatically run
- `assertEquals(expected, actual)` verifies behavior assertions
- `assertThrows(ExceptionType.class, ...)` verifies exceptions are thrown under expected conditions
- Tests live in separate source tree (`src/test/java`) and are not packaged with production code

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

src/test/java/org/example/
    service/
        AppointmentServiceBookingTest.java      (empty, ready for methods)
        AppointmentServiceCancellationTest.java (empty, ready for methods)
        AppointmentServiceFilterTest.java       (empty, ready for methods)
```

### Next session — pick up here
- Implement test methods in `AppointmentServiceBookingTest`:
  - Test successful booking with no conflicts
  - Test `IllegalArgumentException` when booking a conflicting doctor + time slot
- Implement test methods in `AppointmentServiceCancellationTest`:
  - Test successful cancellation by ID
  - Test `IllegalArgumentException` when canceling non-existent ID
- Implement test methods in `AppointmentServiceFilterTest`:
  - Test `getAppointmentsForCitizen()` returns only the selected citizen's appointments
  - Test `getAppointmentsForDoctor()` returns only the selected doctor's appointments
- Run all tests with Maven (`mvn test`) to verify they pass

---

## Session 9 — July 18, 2026

### What we built
- Implemented `AppointmentServiceBookingTest` with two test methods:
  - successful booking adds the appointment to the list
  - conflicting booking (same doctor + same date/time) throws `IllegalArgumentException`
- Implemented `AppointmentServiceCancellationTest` with two test methods:
  - successful cancellation by ID removes only the targeted appointment
  - canceling a non-existent ID throws `IllegalArgumentException`
- Validated both test classes with Maven test selection for just booking + cancellation tests

### Key concepts learned
- `assertThrows(...)` must wrap the call that is expected to fail (inside the lambda)
- Exception type must match service behavior exactly (`IllegalArgumentException` in this project)
- In cancellation success tests, booking first is valid setup for the action under test
- Stronger assertions check both list size and remaining/canceled appointment IDs

### Current status
- `AppointmentServiceBookingTest` and `AppointmentServiceCancellationTest` are implemented and passing
- Verified run result: 4 tests executed, 0 failures, 0 errors
- `AppointmentServiceFilterTest` is still scaffold-only and is the next implementation target

### Next session — pick up here
- Implement `AppointmentServiceFilterTest`:
  - `getAppointmentsForCitizen(...)` should return only the selected citizen's appointments
  - `getAppointmentsForDoctor(...)` should return only the selected doctor's appointments
- Add clear appointment IDs in filter tests and assert expected IDs are returned
- Run all tests together with `mvn test`

---

## Session 10 — July 19, 2026

### What we built
- Completed `AppointmentServiceFilterTest` with six concrete test methods:
  - `getAppointmentsForCitizen` with matches
  - `getAppointmentsForCitizen` no matches (empty list)
  - `getAppointmentsForCitizen` null input (throws)
  - `getAppointmentsForDoctor` with matches
  - `getAppointmentsForDoctor` no matches (empty list)
  - `getAppointmentsForDoctor` null input (throws)
- Added fail-fast null validation in `AppointmentService`:
  - `getAppointmentsForCitizen(String fiscalCode)` now throws `IllegalArgumentException` when `fiscalCode` is null
  - `getAppointmentsForDoctor(String doctorId)` now throws `IllegalArgumentException` when `doctorId` is null
- Standardized assert style in filter tests using size checks + `allMatch(...)` ownership checks
- Corrected vaccine-type typo in test fixtures (`Pfizer`)
- Ran test suite locally; all tests reported as passing at end of session

### Key concepts learned
- "No matches" and "invalid input" are different scenarios and should be tested separately
- Empty list is the right result for valid input with no data
- `assertThrows(...)` should be used only when the service contract explicitly rejects input
- Putting validation at the top of a method creates a clear, predictable API contract
- Test naming works best when concise but scenario-specific (not only `...Success`)

### Current status
- `AppointmentService` now enforces null checks for both filtering methods
- All three test classes are implemented (`Booking`, `Cancellation`, `Filter`)
- You completed about 91 minutes of focused coding in this session

### Next session — pick up here
- Reduce fixture duplication in `AppointmentServiceFilterTest` with a small helper method ✅ done in Session 11
- Decide whether `getAppointmentsForCitizen`/`getAppointmentsForDoctor` should return results sorted by date/time
- If sorting is added, extend filter tests with deterministic order assertions

---

## Session 11 — July 21, 2026

### What we built
- Refactored `AppointmentServiceFilterTest` with a private helper method `bookAppointments(int... appointmentNumbers)`
- The helper maps numbers 1–4 to the four standard citizen/doctor combinations, books them into `service`, and replaced all repeated fixture setup in every test
- Removed the `List` return value from the helper entirely after reasoning through the fact that no test actually needs the returned list — booking happens as a side effect of the method call
- Removed unnecessary fixture setup from both null-input tests — those tests only check input validation, so no appointments need to be booked at all
- All six tests still pass after refactoring

### Key concepts learned
- A private helper method inside a test class is just a regular method — it reduces duplication without changing test behavior
- `int... appointmentNumbers` is Java varargs — lets you pass any number of int arguments like `bookAppointments(1, 2, 3, 4)` or `bookAppointments(2, 4)`
- The return value of a method is optional to capture — if you never use it, you can drop the variable assignment entirely
- A method that returns a value but whose caller never uses that return value is a signal to reconsider the return type
- `void` is the right return type when a method's purpose is purely a side effect (like booking appointments)
- Arrange block assertions (`assertEquals` on setup data) are usually noise — they test the helper, not the behavior under test; remove them
- Tests should only assert the behavior named in the test method — nothing more

### Current status
- `AppointmentServiceFilterTest` is fully refactored: 128 lines down from 157, all six tests passing
- The fixture helper `bookAppointments(int...)` centralises all appointment creation and booking in one place
- You caught and fixed a subtle fixture error yourself: `testGetAppointmentsForDoctor_NoMatchesReturnsEmptyList` correctly uses appointments 3 and 4 (doctor2 only), not 2 and 4

### Next session — pick up here
- Decide whether `getAppointmentsForCitizen`/`getAppointmentsForDoctor` should return results sorted by date/time
- If sorting is added, extend filter tests with deterministic order assertions

---

## Session 13 — July 24, 2026

### What we built
- Added four new test methods to `AppointmentServiceFilterTest` covering the directional sort overloads:
  - `testGetAppointmentsForCitizen_AscendingOrderReturnsOldestFirst` — calls 2-arg method with `true`, asserts APPT003 is first (March 8 < March 12)
  - `testGetAppointmentsForCitizen_DescendingOrderReturnsNewestFirst` — calls 2-arg method with `false` for citizen2, asserts APPT004 is first (March 18 > March 13)
  - `testGetAppointmentsForDoctor_AscendingOrderReturnsOldestFirst` — calls 2-arg method with `true`, asserts APPT001 is first (March 12 < March 13)
  - `testGetAppointmentsForDoctor_DescendingOrderReturnsNewestFirst` — calls 2-arg method with `false` for doctor2, asserts APPT004 is first (March 18 > March 8)
- All 10 tests in `AppointmentServiceFilterTest` pass

### Key concepts learned
- A test named `_WithMatches` should only fail for filtering reasons — sorting is a separate responsibility and belongs in a separate test
- One test, one reason to fail: mixing filter correctness and sort order correctness into the same test creates misleading failure messages
- For a 2-element list, asserting `getFirst()` is sufficient — if the first is correct, the second must be correct by elimination
- The larger a list could grow, the weaker a single-element assertion becomes — full sequence assertions become more valuable with more data
- `assertEquals(expected, actual)` is cleaner than `assertTrue(...equals(...))` for value comparisons — failure messages are more informative
- The tie-breaker (`thenComparing(appointmentId)`) is currently untested because no fixture has two appointments at the exact same `LocalDateTime`

### Current status
- `AppointmentServiceFilterTest` now has 10 tests covering: matches, no-matches, null input, ascending order, and descending order — for both citizen and doctor filters
- Tie-breaker sort logic exists in the service but has no test coverage yet

### Next session — pick up here
- Add a tie-case test: two appointments for the same citizen with different doctors but the same `LocalDateTime`
- Reason through whether the service booking rules allow this (same time, different doctors — does `bookAppointment` block it?)
- If allowed, add case 5 (and possibly 6) to `bookAppointments` helper and write the tie assertion
- Consider whether a tie-case is also needed for the doctor filter

---

## Session 12 — July 22, 2026

### What we built
- Finalized API behavior for filtered retrieval: support caller-selected sort direction (`ascending`/`descending`)
- Refactored both filter methods to overload style:
  - `getAppointmentsForCitizen(String fiscalCode)` delegates to `getAppointmentsForCitizen(String fiscalCode, boolean ascending)`
  - `getAppointmentsForDoctor(String doctorId)` delegates to `getAppointmentsForDoctor(String doctorId, boolean ascending)`
- Centralized validation (`null` checks + `IllegalArgumentException`) inside the 2-arg methods
- Implemented deterministic sorting by extracting `sortAppointments(List<Appointment> list, boolean ascending)`
  - Primary key: `dateTime`
  - Tie-breaker: `appointmentId`
- Removed duplicate sorting blocks by using the shared private sort helper
- Updated service field declaration to `private final List<Appointment> appointments = new ArrayList<>();`

### Key concepts learned
- Overload delegation pattern: 1-arg wrapper methods can provide defaults while 2-arg methods own the full logic
- Validation should live in the implementation method to avoid duplication and behavior drift
- Avoid recursive delegation loops (1-arg → 2-arg is fine; 2-arg → 1-arg causes recursion)
- Comparator chaining with `thenComparing(...)` gives deterministic order for equal primary keys
- IDE duplicated-code warnings are often refactor opportunities (helper extraction)

### Current status
- `AppointmentService` now supports directional sorting for citizen and doctor filters
- Tie-breaker logic is present and shared through `sortAppointments(...)`
- Session-level check: you reported local tests ran successfully after the refactor

### Next session — pick up here
- Update `AppointmentServiceFilterTest` to cover the new directional overloads:
  - citizen ascending/descending order assertion
  - doctor ascending/descending order assertions
- Add deterministic order assertions based on exact appointment ID sequence
- Add at least one tie-case assertion where feasible with current booking rules
- Run full suite with `mvn test` and keep old 1-arg compatibility tests passing

## Session 14 — July 26, 2026

### What we built
- Strengthened `AppointmentServiceFilterTest` to use deterministic order assertions with full appointment ID sequence checks (not only first element)
- Added compatibility coverage for default 1-arg filter methods:
  - `getAppointmentsForCitizen(String fiscalCode)` defaults to ascending order
  - `getAppointmentsForDoctor(String doctorId)` defaults to ascending order
- Expanded fixtures with a third citizen (`citizen3`) and two additional appointments:
  - `APPT005` and `APPT006` share the same `LocalDateTime`
- Added tie-case coverage to verify deterministic secondary ordering by `appointmentId` when date/time is identical
- Kept existing match/no-match/null validation tests intact while extending directional sort coverage

### Key concepts learned
- Sequence assertions are stronger than single-position assertions for sorting behavior
- Adding `assertEquals(expectedSize, result.size())` improves test robustness and makes failures clearer
- Deterministic sorting tests should validate both:
  - primary key ordering (`dateTime`)
  - tie-break ordering (`appointmentId`)
- Backward compatibility should be tested explicitly, not assumed (1-arg wrappers delegating to 2-arg overloads)
- For larger datasets, avoid asserting hundreds of exact positions; prefer sortedness invariants + ownership + size + focused edge-case sequence checks

### Current status
- `AppointmentServiceFilterTest` now contains 13 tests
- Full project test suite passes:
  - `Tests run: 17, Failures: 0, Errors: 0, Skipped: 0`
  - `BUILD SUCCESS`

### Next session — pick up here
- Optionally add descending-direction tie-case assertion for extra confidence in reverse ordering behavior
- Consider small naming cleanup in null-input tests to reflect null ID/fiscal-code (instead of null object wording)
- Evaluate extracting reusable helper assertions for expected ID order to keep tests concise as fixtures grow

## Session 15 — July 28, 2026

### What we built
- Added descending-direction tie-case coverage in `AppointmentServiceFilterTest` to verify deterministic reverse ordering when `LocalDateTime` values are identical
- Renamed null-input tests to reflect the actual invalid inputs (`null` fiscal code / `null` doctor ID) instead of object wording
- Extracted reusable order assertions via `assertAppointmentOrder(List<Appointment> result, String... expectedIds)` to keep sort tests concise and scalable
- Extended fixture helper usage with the existing case set (`1..6`) to keep test arrangement consistent across filter scenarios

### Key concepts learned
- Tie-case assertions should be checked in both sort directions to validate full comparator behavior
- Test names should describe the invalid parameter, not the surrounding object, for clearer intent and faster debugging
- Helper assertions reduce duplication while preserving strong sequence-level validation
- Sequence assertions (`expected ID order`) are easier to maintain when test fixtures grow

### Current status
- `AppointmentServiceFilterTest` now includes descending tie-case coverage in addition to ascending tie-case coverage
- Null-input filter tests use clearer naming aligned to service method parameters
- Reusable ID-order assertions are in place and used by directional sorting tests

### Next session — pick up here
- Strengthen service input validation tests:
  - `bookAppointment(null)` should throw `IllegalArgumentException`
  - `cancelAppointment(null)` and `cancelAppointment("")` behavior should be specified and tested
- Decide and document service contract details before Spring Boot:
  - duplicate `appointmentId` policy
  - case-sensitivity policy for `fiscalCode` and `doctorId`
  - return-list mutability policy for filter methods
- Introduce domain-specific exceptions (e.g., conflict/not-found) to prepare clean HTTP error mapping later
- Add one workflow-style test (`book -> filter -> cancel -> filter`) as a bridge toward controller-level integration tests
