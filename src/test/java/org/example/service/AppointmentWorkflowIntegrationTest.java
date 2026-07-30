package org.example.service;

import org.example.exception.AppointmentConflictException;
import org.example.exception.AppointmentNotFoundException;
import org.example.exception.InvalidAppointmentException;
import org.example.model.Appointment;
import org.example.model.Citizen;
import org.example.model.Doctor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class AppointmentWorkflowIntegrationTest {
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
    public void executeFullAppointmentLifecycle() throws AppointmentConflictException, AppointmentNotFoundException, InvalidAppointmentException {
        //STEP 1: Book appointment
        //Arrange
        Appointment appointment1 = new Appointment("APPT001", citizen, doctor, LocalDateTime.of(2025, 6, 23, 9, 30), "Pfizer");
        Appointment appointment2 = new Appointment("APPT002", citizen, doctor, LocalDateTime.of(2025, 7, 12, 10, 15), "Pfizer");
        //Act: book appointments
        service.bookAppointment(appointment1);
        service.bookAppointment(appointment2);

        //STEP 2: Filter and Verify Presence
        //Act
        List<Appointment> result = service.getAppointmentsForCitizen(citizen.getFiscalCode());
        //Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(a -> a.getCitizen().getFiscalCode().equals(citizen.getFiscalCode())));

        //STEP 3: Cancel the appointment
        //Act
        service.cancelAppointment("APPT002");

        //STEP 4: Filter and Verify removal
        //Act
        List<Appointment> updatedResults = service.getAppointmentsForCitizen(citizen.getFiscalCode());
        //Assert
        assertEquals(1, updatedResults.size());
        assertTrue(updatedResults.stream().allMatch(a -> a.getCitizen().getFiscalCode().equals(citizen.getFiscalCode())));

    }


}
