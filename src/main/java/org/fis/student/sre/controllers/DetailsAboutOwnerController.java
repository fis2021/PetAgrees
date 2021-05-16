package org.fis.student.sre.controllers;

import com.sun.javafx.scene.control.IntegerField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.fis.student.sre.exceptions.UsernameAlreadyExistsException;
import org.fis.student.sre.model.IntegerTextField;
import org.fis.student.sre.model.User;
import org.fis.student.sre.services.UserService;

import java.io.IOException;

public class DetailsAboutOwnerController {
    private static User currentUser;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Text detailsMessage;
    @FXML
    private IntegerField telephoneOwnerField;
    @FXML
    private TextField addressOwnerField;
    @FXML
    private TextField descriptionOwnerField;


    @FXML
    public void handleDetailsAboutOwnerAction(ActionEvent event) {
        try {
            UserService.addOwner(getCurrentUser().getUsername(), getCurrentUser().getPassword(), telephoneOwnerField.getValue(), addressOwnerField.getText(), descriptionOwnerField.getText());
            detailsMessage.setText("Account created successfully!");
            loadHomePageForOwner();
            return;

        } catch (UsernameAlreadyExistsException e) {
            detailsMessage.setText(e.getMessage());
        }
    }

    @FXML
    private void loadHomePageForOwner() {
        try{
            User u = UserService.getUser(getCurrentUser().getUsername());
            Stage stage = (Stage) detailsMessage.getScene().getWindow();
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
    }

    public static User getCurrentUser() {
        return currentUser;
    }

}
