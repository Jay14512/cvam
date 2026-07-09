package org.example.model;

public class Doctor extends User{
    private String doctorId;

    public Doctor(String firstName, String lastName, String fiscalCode, String email, String doctorId){
        super(firstName, lastName,  fiscalCode, email);

        //VALIDATION RULE
        //Doctor ID
        if(doctorId == null || doctorId.isEmpty()){
            throw new IllegalArgumentException("Doctor ID is required");
        }


        this.doctorId = doctorId;

    }
    public String getDoctorId(){
        return doctorId;
    }
}
