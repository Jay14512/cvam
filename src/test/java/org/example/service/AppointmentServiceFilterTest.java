package org.example.service;

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


    @BeforeEach
    public void setUp() {
        //Create fresh service before each test
        service = new AppointmentService();

        //Create reusable test objects
        doctor1 = new Doctor("Giovanni", "Verdi", "VRDGVN70A01H501Z", "giovanni@dottore.it", "556987");
        doctor2 = new Doctor("Luca", "Rossi", "RSSLCU90A01H501Z", "luca@dottore.it", "556989");
        citizen1 = new Citizen("Luigi", "Verdi", "VRDLGU75A01H501Z", "luigi@verdi.com", "+3928974157", LocalDate.of(1975, 1, 1));
        citizen2 = new Citizen("Giulia", "Bianchi", "BNCGIU85A01H501Z", "giulia@bianchi.com", "+3928974158", LocalDate.of(1985, 1, 1));

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
                default -> throw new IllegalArgumentException("Unknown appointment number: " + appointmentNumber);
            };
            service.bookAppointment(appointment);

        }

    }


    @Test
    public void testGetAppointmentsForCitizen_WithMatches() {
        //Arrange: create and book 4 total appointments in system
        bookAppointments(1, 2, 3, 4);


        //Act: Invoke the specific filter method we want to test
        List<Appointment> result = service.getAppointmentsForCitizen(citizen1.getFiscalCode());

        //Assert: citizen gets only their own appointments
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(a -> a.getCitizen().getFiscalCode().equals(citizen1.getFiscalCode())));
    }

    @Test
    public void testGetAppointmentsForDoctor_WithMatches() {
        //Arrange: create and book 4 total appointments in system
        bookAppointments(1, 2, 3, 4);


        //Act: Invoke the specific filter method we want to test
        List<Appointment> result = service.getAppointmentsForDoctor(doctor1.getDoctorId());


        //Assert: doctor gets only their own appointments
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(a -> a.getDoctor().getDoctorId().equals(doctor1.getDoctorId())));
    }

    @Test
    public void testGetAppointmentsForCitizen_NoMatchesReturnsEmptyList() {
        //Arrange: create and book 4 total appointments in system
       bookAppointments(2, 4);


        //Act: Invoke the specific filter method we want to test
        List<Appointment> result = service.getAppointmentsForCitizen(citizen1.getFiscalCode());

        //Assert: verify List is empty if no appointments for requested citizen booked
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAppointmentsForDoctor_NoMatchesReturnsEmptyList() {
        //Arrange: create and book 4 total appointments in system
       bookAppointments(3, 4);


        //Act: Invoke the specific filter method we want to test
        List<Appointment> result = service.getAppointmentsForDoctor(doctor1.getDoctorId());

        //Assert: verify List is empty if no appointments for requested doctor booked
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAppointmentsForCitizen_NullCitizenThrowsException() {

        //Assert: throws IllegalArgumentException when Citizen is null
        assertThrows(IllegalArgumentException.class, () -> service.getAppointmentsForCitizen(null));
    }

    @Test
    public void testGetAppointmentsForDoctor_NullDoctorThrowsException() {

        //Assert: throws IllegalArgumentException when Doctor is null
        assertThrows(IllegalArgumentException.class, () -> service.getAppointmentsForDoctor(null));
    }

}
