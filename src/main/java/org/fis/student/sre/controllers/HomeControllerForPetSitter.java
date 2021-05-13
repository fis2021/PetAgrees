package org.fis.student.sre.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.fis.student.sre.exceptions.UserNotFoundException;
import org.fis.student.sre.model.Appointment;
import org.fis.student.sre.model.User;
import org.fis.student.sre.services.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class HomeController {
    private User user;
    @FXML
    private BorderPane borderPane;

    @FXML
    private ListView<String> appointmentList;

    @FXML
    private ChoiceBox<String> sortChoice;

    public void setAppointments(){
        ArrayList <Appointment> appointments = UserService.getAppointmentsList();

        if(appointments.isEmpty()){
            Label emptyMessage = new Label();
            emptyMessage.setText("Looks like there are no threads :c");
            borderPane.setCenter(emptyMessage);
            sortChoice.setDisable(true);
            return;
        }
    }

    public void setUser(User u){
        user = u;
    }

    private User getUser(){
        return user;
    }
}
