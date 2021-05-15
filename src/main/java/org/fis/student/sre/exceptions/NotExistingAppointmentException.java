package org.fis.student.sre.exceptions;

public class NotExistingAppointmentException  extends Exception {

    public NotExistingAppointmentException() {
        super("This appointment doesn't exist!");
    }
}