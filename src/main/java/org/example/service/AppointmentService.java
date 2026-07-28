package org.example.service;

import org.example.model.Appointment;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AppointmentService {
    private final List<Appointment> appointments = new ArrayList<>();

    public void bookAppointment(Appointment appointment) {
        //Check if Appointment already exists
        for (Appointment existing : appointments) {
            if (existing.getDoctor().getDoctorId().equals(appointment.getDoctor().getDoctorId()) && existing.getDateTime().equals(appointment.getDateTime())) {
                throw new IllegalArgumentException("Appointment already exists for this doctor at this time");
            }

        }
        appointments.add(appointment);
    }

    public void cancelAppointment(String appointmentId) {
        //Compare appointment IDs until a match is found or throw Error
        for (int i = 0; i < appointments.size(); i++) {
            Appointment currentAppointment = appointments.get(i);
            if (currentAppointment.getAppointmentId().equals(appointmentId)) {
                appointments.remove(i);
                return;
            }
        }
        throw new IllegalArgumentException("Appointment not found");
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
        if (fiscalCode == null) {
            throw new IllegalArgumentException("Fiscal Code cannot be null");
        }

        //FILTER
        List<Appointment> filteredAppointments = new ArrayList<>();
        //Show appointments for a specific citizen
        for (Appointment appointment : appointments) {
            if (appointment.getCitizen().getFiscalCode().equals(fiscalCode)) {
                filteredAppointments.add(appointment);
            }
        }

        //SORTING
        sortAppointments(filteredAppointments, ascending);

        return filteredAppointments;
    }

    public List<Appointment> getAppointmentsForDoctor(String doctorId) {
        return getAppointmentsForDoctor(doctorId, true);
    }

    public List<Appointment> getAppointmentsForDoctor(String doctorId, boolean ascending) {
        //VALIDATION
        if (doctorId == null) {
            throw new IllegalArgumentException("Doctor ID cannot be null");
        }

        //FILTER
        List<Appointment> filteredAppointments = new ArrayList<>();
        //Show appointments for a specific doctor
        for (Appointment appointment : appointments) {
            if (appointment.getDoctor().getDoctorId().equals(doctorId)) {
                filteredAppointments.add(appointment);
            }
        }

        //SORT
        sortAppointments(filteredAppointments, ascending);

        return filteredAppointments;
    }

}