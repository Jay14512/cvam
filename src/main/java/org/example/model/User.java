package org.example.model;

public abstract class  User {
    private String firstName;
    private String lastName;
    private String fiscalCode;
    private String email;

    public User (String firstName, String lastName, String fiscalCode, String email) {

        //VALIDATION RULES
        //First Name
        if (firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("First name cannot be empty");
        }

        //Last Name
        if (lastName == null || lastName.isEmpty()){
            throw new IllegalArgumentException("Last name cannot be empty");
        }

        //Email
        if (email == null || !email.contains("@")){
            throw new IllegalArgumentException("Email is invalid");
        }



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

