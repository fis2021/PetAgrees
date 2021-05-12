package org.fis.student.sre.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.fis.student.sre.exceptions.AppointmentAlreadyExistsException;
import org.fis.student.sre.exceptions.UsernameAlreadyExistsException;
import org.fis.student.sre.services.AppointmentService;

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
   /* @FXML
    private ChoiceBox status;

    @FXML
    public void initialize() {
        status.getItems().addAll("ACCEPT", "DENY");
    }

    @FXML
    private BufferedImage imageOfCertification;*/

    @FXML
    public void handleRegisterAction() {
        try {
            AppointmentService.addAppointment(usernamePetSitterField.getText(), usernameOwnerField.getText(), telephoneOwnerField.getMaximumIntegerDigits(), descriptionOwnerField.getText(), "processing", addressOwnerField.getText());
            appointmentMessage.setText("Appointment created successfully!");
        } catch (AppointmentAlreadyExistsException e | UsernameAlreadyExistsException e) {
            appointmentMessage.setText(e.getMessage());
        }
    }
}
