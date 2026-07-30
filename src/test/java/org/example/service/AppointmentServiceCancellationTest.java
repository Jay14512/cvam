package org.example.service;

import org.example.exception.AppointmentNotFoundException;
import org.example.exception.InvalidAppointmentException;
import org.example.model.Appointment;
import org.example.model.Citizen;
import org.example.model.Doctor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class AppointmentServiceCancellationTest {
    private AppointmentService service;
    private Doctor doctor;
    private Citizen citizen;

    @BeforeEach
    public void setUp() {
        //Create fresh service before each test
        service = new AppointmentService();

        //Create reusable test objects
        doctor = new Doctor("Luca", "Rossi", "RSSLCU90A01H501Z", "luca@dottore.it", "556989");
        citizen = new Citizen("Giulia", "Bianchi", "BNCGIU85A01H501Z", "giulia@bianchi.com", "+3928974158", LocalDate.of(1985, 1, 1));
    }

    @Test
    public void testCancelAppointmentSuccess() {
        //Arrange:
        // create appointment
        Appointment appointment1 = new Appointment("APPT001", citizen, doctor, LocalDateTime.of(2024, 6, 15, 10, 30), "Pfizer");
        Appointment appointment2 = new Appointment("APPT002", citizen, doctor, LocalDateTime.of(2024, 6, 18, 10, 45), "Moderna");

        //book appointment
        service.bookAppointment(appointment1);
        service.bookAppointment(appointment2);

        //Act: cancel appointment
        service.cancelAppointment("APPT002");

        //Assert: verify it was canceled
        assertEquals(1, service.getAppointments().size());
        assertEquals("APPT001", service.getAppointments().getFirst().getAppointmentId());

    }


    @Test
    public void testCancelAppointmentNotFoundThrowsException() {
        //Arrange:
        // create appointment
        Appointment appointment1 = new Appointment("APPT001", citizen, doctor, LocalDateTime.of(2024, 6, 15, 10, 30), "Pfizer");
        Appointment appointment2 = new Appointment("APPT002", citizen, doctor, LocalDateTime.of(2024, 6, 18, 10, 45), "Moderna");

        //book appointment
        service.bookAppointment(appointment1);
        service.bookAppointment(appointment2);


        //Act & Assert: cancel non-existing appointment & verify exception is thrown
        //Not Found Case
        assertThrows(AppointmentNotFoundException.class, () ->
                service.cancelAppointment("Non-existent-ID"));

        assertEquals(2, service.getAppointments().size());

    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "  ", "\t"})//These are fed into the parameter one by one
    public void testCancelAppointmentInvalidInputThrowsException(String invalidId) {
        //Act and Assert:
        //Null / Blank Input Cases
        assertThrows(InvalidAppointmentException.class, () -> service.cancelAppointment(invalidId));

    }

}
