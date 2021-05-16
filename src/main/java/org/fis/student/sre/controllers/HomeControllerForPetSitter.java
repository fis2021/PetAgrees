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

public class HomeControllerForPetSitter {

   // private User currentUser;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button buttonAppointmentsListList;
    @FXML
    private Button buttonRequestListList;
    @FXML
    private Button buttonLogOut;


    /*public void setUser(User u){
        currentUser = u;
    }
    public User getUser(){ return currentUser;}
*/
    @FXML
    private void loadAppointmentsListPage(){
        try {
            Parent root= FXMLLoader.load(getClass().getClassLoader().getResource("seeAllAppointmentsList.fxml"));
            Stage stage = (Stage) (buttonAppointmentsListList.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error!\n");
        }

    }

    @FXML
    private void loadRequestListPage(){
        try {
            Parent root= FXMLLoader.load(getClass().getClassLoader().getResource("requestList.fxml"));
            Stage stage = (Stage) (buttonRequestListList.getScene().getWindow());
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
