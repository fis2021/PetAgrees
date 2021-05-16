package org.fis.student.sre.controllers;

import com.sun.javafx.scene.control.IntegerField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.fis.student.sre.exceptions.UsernameAlreadyExistsException;
import org.fis.student.sre.model.User;
import org.fis.student.sre.services.UserService;

import java.io.IOException;

public class DetailsAboutOwnerController {
    private static User currentUser;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button buttonAllDone;
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
            UserService.addOwner(getCurrentUser().getUsername(), getCurrentUser().getPassword(),getCurrentUser().getImageOfCertification(), telephoneOwnerField.getValue(), addressOwnerField.getText(), descriptionOwnerField.getText());
            detailsMessage.setText("Account created successfully!");
            try {
                Parent root= FXMLLoader.load(getClass().getClassLoader().getResource("homeForOwner.fxml"));
                Stage stage = (Stage) (buttonAllDone.getScene().getWindow());
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                System.out.println("Error!\n");
            }

        } catch (UsernameAlreadyExistsException e) {
            detailsMessage.setText(e.getMessage());
        }
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public void setUser(User u) {
        this.currentUser = u;
    }
}
