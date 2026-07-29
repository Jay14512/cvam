package org.example.service;

import org.example.exception.AppointmentConflictException;
import org.example.exception.AppointmentNotFoundException;
import org.example.exception.InvalidAppointmentException;
import org.example.model.Appointment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AppointmentService {
    private final List<Appointment> appointments = new ArrayList<>();

    public void bookAppointment(Appointment appointment) {
        //GUARD RAIL
        //Layer 1: Guard rail for container itself
        if (appointment == null) {
            throw new InvalidAppointmentException("Appointment data cannot be null.");
        }
        //Layer 2: Guard rails for the required text fields inside the object
        if (appointment.getAppointmentId() == null || appointment.getAppointmentId().trim().isEmpty()) {
            throw new InvalidAppointmentException("Invalid input: Appointment ID cannot be null or blank.");
        }

        if (appointment.getDoctor() == null || appointment.getDoctor().getDoctorId() == null || appointment.getDoctor().getDoctorId().trim().isEmpty()) {
            throw new InvalidAppointmentException("Doctor ID cannot be null or blank.");
        }

        if (appointment.getCitizen() == null || appointment.getCitizen().getFiscalCode() == null || appointment.getCitizen().getFiscalCode().trim().isEmpty()) {
            throw new InvalidAppointmentException("Citizen can not be null or empty.");
        }

        //CONTRACT RULE 1 & 2: REJECT DUPLICATE IDs (CASE-INSENSITIVE)
        for (Appointment existing : appointments) {
            if (existing.getAppointmentId().equalsIgnoreCase(appointment.getAppointmentId())) {
                throw new AppointmentConflictException("Booking failed: Appointment ID already exists.");
            }
        }

        //CONTRACT RULE 2: CASE-INSENSITIVE DOUBLE BOOKING GUARD
        for (Appointment existing : appointments) {
            if (existing.getDoctor().getDoctorId().equalsIgnoreCase(appointment.getDoctor().getDoctorId()) && existing.getDateTime().equals(appointment.getDateTime())) {
                throw new AppointmentConflictException("Appointment already exists for this doctor at this time.");
            }
        }

        appointments.add(appointment);
    }

    public void cancelAppointment(String appointmentId) {
        //GUARD RAIL
        if (appointmentId == null || appointmentId.isEmpty() || appointmentId.trim().isEmpty()) {
            throw new InvalidAppointmentException("Invalid input: Appointment ID cannot be null, empty or blank");
        }

        //Compare appointment IDs until a match is found or throw Error
        for (int i = 0; i < appointments.size(); i++) {
            Appointment currentAppointment = appointments.get(i);
            if (currentAppointment.getAppointmentId().equalsIgnoreCase(appointmentId)) {
                appointments.remove(i);
                return;
            }
        }
        throw new AppointmentNotFoundException("Appointment not found");
    }

    private void sortAppointments(List<Appointment> list, boolean ascending) {
        Comparator<Appointment> ascendingComparator =
                Comparator.comparing(Appointment::getDateTime)
                        .thenComparing(Appointment::getAppointmentId);

        if (ascending) {
            list.sort(ascendingComparator); //True: Oldest to newest
        } else {
            list.sort(
                    Comparator.comparing(Appointment::getDateTime)
                            .reversed()
                            .thenComparing(Comparator.comparing(Appointment::getAppointmentId).reversed()) //False: Newest to oldest
            );
        }
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public List<Appointment> getAppointmentsForCitizen(String fiscalCode) {
        return getAppointmentsForCitizen(fiscalCode, true);
    }

    public List<Appointment> getAppointmentsForCitizen(String fiscalCode, boolean ascending) {
        //VALIDATION
        if (fiscalCode == null || fiscalCode.isEmpty() || fiscalCode.trim().isEmpty()) {
            throw new InvalidAppointmentException("Fiscal Code cannot be null, empty or blank");
        }

        //FILTER
        List<Appointment> filteredAppointments = new ArrayList<>();
        //Show appointments for a specific citizen
        for (Appointment appointment : appointments) {
            if (appointment.getCitizen().getFiscalCode().equalsIgnoreCase(fiscalCode)) {
                filteredAppointments.add(appointment);
            }
        }

        //SORTING
        sortAppointments(filteredAppointments, ascending);

        return Collections.unmodifiableList(filteredAppointments);
    }

    public List<Appointment> getAppointmentsForDoctor(String doctorId) {
        return getAppointmentsForDoctor(doctorId, true);
    }

    public List<Appointment> getAppointmentsForDoctor(String doctorId, boolean ascending) {
        //VALIDATION
        if (doctorId == null || doctorId.isEmpty() || doctorId.trim().isEmpty()) {
            throw new InvalidAppointmentException("Doctor ID cannot be null, empty or blank");
        }

        //FILTER
        List<Appointment> filteredAppointments = new ArrayList<>();
        //Show appointments for a specific doctor
        for (Appointment appointment : appointments) {
            if (appointment.getDoctor().getDoctorId().equalsIgnoreCase(doctorId)) {
                filteredAppointments.add(appointment);
            }
        }

        //SORT
        sortAppointments(filteredAppointments, ascending);

        return Collections.unmodifiableList(filteredAppointments);
    }

}