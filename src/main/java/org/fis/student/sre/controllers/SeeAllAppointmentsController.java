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
import java.util.Objects;

public class SeeAllAppointmentsController {
    private static ObservableList<Appointment> appointments;
    private static ObjectRepository<Appointment> REPOSITORY = UserService.getAppointmentRepository();
    private static User currentUser;
    private static String id, username;

    @FXML
    public TableView<Appointment> appointmentTable;
    @FXML
    public TableColumn<Appointment, String> appointmentNameColumn;

    @FXML
    private Label usernameOwnerLabel;
    @FXML
    private Label telephoneOwnerLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Label addressLabel;
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
        Cursor<Appointment> cursor = REPOSITORY.find(FindOptions.sort("usernameOwner", SortOrder.Ascending));
        for(Appointment appointment:cursor) {
            if(Objects.equals(getCurrentUser().getUsername(), appointment.getUsernamePetSitter())) {
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

            appointmentNameColumn.setCellValueFactory(new PropertyValueFactory<>("usernameOwner"));
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
                                if (offer.getUsernameOwner().toLowerCase().indexOf(lowerCaseFilter) != -1) {
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
                    if (event.getClickCount() == 2 && !row.isEmpty()) {
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
            usernameOwnerLabel.setText(appointment.getUsernameOwner());
            telephoneOwnerLabel.setText(String.valueOf(appointment.getTelephoneOwner()));
            descriptionLabel.setText(appointment.getDescription());
            statusLabel.setText(appointment.getStatus());
            addressLabel.setText(appointment.getAddress());
            dataPrimaZiLabel.setText(appointment.getDataPrimaZiAsString());
            numarDeZileLabel.setText(String.valueOf(appointment.getNumarDeZile()));
        } else {
            usernameOwnerLabel.setText("");
            telephoneOwnerLabel.setText("");
            descriptionLabel.setText("");
            statusLabel.setText("");
            addressLabel.setText("");
            dataPrimaZiLabel.setText("");
            numarDeZileLabel.setText("");
        }
    }

    @FXML
    private void loadLoginAction(){
        try {
            Parent root= FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
            Stage stage = (Stage) (buttonLogOut.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error!\n");
        }
    }

    @FXML
    private void loadBackAction(){
        try {
            Parent root= FXMLLoader.load(getClass().getClassLoader().getResource("homeForPetSitter.fxml"));
            Stage stage = (Stage) (buttonBack.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error!\n");
        }
    }

    public static ObservableList<Appointment> getAppointments() {
        return appointments;
    }

}
