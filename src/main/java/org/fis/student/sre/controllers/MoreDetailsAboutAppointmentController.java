package org.fis.student.sre.controllers;

import org.fis.student.sre.model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MoreDetailsAboutAppointmentController {
    private static Appointment selectedAppointment;
    @FXML
    private HBox gridAppointment;

    private ClickListener clickListener;

    public static Appointment getSelectedAppointment() {
        return selectedAppointment;
    }
    public static void setSelectedAppointment(Appointment selectedAppointment) {
        MoreDetailsAboutAppointmentController.selectedAppointment = selectedAppointment;
    }

    /*@Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clickListener = new ClickListener() {
            @Override
            public void onClickListener(Appointment appointment) {
                MoreDetailsAboutAppointmentController.setSelectedAppointment(MoreDetailsAboutAppointmentController.getSelectedAppointment());
            }
        };
        try {
            requests reqAppointment = new requests();
            String appointmentResponse = reqAppointment.getData("/discover/appointment?", "");
            Appointment appointments = reqAppointment.getBaseData(appointmentResponse);
            createGrid(appointments, gridAppointment);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void createGrid(ArrayList<Appointment> appointments, HBox gridAppointment) throws IOException {
        for (Appointment appointment : appointments) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/appointmentLayout.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();

            AppointmentLayoutController showLayoutController = fxmlLoader.getController();
            showLayoutController.setData(appointment, clickListener);
            gridAppointment.getChildren().add(anchorPane);
            gridAppointment.setSpacing(20);
        }
    }*/





}
