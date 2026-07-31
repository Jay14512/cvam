package org.example.model;

@SuppressWarnings("unused")

public class Doctor extends User {
    private final String doctorId;

    public Doctor(String firstName, String lastName, String fiscalCode, String email, String doctorId) {
        super(firstName, lastName, fiscalCode, email);

        //VALIDATION RULE
        //Doctor ID
        if (doctorId == null || doctorId.isEmpty()) {
            throw new IllegalArgumentException("Doctor ID is required");
        }


        this.doctorId = doctorId;

    }

    public String getDoctorId() {
        return doctorId;
    }
}
