package org.example;
import java.time.LocalDate;
import org.example.model.Staff;
import org.example.model.Citizen;
import org.example.model.Doctor;



public class Main {
    public static void main(String[] args) {
        // STAFF
        Staff staff = new Staff("Anna", "Bianchi",  "BNCANN90A41H501Z", "anna@clinic.com", "336124", "556987");
        System.out.println("STAFF:");
        System.out.println("First Name: " +staff.getFirstName());
        System.out.println("Staff ID: " +staff.getStaffCode());
        System.out.println("Corresponding Doctor ID: "+staff.getDoctorId());

        // CITIZEN
        Citizen citizen = new Citizen("Mario", "Rossi", "RSSMRA80A01H501Z", "mario@rossi.com", "003928974156", LocalDate.of(1968,6,8));
        System.out.println("CITIZEN: ");
        System.out.println("First Name: " +citizen.getFirstName());
        System.out.println("Fiscal Code: " +citizen.getFiscalCode());
        System.out.println("Phone Number: " +citizen.getPhoneNumber());
        System.out.println("Birth Date: " +citizen.getBirthDate());


        // DOCTOR
        Doctor doctor = new Doctor("Giovanni", "Verdi", "VRDGVN70A01H501Z", "giovanni@dottore.it", "556987");
       System.out.println("DOCTOR: ");
        System.out.println("First Name: " +doctor.getFirstName());
        System.out.println("Last Name: "+doctor.getLastName());
        System.out.println("Doctor ID: " +doctor.getDoctorId());
        }


    }
