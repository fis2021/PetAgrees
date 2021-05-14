package org.fis.student.sre.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.fis.student.sre.exceptions.NotExistingAppointmentException;
import org.fis.student.sre.model.Appointment;
import org.fis.student.sre.model.ClickListener;
import org.fis.student.sre.model.User;
import org.fis.student.sre.services.UserService;

//import java.awt.*;
import java.net.URL;
import java.util.*;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class SeeAppointmentsController {
    @FXML
    private static User currentUser;
    private ArrayList<Appointment> appointments;
    private ContextMenu menu;

    @FXML
    private Text seeAllAppointmentsMessage;

    @FXML
    private Button buttonSeeAllAppointments;

    @FXML
    private Button buttonSeePreviousAppointments;

    @FXML
    private Button buttonSeeUpcomingAppointments;

    @FXML
    private Button buttonLogOut;

    @FXML
    private Button buttonBack;

    @FXML
    private ListView<String> appointmentsList;



    public static User getCurrentUser() {
        return currentUser;
    }

    public void setUser(User user)
    {
        this.currentUser=user;
    }

    @FXML
    public void initialize() {
        menu = new ContextMenu();
        appointmentsList.setContextMenu(menu);
    }

    @FXML
    private void loadLoginAction(){
        try {
            Stage stage = (Stage) buttonLogOut.getScene().getWindow();
            Parent loginRoot = FXMLLoader.load(getClass().getResource("/FXML/login.fxml"));
            Scene scene = new Scene(loginRoot, 640, 480);
            stage.setTitle("PetAgrees -login");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAppointments(){
        appointments = UserService.getAppointmentsList(getCurrentUser().getUsername());

        if(appointments.isEmpty()){
            Label seeAllAppointmentsMessage = new Label();
            seeAllAppointmentsMessage.setText("Looks like there are no appointments :(");
            return;
        }
    }

    @FXML
    private void handleSeeAllAppointmentsAction(MouseEvent event) {
       ArrayList <Appointment> appointmentArrayList = getCurrentUser().getAppointments();
       for (Appointment appointment : appointmentArrayList) {
           appointmentsList.setAccessibleText(appointment.toStringSmallDetails());
       }

    }

    @FXML
    private void handleSeePreviousAppointmentsAction(MouseEvent event) {
        Calendar currentDate = new GregorianCalendar();
        ArrayList <Appointment> appointmentArrayList = getCurrentUser().getAppointments();
        for (Appointment appointment : appointmentArrayList) {
            if (appointment.getDataPrimaZi().compareTo(currentDate) < 0) {
                appointmentsList.setAccessibleText(appointment.toStringSmallDetails());
            }
        }

    }

    @FXML
    private void handleSeeUpcomingAppointmentsAction(MouseEvent event) {
        Calendar currentDate = new GregorianCalendar();
        ArrayList <Appointment> appointmentArrayList = getCurrentUser().getAppointments();
        for (Appointment appointment : appointmentArrayList) {
            if (appointment.getDataPrimaZi().compareTo(currentDate) > 0) {
                appointmentsList.setAccessibleText(appointment.toStringSmallDetails());
            }
        }

    }

    @FXML
    private void loadBackAction(){
        try {
            Stage stage = (Stage) buttonBack.getScene().getWindow();
            Parent loginRoot = FXMLLoader.load(getClass().getResource("/FXML/homeForPetSitter.fxml"));
            Scene scene = new Scene(loginRoot, 640, 480);
            stage.setTitle("PetAgrees for Pet Sitter");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
