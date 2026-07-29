package org.example.exception;


//Extend RuntimeException so we don't force the service to use try/catch blocks everywhere
public class AppointmentNotFoundException extends RuntimeException {

    //Constructor that accepts custom message string
    public AppointmentNotFoundException(String message){
        //"super" passes custom text up to the main Java exception engine
        super(message);
    }
}