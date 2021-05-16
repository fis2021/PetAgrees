package org.fis.student.sre.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.fis.student.sre.model.User;
import java.io.IOException;

public class HomeControllerForOwner {

    private User currentUser;

    @FXML
    private BorderPane borderPane;
    @FXML
    private Button buttonAvailablePetSitterList;
    @FXML
    private Button buttonPreviousAppointmentsList;
    @FXML
    private Button buttonLogOut;


    public void setUser(User u){
        currentUser = u;
    }
    public User getUser(){ return currentUser;}

    @FXML
    private void loadAvailablePetSitterListPage(){
        try {
            Parent root= FXMLLoader.load(getClass().getClassLoader().getResource("seeAvailablePetSitterList.fxml"));
            Stage stage = (Stage) (buttonAvailablePetSitterList.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error!\n");
        }
    }

    @FXML
    private void  loadPreviousAppointmentsListPage(){
        try {
            Parent root= FXMLLoader.load(getClass().getClassLoader().getResource("seePreviousAppointmentsList.fxml"));
            Stage stage = (Stage) (buttonPreviousAppointmentsList.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error!\n");
        }
    }


    @FXML
    private void loadLoginPage(){
        try {
            Parent root= FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
            Stage stage = (Stage) (buttonLogOut.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error!\n");
        }
    }







}
