package org.fis.student.sre.model;

//import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;


public class PetSitter extends User{
    private List <Appointment> appointments;
    private int listIndex;
    private int max = 50;


    public PetSitter (String username, String password, String role, BufferedImage imageOfCertification) {
        super(username, password, role, imageOfCertification);
        this.appointments = new ArrayList<Appointment>(max);
        this.listIndex = 0;
    }

    private void listIsFull() {
        max += 50;
        List <Appointment> aux;
        aux = new ArrayList<Appointment>(max);
        for (Appointment ap : appointments) {
            aux.add(ap);
        }
        appointments = aux;
    }

    private void addAppointmentInList(Appointment ap) {
        this.appointments.add(ap);
        this.listIndex++;
        if (this.listIndex == max) {
            listIsFull();
        }
    }

    public String seePreviousAppointments() {
        String s = new String();
        if (this.appointments != null) {
            for (Appointment ap : appointments) {
                if (ap.getStatus() != "processing") {
                    s += ap.toString();
                }
            }
        } else {
            s = "The list is empty";
        }
        return s;
    }

    public void acceptAppointment(Appointment ap) {
        ap.status = "ACCEPT";
    }

    public void denyAppointment(Appointment ap) {
        ap.status = "DENIED";
    }

    public void seeRequests() {
        if (this.appointments != null) {
            for (Appointment ap : appointments) {
                if (ap.getStatus() == "processing") {
                    //afisare detalii despre cerere
                    //afisare buton cu ACCEPT sau DENY
                    //dupa apasare buton, se trece la urmatoarea
                }
            }
        } else {
            System.out.println("The list is empty");
        }
    }

}

