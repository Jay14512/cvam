package org.example.service;

import org.example.exception.AppointmentConflictException;
import org.example.exception.InvalidAppointmentException;
import org.example.model.Appointment;
import org.example.model.Citizen;
import org.example.model.Doctor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class AppointmentServiceBookingTest {
    private AppointmentService service;
    private Doctor doctor;
    private Citizen citizen1;
    private Citizen citizen2;

    @BeforeEach
    public void setUp() {
        //Create a fresh service before each test
        service = new AppointmentService();

        //Create reusable test objects
        doctor = new Doctor("Giovanni", "Verdi", "VRDGVN70A01H501Z", "giovanni@dottore.it", "556987");
        citizen1 = new Citizen("Mario", "Rossi", "RSSMRA70A01H501Z", "mario.rossi@example.com", "+3928974156", LocalDate.of(1968, 6, 8));
        citizen2 = new Citizen("Luigi", "Verdi", "VRDLGU75A01H501Z", "luigi@verdi.com", "+3928974157", LocalDate.of(1975, 1, 1));
    }

    @Test
    public void testBookAppointmentSuccess() {
        //Arrange: create an appointment
        Appointment appointment = new Appointment("APPT001", citizen1, doctor, LocalDateTime.of(2024, 6, 15, 10, 30), "Pfizer");

        //Act: book the appointment
        service.bookAppointment(appointment);

        //Assert: verify it was added
        assertEquals(1, service.getAppointments().size());
        assertEquals("APPT001", service.getAppointments().getFirst().getAppointmentId());

    }

    @Test
    public void testBookAppointmentConflictThrowsException() {
        //Arrange: book one appointment
        Appointment appointment1 = new Appointment("APPT001", citizen1, doctor, LocalDateTime.of(2024, 6, 15, 10, 30), "Pfizer");
        service.bookAppointment(appointment1);

        //Create a conflicting appointment (SAME doctor, SAME time)
        Appointment appointment2 = new Appointment("APPT002", citizen2, doctor, LocalDateTime.of(2024, 6, 15, 10, 30), "Moderna");

        //Act & Assert: verify exception is thrown
        assertThrows(AppointmentConflictException.class, () -> service.bookAppointment(appointment2));
    }

    @Test
    public void testBookAppointmentDuplicateIdThrowsException() {
        //Arrange: book one appointment
        Appointment appointment1 = new Appointment("APPT001", citizen1, doctor, LocalDateTime.of(2024, 6, 15, 10, 30), "Pfizer");
        service.bookAppointment(appointment1);

        //Create Conflicting appointment (different time, SAME ID)
        Appointment appointment2 = new Appointment("appt001", citizen1, doctor, LocalDateTime.of(2024, 6, 15, 11, 30), "Pfizer");

        //Act and Assert:
        assertThrows(AppointmentConflictException.class, () -> service.bookAppointment(appointment2));
    }

    @Test
    public void testBookAppointmentNullThrowsException() {
        //Act and Assert:
        assertThrows(InvalidAppointmentException.class, () -> service.bookAppointment(null));
    }

}



