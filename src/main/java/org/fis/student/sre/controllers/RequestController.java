package org.fis.student.sre.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.fis.student.sre.exceptions.NotExistingAppointmentException;
import org.fis.student.sre.model.Appointment;
import org.fis.student.sre.model.User;
import org.fis.student.sre.services.UserService;

import java.io.IOException;
import java.util.ArrayList;

public class RequestController {

    private static User currentUser;

    @FXML
    private BorderPane borderPane;

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
        ArrayList <Appointment> requestList = UserService.getRequestList(this.getCurrentUser().getUsername());
        if (requestList == null) {
            requestMessage.setText("Sorry. You don't have any request:(");
        } else {
            try {
                for (Appointment request : requestList) {
                    detailsAboutRequest.setText(request.toString());
                    if ((String) status.getValue() == "ACCEPT") {
                        Object app = status.getUserData();
                        UserService.acceptAppointment(request);
                        requestMessage.setText("Request accepted successfully!");
                    }
                    if ((String) status.getValue() == "DENY") {
                        Object app = status.getUserData();
                        UserService.denyAppointment(request);
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

