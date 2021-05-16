package org.fis.student.sre.controllers;

import javafx.scene.control.Button;
import org.fis.student.sre.exceptions.UserNotFoundException;
import org.fis.student.sre.model.User;
import org.fis.student.sre.services.UserService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

    @FXML
    private Button buttonLogIn;

    @FXML
    private Button buttonRegister;

    //private static User currentUser;

    public void setRegistrationConfirmation(){
        loginMessage.setText("Login successfully!");
    }

    @FXML
    public void handleLoginAction(ActionEvent event) {    //login button
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username == null || username.isEmpty()) {
            loginMessage.setText("Please type in a username!");
        }

        if (password == null || password.isEmpty()) {
            loginMessage.setText("Password cannot be empty");
        }

        String encoded_password = UserService.encodePassword(username, password);

        try{
            String stored_password = UserService.getHashedUserPassword(username);
            if(stored_password.equals(encoded_password)){
                User currentUser = UserService.getUser(usernameField.getText());
                if (currentUser.getRole().equals("PetSitter")) {
                    try {
                        Parent root= FXMLLoader.load(getClass().getClassLoader().getResource("homeForPetSitter.fxml"));
                        Stage stage = (Stage) (buttonLogIn.getScene().getWindow());
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (IOException e) {
                        System.out.println("Error!\n");
                    }
                }
                if (currentUser.getRole().equals("Owner")){
                    try {
                        Parent root= FXMLLoader.load(getClass().getClassLoader().getResource("homeForOwner.fxml"));
                        Stage stage = (Stage) (buttonLogIn.getScene().getWindow());
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (IOException e) {
                        System.out.println("Error!\n");
                    }
                }

                return;
            }

        } catch(UserNotFoundException e){
            loginMessage.setText(e.getMessage());
            return;
        }

        loginMessage.setText("Incorrect login!");
    }

    /*public static User getCurrentUser() {
        return currentUser;
    }*/

    @FXML
    public void loadRegisterPage(){
        try {
            Parent root= FXMLLoader.load(getClass().getClassLoader().getResource("register.fxml"));
            Stage stage = (Stage) (buttonRegister.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error!\n");
        }
    }

    /*@FXML
    private void loadHomePageForPetSitter() {

        try{
            User u = UserService.getUser(usernameField.getText());
            Stage stage = (Stage) buttonLogIn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/homeForPetSitter.fxml"));
            Parent homeRoot = loader.load();
            HomeControllerForPetSitter controller = loader.getController();
            controller.setUser(u);
            stage.setUserData(u);
            Scene scene = new Scene(homeRoot, 640, 800);
            stage.setTitle("PetAgrees for PetSitter - Home");
            stage.setScene(scene);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void loadHomePageForOwner() {
        try{
            User u = UserService.getUser(usernameField.getText());
            Stage stage = (Stage) buttonLogIn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/homeForOwner.fxml"));
            Parent homeRoot = loader.load();
            HomeControllerForPetSitter controller = loader.getController();
            controller.setUser(u);
            stage.setUserData(u);
            Scene scene = new Scene(homeRoot, 640, 800);
            stage.setTitle("PetAgrees for Owner - Home");
            stage.setScene(scene);
        } catch (IOException e){
            e.printStackTrace();
        }
    }*/




}
