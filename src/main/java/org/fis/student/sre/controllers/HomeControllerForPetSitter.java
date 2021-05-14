package org.fis.student.sre.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.fis.student.sre.model.User;
import java.io.IOException;

public class HomeControllerForPetSitter {

    private User currentUser;

    @FXML
    private BorderPane borderPane;


    public void setUser(User u){
        currentUser = u;
    }
    public User getUser(){ return currentUser;}

    @FXML
    private void loadAppointmentsListPage(){
        try {
            Stage stage = (Stage) borderPane.getScene().getWindow();
            Parent loginRoot = FXMLLoader.load(getClass().getResource("/fxml/seeAllAppointmentsList.fxml"));
            Scene scene = new Scene(loginRoot, 640, 800);
            stage.setTitle("This is your appointments list :)");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadRequestListPage(){
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
    private void loadLoginPage(){
        try {
            Stage stage = (Stage) borderPane.getScene().getWindow();
            Parent loginRoot = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
            Scene scene = new Scene(loginRoot, 640, 800);
            stage.setTitle("PetAgrees - Login");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }







}
