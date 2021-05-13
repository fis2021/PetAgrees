package org.fis.student.sre.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import org.fis.student.sre.exceptions.AppointmentAlreadyExistsException;
import org.fis.student.sre.exceptions.NotExistingAppointmentException;
import org.fis.student.sre.exceptions.UsernameAlreadyExistsException;
import org.fis.student.sre.model.Appointment;
import org.fis.student.sre.model.User;
import org.fis.student.sre.services.AppointmentService;
import org.fis.student.sre.model.PetSitter;
import org.fis.student.sre.services.UserService;

import java.util.ArrayList;

public class RequestController {

    //protected Appointment appointment;

    private static User currentUser;

    @FXML
    private Text requestMessage;
    @FXML
    private Text detailsAboutRequest;
    @FXML
    private ChoiceBox status;

    @FXML
    public void initialize() {
        status.getItems().addAll("ACCEPT", "DENY");
    }



    @FXML
    public void handleRequestAction() {
        ArrayList <Appointment> requestListNames = UserService.getAppointmentsList(this.getCurrentUser().getUsername());
        if (requestListNames == null) {
            requestMessage.setText("Sorry. You don't have any request:(");
        } else {
            detailsAboutRequest.setText(currentUser.getAppointments().toString());
            try {
                for (Appointment appointment : requestListNames) {
                    if ((String) status.getValue() == "ACCEPT") {
                        Object app = status.getUserData();
                        UserService.acceptAppointment(appointment);
                        requestMessage.setText("Request accepted successfully!");
                    }
                    if ((String) status.getValue() == "DENY") {
                        Object app = status.getUserData();
                        UserService.denyAppointment(appointment);
                        requestMessage.setText("Request denied successfully!");
                    }
                }
            } catch ( NotExistingAppointmentException e) {
                requestMessage.setText(e.getMessage());
            }
        }
    }

    public static User getCurrentUser() {
        return currentUser;
    }
}

