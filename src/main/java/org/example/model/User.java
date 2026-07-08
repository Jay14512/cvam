package org.example.model;

public abstract class  User {
    private String firstName;
    private String lastName;
    private String fiscalCode;
    private String email;

    public User (String firstName, String lastName, String fiscalCode, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fiscalCode = fiscalCode;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFiscalCode() {
        return fiscalCode;
    }

    public String getEmail() {
        return email;
    }
}

