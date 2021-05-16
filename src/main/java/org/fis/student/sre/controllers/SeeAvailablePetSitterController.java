package org.fis.student.sre.controllers;


import com.sun.javafx.scene.control.IntegerField;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.dizitart.no2.FindOptions;
import org.dizitart.no2.SortOrder;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.fis.student.sre.exceptions.AppointmentAlreadyExistsException;
import org.fis.student.sre.model.Appointment;
import org.fis.student.sre.model.User;
import org.fis.student.sre.services.UserService;

import java.io.IOException;
import java.util.Objects;

public class SeeAvailablePetSitterController {

    private static ObservableList<User> petSitters;
    private static ObjectRepository<User> REPOSITORY = UserService.getUserRepository();
    private static Owner currentOwner;
    private static String id, username;
    private static Appointment request;

    @FXML
    private Text appointmentMessage;

    @FXML
    public TableView<User> petSitterTable;
    @FXML
    public TableColumn<User, String> petSitterNameColumn;

    @FXML
    private IntegerField anPrimaZiField;
    @FXML
    private IntegerField lunaPrimaZiField;
    @FXML
    private IntegerField ziPrimaZiField;
    @FXML
    private IntegerField oraPrimaZiField;
    @FXML
    private IntegerField minutPrimaZiField;
    @FXML
    private IntegerField numarDeZileField;

    @FXML
    private Button buttonAdd;

    @FXML
    private Button buttonLogOut;

    @FXML
    private Button buttonBack;

    @FXML
    private TextField searchTextField;

    public static void getAllPetSitters(){
        ObservableList<User> newList = FXCollections.observableArrayList();
        Cursor<User> cursor = REPOSITORY.find(FindOptions.sort("usernamePetSitter", SortOrder.Ascending));
        for(User petSitter:cursor) {
            if(Objects.equals(petSitter.getRole(), "PetSitter")) {
                newList.add(petSitter);
            }
        }
        petSitters = newList;
    }

    public static Owner getCurrentOwner() {
        return currentOwner;
    }

    public void setOwner(Owner owner)
    {
        this.currentOwner = owner;
    }

    @FXML
    public void initialize() {
        getAllPetSitters();
        Platform.runLater(() -> {

            petSitterNameColumn.setCellValueFactory(new PropertyValueFactory<>("usernamePetSitter"));
            petSitterTable.setItems(petSitters);

            showRequest(null);

            petSitterTable.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> showRequest(newValue));

            FilteredList<User> filteredData = new FilteredList<>(petSitters, p -> true);

            searchTextField.setOnKeyReleased(e -> {
                searchTextField.textProperty().addListener(
                        (observable, oldValue, newValue) -> {
                            filteredData.setPredicate(offer -> {
                                if (newValue == null || newValue.isEmpty()) {
                                    return true;
                                }

                                String lowerCaseFilter = newValue.toLowerCase();
                                if (offer.getUsername().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                                    return true;
                                }
                                return false;

                            });
                        });

                SortedList<User> sortedList = new SortedList<>(filteredData);
                sortedList.comparatorProperty().bind(petSitterTable.comparatorProperty());
                petSitterTable.setItems(sortedList);
            });

            petSitterTable.setRowFactory(offer -> {
                TableRow<User> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 1 && !row.isEmpty()) {
                        try {
                            User selectedPetSitter = petSitterTable.getSelectionModel().getSelectedItem();
                            if (selectedPetSitter != null) {
                                showRequest(selectedPetSitter);
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

    public void showRequest(User PetSitter) {
        try {
            Appointment appointment = new Appointment(PetSitter.getUsername(), getCurrentOwner().getUsername(), getCurrentOwner().getTelephoneOwner(), getCurrentOwner().getDescriptionOwner(), "processing", getCurrentOwner().getAddressOwner(), anPrimaZiField.getValue(), lunaPrimaZiField.getValue(), ziPrimaZiField.getValue(), oraPrimaZiField.getValue(), minutPrimaZiField.getValue(), numarDeZileField.getValue());
            setRequest(appointment);
            UserService.addAppointmentInAppointmentsList(getCurrentOwner().getUsername(), appointment);// add appointment in appointment list for Owner
            appointmentMessage.setText("Request created successfully!");
        } catch (AppointmentAlreadyExistsException e) {
            appointmentMessage.setText(e.getMessage());
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

    public void handleAddRequest() {
        try {
            Appointment appointment = getRequest();
            UserService.addAppointmentInAppointmentsList(getCurrentOwner().getUsername(), appointment);// add appointment in appointment list for Owner
            appointmentMessage.setText("Request created successfully!");
        } catch (AppointmentAlreadyExistsException e) {
            appointmentMessage.setText(e.getMessage());
        }

    }

    private void setRequest(Appointment request) { this.request = request; }
    private Appointment getRequest() { return this.request; }

}
