package org.example.model;
import java.time.LocalDateTime;

public class Appointment {
    private String appointmentId;
    private Citizen citizen;
    private Doctor doctor;
    private LocalDateTime dateTime;
    private String vaccineType;

    public Appointment(String appointmentId, Citizen citizen, Doctor doctor, LocalDateTime dateTime, String vaccineType){
        this.appointmentId = appointmentId;
        this.citizen = citizen;
        this.doctor = doctor;
        this.dateTime = dateTime;
        this.vaccineType = vaccineType;
    }

    public String getAppointmentId(){
        return appointmentId;
    }

    public Citizen getCitizen(){
        return citizen;
    }

    public Doctor getDoctor(){
        return doctor;
    }

    public LocalDateTime getDateTime(){
        return dateTime;
    }

    public String getVaccineType(){
        return vaccineType;
    }
}
