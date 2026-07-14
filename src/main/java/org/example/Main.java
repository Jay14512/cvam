package org.example;

import org.example.model.Appointment;
import org.example.model.Citizen;
import org.example.model.Doctor;
import org.example.model.Staff;
import org.example.service.AppointmentService;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class Main {
    public static void main(String[] args) {
        // STAFF
        Staff staff = new Staff("Anna", "Bianchi", "BNCANN90A41H501Z", "anna@clinic.com", "336124", "556987");


        // CITIZEN
        Citizen citizen1 = new Citizen("Mario", "Rossi", "RSSMRA80A01H501Z", "mario@rossi.com", "+3928974156", LocalDate.of(1968, 6, 8));
        Citizen citizen2 = new Citizen("Luigi", "Verdi", "VRDLGU75A01H501Z", "luigi@verdi.com", "+3928974157", LocalDate.of(1975, 1, 1));
        Citizen citizen3 = new Citizen("Giulia", "Bianchi", "BNCGIU85A01H501Z", "giulia@bianchi.com", "+3928974158", LocalDate.of(1985, 1, 1));


        // DOCTOR
        Doctor doctor1 = new Doctor("Giovanni", "Verdi", "VRDGVN70A01H501Z", "giovanni@dottore.it", "556987");
        Doctor doctor2 = new Doctor("Francesca", "Neri", "NRIFNC80A01H501Z", "francesca@dottore.it", "556988");
        Doctor doctor3 = new Doctor("Luca", "Rossi", "RSSLCU90A01H501Z", "luca@dottore.it", "556989");

        // APPOINTMENT
        Appointment appointment1 = new Appointment("APPT001", citizen1, doctor1, LocalDateTime.of(2024, 6, 15, 10, 30), "Pfizer");


        Appointment appointment2 = new Appointment("APPT002", citizen2, doctor2, LocalDateTime.of(2024, 6, 15, 11, 0), "Pfizer");


        Appointment appointment3 = new Appointment("APPT003", citizen3, doctor1, LocalDateTime.of(2024, 6, 15, 11, 30), "Pfizer");


        AppointmentService service = new AppointmentService();

        service.bookAppointment(appointment1);
        service.bookAppointment(appointment2);
        service.bookAppointment(appointment3);

        //Cancel Appointment
        //Existing Appt
        try {
            service.cancelAppointment("APPT002");
            System.out.println("Appointment APPT002 canceled successfully.");
        } catch (IllegalArgumentException e)
        {
            System.out.println("Could not cancel appointment: "+e.getMessage());
        }
        //Non existing Appt
        try{
            service.cancelAppointment("APPT999");
            System.out.println("Appointment  APPT999 canceled successfully.");
        }  catch (IllegalArgumentException e) {
            System.out.println("Could not cancel appointment: "+e.getMessage());
        }



//New Appointments


        System.out.print("\nMario's appointments: ");
        for (Appointment appt : service.getAppointmentsForCitizen("RSSMRA80A01H501Z")) {
            System.out.println(appt.getAppointmentId());
            System.out.println(appt.getDateTime());
            System.out.println(appt.getDoctor().getLastName());
        }


        System.out.print("\nDr. Verdi's appointments: ");
        for (Appointment appt : service.getAppointmentsForDoctor("556987")) {
            System.out.println(appt.getAppointmentId());
            System.out.println(appt.getDateTime());
        }

        System.out.print("\nDr. Neri's appointments: ");
        for (Appointment appt : service.getAppointmentsForDoctor("556988")) {
            System.out.println(appt.getAppointmentId());
            System.out.println(appt.getDateTime());
        }

    }
}
