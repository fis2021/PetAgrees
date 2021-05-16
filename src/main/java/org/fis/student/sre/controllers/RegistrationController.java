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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.fis.student.sre.exceptions.UsernameAlreadyExistsException;
import org.fis.student.sre.model.User;
import org.fis.student.sre.services.UserService;
import javafx.scene.layout.BorderPane;
import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
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

    @FXML
    public ImageView imageOfCertification;
    private String imagePath;

    public User getCurrentUser() {
        return currentUser;
    }
    public void setCurrentUser(User user) { this.currentUser = user;}


    private void setImageView() {
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(new File(imagePath));
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            imageOfCertification.setImage(image);
        }
        catch (Exception e){
            try {
                bufferedImage = ImageIO.read(new File("D:/Facultate/ANUL II/SEM II/FIS - Fundamente de Inginerie Software/PetAgrees/404.jpg"));
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                imageOfCertification.setImage(image);
            }
            catch (IOException ex){}
        }
    }

    @FXML
    public void handleRegisterAction(ActionEvent event) {
        try {
            setImageView();
            User user = new User(usernameField.getText(), passwordField.getText(), (String) role.getValue(), imageOfCertification);
            setCurrentUser(user);
            UserService.addUser(user);
            registrationMessage.setText("Account created successfully!");

            if (getCurrentUser().getRole().equals("PetSitter")) {
                try {
                    Parent root= FXMLLoader.load(getClass().getClassLoader().getResource("homeForPetSitter.fxml"));
                    Stage stage = (Stage) (buttonRegister.getScene().getWindow());
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    System.out.println("Error!\n");
                }
            }
            if (getCurrentUser().getRole().equals("Owner")){
                try {
                    Parent root= FXMLLoader.load(getClass().getClassLoader().getResource("detailsAboutOwner.fxml"));
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


    @FXML
    private void loadHomePageForPetSitter() {
        try {
            Parent root= FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
            Stage stage = (Stage) (buttonLogIn.getScene().getWindow());
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            System.out.println("Error!\n");
        }
       /* try{
            User u = UserService.getUser(usernameField.getText());
            Stage stage = (Stage) buttonRegister.getScene().getWindow();
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
        }*/
    }

    @FXML
    private void loadDetailsAboutOwner() {
        try{
            User u = UserService.getUser(usernameField.getText());
            Stage stage = (Stage) buttonRegister.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/detailsAboutOwner.fxml"));
            Parent homeRoot = loader.load();
            DetailsAboutOwnerController controller = loader.getController();
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
