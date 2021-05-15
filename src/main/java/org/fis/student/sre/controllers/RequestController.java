package org.fis.student.sre.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import java.util.Iterator;

public class RequestController {

    private static User currentUser;

    @FXML
    private Button buttonConfirmStatusRequest;

    @FXML
    private Button buttonLogOut;

    @FXML
    private Button buttonBack;

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
                Iterator<Appointment> request = requestList.iterator();
                detailsAboutRequest.setText(request.toString());
                if ((String) status.getValue() == "ACCEPT") {
                    Object app = status.getUserData();
                    UserService.acceptAppointment((Appointment) request);
                    requestMessage.setText("Request accepted successfully!");
                }
                if ((String) status.getValue() == "DENY") {
                    Object app = status.getUserData();
                    UserService.denyAppointment((Appointment) request);
                    requestMessage.setText("Request denied successfully!");
                }
                if (request.hasNext()) {
                    loadRequestAction();
                }
            } catch ( NotExistingAppointmentException e) {
                requestMessage.setText(e.getMessage());
            }
        }
    }

    @FXML
    private void loadRequestAction(){
        try {
            Stage stage = (Stage) borderPane.getScene().getWindow();
            Parent loginRoot = FXMLLoader.load(getClass().getResource("/fxml/requestList.fxml"));
            Scene scene = new Scene(loginRoot, 640, 800);
            stage.setTitle("This is your requests list :)");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadLoginAction(){
        try {
            Stage stage = (Stage) buttonLogOut.getScene().getWindow();
            Parent loginRoot = FXMLLoader.load(getClass().getResource("/FXML/login.fxml"));
            Scene scene = new Scene(loginRoot, 640, 480);
            stage.setTitle("PetAgrees -login");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadBackAction(){
        try {
            Stage stage = (Stage) buttonBack.getScene().getWindow();
            Parent loginRoot = FXMLLoader.load(getClass().getResource("/FXML/homeForPetSitter.fxml"));
            Scene scene = new Scene(loginRoot, 640, 480);
            stage.setTitle("PetAgrees for Pet Sitter");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static User getCurrentUser() {
        return currentUser;
    }

}

