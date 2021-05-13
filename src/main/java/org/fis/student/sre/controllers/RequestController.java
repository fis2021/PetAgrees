package org.fis.student.sre.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import org.fis.student.sre.exceptions.AppointmentAlreadyExistsException;
import org.fis.student.sre.exceptions.UsernameAlreadyExistsException;
import org.fis.student.sre.model.Appointment;
import org.fis.student.sre.services.AppointmentService;
import org.fis.student.sre.model.PetSitter;

public class RequestController {

    protected Appointment appointment;

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
    public void handleRegisterAction() {
        detailsAboutRequest.setText(appointment.toString());
        try{
            if ((String) status.getValue() == "ACCEPT") {
                Object app = status.getUserData();
                AppointmentService.acceptAppointment(appointment.getUsernamePetSitter(), appointment.getUsernameOwner(), appointment.getTelephoneOwner(), appointment.getDescription(), appointment.getStatus(), appointment.getAddress());
                requestMessage.setText("Request accepted successfully!");
            }
            if ((String) status.getValue() == "DENY") {
                Object app = status.getUserData();
                AppointmentService.denyAppointment(appointment.getUsernamePetSitter(), appointment.getUsernameOwner(), appointment.getTelephoneOwner(), appointment.getDescription(), appointment.getStatus(), appointment.getAddress());
                requestMessage.setText("Request denied successfully!");
            }
        } catch (AppointmentAlreadyExistsException | UsernameAlreadyExistsException e) {
            requestMessage.setText(e.getMessage());
        }
    }
}

