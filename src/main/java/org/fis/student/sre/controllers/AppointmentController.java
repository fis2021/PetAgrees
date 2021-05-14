package org.fis.student.sre.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.fis.student.sre.exceptions.AppointmentAlreadyExistsException;
import org.fis.student.sre.model.Appointment;
import org.fis.student.sre.model.IntegerTextField;
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
    private IntegerTextField telephoneOwnerField;
    @FXML
    private TextField descriptionOwnerField;
    @FXML
    private TextField addressOwnerField;
    @FXML
    private IntegerTextField anPrimaZiField;
    @FXML
    private IntegerTextField lunaPrimaZiField;
    @FXML
    private IntegerTextField ziPrimaZiField;
    @FXML
    private IntegerTextField oraPrimaZiField;
    @FXML
    private IntegerTextField minutPrimaZiField;
    @FXML
    private IntegerTextField numarDeZileField;

    @FXML
    public void handleAppointmentAction() {
        try {
            Appointment appointment = new Appointment(usernamePetSitterField.getText(), usernameOwnerField.getText(), telephoneOwnerField.getInt(), descriptionOwnerField.getText(), "processing", addressOwnerField.getText(), anPrimaZiField.getInt(), lunaPrimaZiField.getInt(), ziPrimaZiField.getInt(), oraPrimaZiField.getInt(), minutPrimaZiField.getInt(), numarDeZileField.getInt());
            UserService.addAppointmentInAppointmentsList(usernameOwnerField.getText(), appointment);// add appointment in appointment list for Owner
            appointmentMessage.setText("Appointment created successfully!");
        } catch (AppointmentAlreadyExistsException e) {
            appointmentMessage.setText(e.getMessage());
        }
    }
}
