package org.fis.student.sre.controllers;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.dizitart.no2.FindOptions;
import org.dizitart.no2.SortOrder;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.fis.student.sre.model.Appointment;
import org.fis.student.sre.model.User;
import org.fis.student.sre.services.UserService;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

public class SeePreviousAppointmentsController {

    private static ObservableList<Appointment> appointments;
    private static ObjectRepository<Appointment> REPOSITORY = UserService.getAppointmentRepository();
    private static User currentUser;
    private static String id, username;

    @FXML
    public TableView<Appointment> appointmentTable;
    @FXML
    public TableColumn<Appointment, String> appointmentNameColumn;

    @FXML
    private Label usernamePetSitterLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Label dataPrimaZiLabel;
    @FXML
    private Label numarDeZileLabel;


    @FXML
    private Button buttonLogOut;

    @FXML
    private Button buttonBack;

    @FXML
    private TextField searchTextField;

    public static void getAllAppointments(){
        ObservableList<Appointment> newList = FXCollections.observableArrayList();
        Cursor<Appointment> cursor = REPOSITORY.find(FindOptions.sort("usernamePetSitter", SortOrder.Ascending));
        for(Appointment appointment:cursor) {
            Calendar c = Calendar.getInstance();
            if(Objects.equals(getCurrentUser().getUsername(), appointment.getUsernameOwner()) && (appointment.getDataPrimaZi().compareTo(c) == -1)) {
                newList.add(appointment);
            }
        }
        appointments = newList;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public void setUser(User user)
    {
        this.currentUser=user;
    }

    @FXML
    public void initialize() {
        getAllAppointments();
        Platform.runLater(() -> {

            appointmentNameColumn.setCellValueFactory(new PropertyValueFactory<>("usernamePetSitter"));
            appointmentTable.setItems(appointments);

            showAppointmentDetails(null);

            appointmentTable.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> showAppointmentDetails(newValue));

            FilteredList<Appointment> filteredData = new FilteredList<>(appointments, p -> true);

            searchTextField.setOnKeyReleased(e -> {
                searchTextField.textProperty().addListener(
                        (observable, oldValue, newValue) -> {
                            filteredData.setPredicate(offer -> {
                                if (newValue == null || newValue.isEmpty()) {
                                    return true;
                                }

                                String lowerCaseFilter = newValue.toLowerCase();
                                if (offer.getUsernamePetSitter().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                                    return true;
                                }
                                return false;

                            });
                        });

                SortedList<Appointment> sortedList = new SortedList<>(filteredData);
                sortedList.comparatorProperty().bind(appointmentTable.comparatorProperty());
                appointmentTable.setItems(sortedList);
            });

            appointmentTable.setRowFactory(offer -> {
                TableRow<Appointment> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 1 && !row.isEmpty()) {
                        try {
                            Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
                            if (selectedAppointment != null) {
                                showAppointmentDetails(selectedAppointment);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                return row;
            });

        });
    }

    public void showAppointmentDetails(Appointment appointment) {
        if(appointment != null) {
            usernamePetSitterLabel.setText(appointment.getUsernamePetSitter());
            statusLabel.setText(appointment.getStatus());
            dataPrimaZiLabel.setText(appointment.getDataPrimaZiAsString());
            numarDeZileLabel.setText(String.valueOf(appointment.getNumarDeZile()));
        } else {
            usernamePetSitterLabel.setText("");
            statusLabel.setText("");
            dataPrimaZiLabel.setText("");
            numarDeZileLabel.setText("");
        }
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

    public static ObservableList<Appointment> getAppointments() {
        return appointments;
    }

}
