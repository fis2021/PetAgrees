package org.fis.student.sre.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.fis.student.sre.exceptions.UserNotFoundException;
import org.fis.student.sre.exceptions.UsernameAlreadyExistsException;
import org.fis.student.sre.model.User;
import org.fis.student.sre.services.UserService;
import javafx.scene.layout.BorderPane;

import java.awt.image.BufferedImage;
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
    public void initialize() {
        role.getItems().addAll("Owner", "PetSitter");
    }

    @FXML
    public BufferedImage imageOfCertification;

    public static User getCurrentUser() {
        return currentUser;
    }

    @FXML
    public void handleRegisterAction(ActionEvent event) {
        try {
            UserService.addUser(usernameField.getText(), passwordField.getText(), (String) role.getValue(), imageOfCertification);
            registrationMessage.setText("Account created successfully!");
            if (currentUser.getRole() == "PetSitter") {
                loadHomePageForPetSitter();
            }
            if (currentUser.getRole() == "Owner"){
                loadDetailsAboutOwner();
            }
            return;

        } catch (UsernameAlreadyExistsException  e) {
            registrationMessage.setText(e.getMessage());
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

    @FXML
    private void loadHomePageForPetSitter() {
        try{
            User u = UserService.getUser(usernameField.getText());
            Stage stage = (Stage) registrationMessage.getScene().getWindow();
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
    private void loadDetailsAboutOwner() {
        try{
            User u = UserService.getUser(usernameField.getText());
            Stage stage = (Stage) registrationMessage.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/detailsAboutOwner.fxml"));
            Parent homeRoot = loader.load();
            HomeControllerForPetSitter controller = loader.getController();
            controller.setUser(u);
            stage.setUserData(u);
            Scene scene = new Scene(homeRoot, 640, 800);
            stage.setTitle("More details about you");
            stage.setScene(scene);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
