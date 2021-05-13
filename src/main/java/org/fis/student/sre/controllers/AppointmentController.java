package org.fis.student.sre.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.fis.student.sre.exceptions.AppointmentAlreadyExistsException;
import org.fis.student.sre.model.Appointment;
import org.fis.student.sre.services.UserService;

import java.text.NumberFormat;


public class AppointmentController {
    @FXML
    private Text appointmentMessage;
    @FXML
    private TextField usernamePetSitterField;
    @FXML
    private TextField usernameOwnerField;
    @FXML
    private NumberFormat telephoneOwnerField;
    @FXML
    private TextField descriptionOwnerField;
    @FXML
    private TextField addressOwnerField;

    @FXML
    public void handleAppointmentAction() {
        try {
            Appointment appointment = new Appointment(usernamePetSitterField.getText(), usernameOwnerField.getText(), telephoneOwnerField.getMaximumIntegerDigits(), descriptionOwnerField.getText(), "processing", addressOwnerField.getText());
            UserService.addAppointmentInAppointmentsList(usernameOwnerField.getText(), appointment);// add appointment in appointment list for Owner
            appointmentMessage.setText("Appointment created successfully!");
        } catch (AppointmentAlreadyExistsException e) {
            appointmentMessage.setText(e.getMessage());
        }
    }
}
