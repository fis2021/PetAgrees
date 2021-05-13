package org.fis.student.sre.controllers;

import org.fis.student.sre.exceptions.NotExistingAccountException;
import org.fis.student.sre.exceptions.UserNotFoundException;
import org.fis.student.sre.exceptions.WrongPasswordException;
import org.fis.student.sre.model.User;
import org.fis.student.sre.services.UserService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginController {

    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private Text loginMessage;

    private static User currentUser;

    public void setRegistrationConfirmation(){
        loginMessage.setText("Login successfully!");
    }

    @FXML
    public void handleLoginAction(ActionEvent event) {    //login button
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username == null || username.isEmpty()) {
            loginMessage.setText("Please type in a username!");
            return;
        }

        if (password == null || password.isEmpty()) {
            loginMessage.setText("Password cannot be empty");
            return;
        }

        String encoded_password = UserService.encodePassword(username, password);

        try{
            String stored_password = UserService.getHashedUserPassword(username);
            if(stored_password.equals(encoded_password)){
                if (currentUser.getRole() == "PetSitter") {
                    loadHomePageForPetSitter();
                }
                if (currentUser.getRole() == "Owner"){
                    loadHomePageForOwner();
                }

                return;
            }

        } catch(UserNotFoundException e){
            loginMessage.setText(e.getMessage());
            return;
        }

        loginMessage.setText("Incorrect login!");
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    @FXML
    public void loadRegisterPage(){
        try {
            Stage stage = (Stage) loginMessage.getScene().getWindow();
            Parent registerRoot = FXMLLoader.load(getClass().getResource("/fxml/register.fxml"));
            Scene scene = new Scene(registerRoot, 640, 800);
            stage.setTitle("PetAgrees - Register");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadHomePageForPetSitter() {
        try{
            User u = UserService.getUser(usernameField.getText());
            Stage stage = (Stage) loginMessage.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"));
            Parent homeRoot = loader.load();
            HomeControllerForPetSitter controller = loader.getController();
            controller.setUser(u);
            stage.setUserData(u);
            controller.setAppointments();
            Scene scene = new Scene(homeRoot, 640, 800);
            stage.setTitle("PetAgrees for PetSitter - Home");
            stage.setScene(scene);
        } catch (IOException e){
            e.printStackTrace();
        }
    }



}
