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
            Parent root= FXMLLoader.load(getClass().getClassLoader().getResource("requestList.fxml"));
            Stage stage = (Stage) (buttonConfirmStatusRequest.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error!\n");
        }
    }

    @FXML
    private void loadLoginAction(){
        try {
            Parent root= FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
            Stage stage = (Stage) (buttonLogOut.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error!\n");
        }
    }

    @FXML
    private void loadBackAction(){
        try {
            Parent root= FXMLLoader.load(getClass().getClassLoader().getResource("homeForPetSitter.fxml"));
            Stage stage = (Stage) (buttonBack.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error!\n");
        }
    }



    public static User getCurrentUser() {
        return currentUser;
    }

}

