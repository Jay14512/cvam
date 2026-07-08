package org.example.model;

public class Staff extends User {
    private String staffCode;
    private String doctorId;

    public Staff(String firstName, String lastName, String fiscalCode, String email, String staffCode, String doctorId) {
        super(firstName, lastName, fiscalCode, email);
        this.staffCode = staffCode;
        this.doctorId = doctorId;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public String getDoctorId() {
        return doctorId;
    }
}

