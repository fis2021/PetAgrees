package org.fis.student.sre.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.fis.student.sre.exceptions.NotExistingAppointmentException;
import org.fis.student.sre.model.Appointment;
import org.fis.student.sre.model.ClickListener;
import org.fis.student.sre.model.User;
import org.fis.student.sre.services.UserService;

//import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SeeAllAppointmentsController implements Initializable {
    private static User currentUser;

    @FXML
    private GridPane watchGrid;

    @FXML
    private ListView<String> detailsAboutAppointment;

    private ClickListener clickListener;


    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clickListener = new ClickListener() {
            @Override
            public void onClickListener(Appointment appointment) {
                MoreDetailsAboutAppointmentController.setSelectedAppointment(MoreDetailsAboutAppointmentController.getSelectedAppointment());
            }
        };
        int column = 1, row = 1;
        watchGrid.setVgap(10);
        watchGrid.setHgap(10);
        ArrayList<Appointment> appointmentsList = UserService.getAppointmentsList(this.getCurrentUser().getUsername());
        assert appointmentsList != null;
        for (Appointment appointment : appointmentsList) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/appointmentLayoutLabel.fxml"));
            AnchorPane anchorPane = null;
            try {
                anchorPane = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (column == 6) {
                column = 1;
                row++;
            }
            AppointmentLayoutLabelController appointmentLayoutController = fxmlLoader.getController();
            appointmentLayoutController.setData(appointment, clickListener);

            watchGrid.add(anchorPane, column++, row);

            watchGrid.setMinHeight(Region.USE_COMPUTED_SIZE);
            watchGrid.setMinWidth(Region.USE_COMPUTED_SIZE);
            watchGrid.setPrefHeight(Region.USE_COMPUTED_SIZE);
            watchGrid.setPrefWidth(Region.USE_COMPUTED_SIZE);
            watchGrid.setMaxHeight(Region.USE_PREF_SIZE);
            watchGrid.setMaxWidth(Region.USE_PREF_SIZE);

        }

    }

    public static User getCurrentUser() {
        return currentUser;
    }
}
