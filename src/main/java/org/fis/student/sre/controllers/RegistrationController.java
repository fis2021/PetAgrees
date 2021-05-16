package org.fis.student.sre.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.fis.student.sre.exceptions.UsernameAlreadyExistsException;
import org.fis.student.sre.model.User;
import org.fis.student.sre.services.UserService;
import javafx.scene.layout.BorderPane;


import java.io.IOException;


public class RegistrationController {
    private static User currentUser;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Text registrationMessage;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private ChoiceBox role;

    @FXML
    private Button buttonLogIn;

    @FXML
    private Button buttonRegister;

    @FXML
    public void initialize() {
        role.getItems().addAll("Owner", "PetSitter");
    }


    public User getCurrentUser() {
        return currentUser;
    }
    public void setCurrentUser(User user) { this.currentUser = user;}



    @FXML
    public void handleRegisterAction(ActionEvent event) {
        try {
            UserService.addUser(usernameField.getText(), passwordField.getText(), (String) role.getValue());
            registrationMessage.setText("Account created successfully!");

            if ( ((String) role.getValue()).equals("PetSitter")) {
                try {
                    Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("homeForPetSitter.fxml"));
                    Stage stage = (Stage) (buttonRegister.getScene().getWindow());
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    System.out.println("Error!\n");
                }
            }
            if ( ((String) role.getValue()).equals("Owner")) {
                try {
                    Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("detailsAboutOwner.fxml"));
                    Stage stage = (Stage) (buttonRegister.getScene().getWindow());
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    System.out.println("Error!\n");
                }
            }

            return;

        } catch (UsernameAlreadyExistsException  e) {
            registrationMessage.setText(e.getMessage());
        }
    }

    @FXML
    private void loadLoginPage(){
        try {
            Parent root= FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
            Stage stage = (Stage) (buttonLogIn.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error!\n");
        }
    }


}
