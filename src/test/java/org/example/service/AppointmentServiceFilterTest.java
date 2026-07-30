package org.example.service;

import org.example.exception.InvalidAppointmentException;
import org.example.model.Appointment;
import org.example.model.Citizen;
import org.example.model.Doctor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentServiceFilterTest {
    private AppointmentService service;
    private Doctor doctor1;
    private Doctor doctor2;
    private Citizen citizen1;
    private Citizen citizen2;
    private Citizen citizen3;


    @BeforeEach
    public void setUp() {
        //Create fresh service before each test
        service = new AppointmentService();

        //Create reusable test objects
        doctor1 = new Doctor("Giovanni", "Verdi", "VRDGVN70A01H501Z", "giovanni@dottore.it", "556987");
        doctor2 = new Doctor("Luca", "Rossi", "RSSLCU90A01H501Z", "luca@dottore.it", "556989");
        citizen1 = new Citizen("Luigi", "Verdi", "VRDLGU75A01H501Z", "luigi@verdi.com", "+3928974157", LocalDate.of(1975, 1, 1));
        citizen2 = new Citizen("Giulia", "Bianchi", "BNCGIU85A01H501Z", "giulia@bianchi.com", "+3928974158", LocalDate.of(1985, 1, 1));
        citizen3 = new Citizen("Mario", "Rossi", "RSSMRA80A01H501Z", "mario@rossi.com", "+3928974156", LocalDate.of(1968, 6, 8));
    }

    private void bookAppointments(int... appointmentNumbers) {

        for (int appointmentNumber : appointmentNumbers) {
            Appointment appointment = switch (appointmentNumber) {
                case 1 ->
                        new Appointment("APPT001", citizen1, doctor1, LocalDateTime.of(2024, 3, 12, 11, 30), "Moderna");
                case 2 ->
                        new Appointment("APPT002", citizen2, doctor1, LocalDateTime.of(2024, 3, 13, 10, 30), "Pfizer");
                case 3 ->
                        new Appointment("APPT003", citizen1, doctor2, LocalDateTime.of(2024, 3, 8, 11, 45), "Moderna");
                case 4 -> new Appointment("APPT004", citizen2, doctor2, LocalDateTime.of(2024, 3, 18, 9, 15), "Pfizer");

                case 5 ->
                        new Appointment("APPT005", citizen3, doctor1, LocalDateTime.of(2024, 4, 12, 11, 30), "Moderna");

                case 6 ->
                        new Appointment("APPT006", citizen3, doctor2, LocalDateTime.of(2024, 4, 12, 11, 30), "Pfizer");
                default -> throw new IllegalArgumentException("Unknown appointment number: " + appointmentNumber);
            };
            service.bookAppointment(appointment);

        }

    }

    private void assertAppointmentOrder(List<Appointment> result, String... expectedIds) {
        //1. Check that the list size matches exactly how many IDs we passed in
        assertEquals(expectedIds.length, result.size());

        //2. Loop through and check every single position automatically
        for (int i = 0; i < expectedIds.length; i++) {
            assertEquals(expectedIds[i], result.get(i).getAppointmentId());
        }
    }

    //CITIZEN TESTS

    @Test
    public void testGetAppointmentsForCitizen_WithMatches() {
        //Arrange: create and book 6 total appointments in system
        bookAppointments(1, 2, 3, 4, 5, 6);


        //Act: Invoke the specific filter method we want to test
        List<Appointment> result = service.getAppointmentsForCitizen(citizen1.getFiscalCode());

        //Assert: citizen gets only their own appointments
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(a -> a.getCitizen().getFiscalCode().equals(citizen1.getFiscalCode())));
    }

    @Test
    public void testGetAppointmentsForCitizen_OneArgVersionDefaultsToAscending() {
        //Arrange: create and book 6 total appointments in system
        bookAppointments(1, 2, 3, 4, 5, 6);

        //Act: Invoke the specific filter method we want to test
        List<Appointment> result = service.getAppointmentsForCitizen(citizen2.getFiscalCode());

        //Assert: citizen gets only their own appointments in order asc (by DateTime) by default
        assertAppointmentOrder(result, "APPT002", "APPT004");
    }

    @Test
    public void testGetAppointmentsForCitizen_AscendingOrderReturnsOldestFirst() {
        //Arrange: create and book 6 total appointments in system
        bookAppointments(1, 2, 3, 4, 5, 6);

        //Act: Invoke the specific filter method we want to test
        List<Appointment> result = service.getAppointmentsForCitizen(citizen1.getFiscalCode(), true);

        //Assert: citizen gets only their own appointments in order asc (by DateTime)
        assertAppointmentOrder(result, "APPT003", "APPT001");
    }

    @Test
    public void testGetAppointmentsForCitizen_DescendingOrderReturnsNewestFirst() {
        //Arrange: create and book 6 total appointments in system
        bookAppointments(1, 2, 3, 4, 5, 6);

        //Act: Invoke the specific filter method we want to test
        List<Appointment> result = service.getAppointmentsForCitizen(citizen2.getFiscalCode(), false);

        //Assert: citizen gets only their own appointments in order desc (by DateTime)
        assertAppointmentOrder(result, "APPT004", "APPT002");
    }

    @Test
    public void testGetAppointmentsForCitizen_IdenticalTimes_ReturnsOrderedByIdAscending() {
        //Arrange: create and book 6 total appointments in system
        bookAppointments(1, 2, 3, 4, 6, 5);

        //Act: Invoke the specific filter method we want to test
        List<Appointment> result = service.getAppointmentsForCitizen(citizen3.getFiscalCode(), true);

        //Assert: citizen gets only their own appointments in order asc (by ID)
        assertAppointmentOrder(result, "APPT005", "APPT006");
    }

    @Test
    public void testGetAppointmentsForCitizen_IdenticalTimes_ReturnsOrderedByIdDescending() {
        //Arrange: create and book 6 total appointments in system
        bookAppointments(1, 2, 3, 4, 5, 6);

        //Act: Invoke the specific filter method we want to test
        List<Appointment> result = service.getAppointmentsForCitizen(citizen3.getFiscalCode(), false);

        //Assert: citizen gets only their own appointments in order desc (by ID)
        assertAppointmentOrder(result, "APPT006", "APPT005");

    }

    //DOCTOR TESTS

    @Test
    public void testGetAppointmentsForDoctor_WithMatches() {
        //Arrange: create and book 6 total appointments in system
        bookAppointments(1, 2, 3, 4, 5, 6);


        //Act: Invoke the specific filter method we want to test
        List<Appointment> result = service.getAppointmentsForDoctor(doctor1.getDoctorId());


        //Assert: doctor gets only their own appointments
        assertEquals(3, result.size());
        assertTrue(result.stream().allMatch(a -> a.getDoctor().getDoctorId().equals(doctor1.getDoctorId())));
    }


    @Test
    public void testGetAppointmentsForDoctor_OneArgVersionDefaultsToAscending() {
        //Arrange: create and book 6 total appointments in system
        bookAppointments(1, 2, 3, 4, 5, 6);

        //Act: Invoke the specific filter method we want to test
        List<Appointment> result = service.getAppointmentsForDoctor(doctor2.getDoctorId());

        //Assert: doctor gets only their own appointments in order asc (by DateTime) by default
        assertAppointmentOrder(result, "APPT003", "APPT004", "APPT006");

    }

    @Test
    public void testGetAppointmentsForDoctor_AscendingOrderReturnsOldestFirst() {
        //Arrange: create and book 6 total appointments in system
        bookAppointments(1, 2, 3, 4, 5, 6);

        //Act: Invoke the specific filter method we want to test
        List<Appointment> result = service.getAppointmentsForDoctor(doctor1.getDoctorId(), true);

        //Assert: doctor gets only their own appointments in order asc (byDateTime)
        assertAppointmentOrder(result, "APPT001", "APPT002", "APPT005");
    }

    @Test
    public void testGetAppointmentsForDoctor_DescendingOrderReturnsNewestFirst() {
        //Arrange: create and book 6 total appointments in system
        bookAppointments(1, 2, 3, 4, 5, 6);

        //Act: Invoke the specific filter method we want to test
        List<Appointment> result = service.getAppointmentsForDoctor(doctor2.getDoctorId(), false);

        //Assert: doctor gets only their own appointments in order desc (by DateTime)
        assertAppointmentOrder(result, "APPT006", "APPT004", "APPT003");

    }

    @Test
    public void testGetAppointmentsForCitizen_NoMatchesReturnsEmptyList() {
        //Arrange: create and book appointments in system
        bookAppointments(2, 4);


        //Act: Invoke the specific filter method we want to test
        List<Appointment> result = service.getAppointmentsForCitizen(citizen1.getFiscalCode());

        //Assert: verify List is empty if no appointments for requested citizen booked
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAppointmentsForDoctor_NoMatchesReturnsEmptyList() {
        //Arrange: create and book appointments in system
        bookAppointments(3, 4);


        //Act: Invoke the specific filter method we want to test
        List<Appointment> result = service.getAppointmentsForDoctor(doctor1.getDoctorId());

        //Assert: verify List is empty if no appointments for requested doctor booked
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAppointmentsForCitizen_NullFiscalCodeThrowsException() {

        //Assert: throws IllegalArgumentException when Citizen is null
        assertThrows(InvalidAppointmentException.class, () -> service.getAppointmentsForCitizen(null));
    }

    @Test
    public void testGetAppointmentsForDoctor_NullDoctorIdThrowsException() {

        //Assert: throws IllegalArgumentException when Doctor is null
        assertThrows(InvalidAppointmentException.class, () -> service.getAppointmentsForDoctor(null));
    }

}
