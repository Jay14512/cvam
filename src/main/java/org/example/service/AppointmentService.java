package org.example.service;

import org.example.model.Appointment;


import java.sql.Array;
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

    public void cancelAppointment(Appointment appointment){
        //Check if Appointment exists

    }

    public List<Appointment> getAppointments(){
        return appointments;
    }
    public List<Appointment> getAppointmentsForCitizen(String fiscalCode){
        List<Appointment> result = new ArrayList<>();
        //Show appointments for a specific citizen
        for (Appointment appointment : appointments){
            if (appointment.getCitizen().getFiscalCode().equals(fiscalCode)){
                result.add(appointment);
            }
        }
        return result;
    }


    public List<Appointment> getAppointmentsForDoctor(String doctorId){
        List<Appointment> result = new ArrayList<>();
        //Show appointments for a specific doctor
        for (Appointment appointment : appointments){
            if (appointment.getDoctor().getDoctorId().equals(doctorId)){
                result.add(appointment);
            }
        }
        return result;
    }


}