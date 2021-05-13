package org.fis.student.sre.exceptions;

public class AppointmentAlreadyExistsException extends Throwable {
    private String usernamePetSitter;
    private String description;

    public AppointmentAlreadyExistsException(String username, String description) {
        super(String.format("An appointment with the username %s and description %s already exists!", username, description));
        this.usernamePetSitter = username;
        this.description = description;
    }

    public String getUsername() {
        return usernamePetSitter;
    }

    public String getDescription() {
        return description;
    }
}
