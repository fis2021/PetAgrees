package org.fis.student.sre.controllers;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.fis.student.sre.exceptions.AppointmentAlreadyExistsException;
import org.fis.student.sre.model.Appointment;
import org.fis.student.sre.services.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.fis.student.sre.services.FileSystemService;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.junit.jupiter.api.Test;

import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
public class SeeAvailablePetSitterControllerTest {

    @BeforeEach
    void setUp() throws Exception, AppointmentAlreadyExistsException {
        FileSystemService.APPLICATION_FOLDER = ".test-PetAgrees";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        UserService.initDatabase();
        UserService.addUser("ADMIN", "ADMIN", "Owner");
    }

    @Start
    void start(Stage stage) throws IOException {
        UserService.initDatabase();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("seeAllAppointmentsList.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void seeAllAppointmentsTest(FxRobot robot) {

        assertThat(UserService.getAllAppointments().size()).isEqualTo(2);


        robot.clickOn("#appointmentTable");
        robot.type(KeyCode.DOWN);
        robot.moveTo("ADMIN").doubleClickOn();

        robot.clickOn("#anPrimaZiField");
        robot.write("2021");

        robot.clickOn("#lunaPrimaZiField");
        robot.write("1");

        robot.clickOn("#ziPrimaZiField");
        robot.write("1");

        robot.clickOn("#oraPrimaZiField");
        robot.write("1");

        robot.clickOn("#minutPrimaZiField");
        robot.write("1");

        robot.clickOn("#numarDeZileField");
        robot.write("1");

        robot.clickOn("#buttonAdd");
    }


}
