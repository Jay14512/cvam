package org.example.service;

import org.example.model.Appointment;

import java.util.ArrayList;
import java.util.List;

public class AppointmentService {
    private List<Appointment> appointments = new ArrayList<>();

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


    public List<Appointment> getAppointments() {
        return appointments;
    }

    public List<Appointment> getAppointmentsForCitizen(String fiscalCode) {
        if (fiscalCode == null) {
            throw new IllegalArgumentException("Fiscal Code cannot be null");
        }
        List<Appointment> result = new ArrayList<>();
        //Show appointments for a specific citizen
        for (Appointment appointment : appointments) {
            if (appointment.getCitizen().getFiscalCode().equals(fiscalCode)) {
                result.add(appointment);
            }
        }
        return result;
    }


    public List<Appointment> getAppointmentsForDoctor(String doctorId) {
        if (doctorId == null) {
            throw new IllegalArgumentException("Doctor ID cannot be null");
        }
        List<Appointment> result = new ArrayList<>();
        //Show appointments for a specific doctor
        for (Appointment appointment : appointments) {
            if (appointment.getDoctor().getDoctorId().equals(doctorId)) {
                result.add(appointment);
            }
        }
        return result;
    }


}