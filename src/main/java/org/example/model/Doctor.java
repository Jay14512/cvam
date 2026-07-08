package org.example.model;

public class Doctor extends User{
    private String doctorId;

    public Doctor(String firstName, String lastName, String fiscalCode, String email, String doctorId){
        super(firstName, lastName,  fiscalCode, email);
        this.doctorId = doctorId;

    }
    public String getDoctorId(){
        return doctorId;
    }
}
