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
public class SeePreviousAppointmentsControllerTest {

    @BeforeEach
    void setUp() throws Exception, AppointmentAlreadyExistsException {
        FileSystemService.APPLICATION_FOLDER = ".test-PetAgrees";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        UserService.initDatabase();
        Calendar dataPrimaZi1 = new GregorianCalendar(2021, 1, 1, 1, 1);
        Appointment appointment1 = new Appointment("1", "ADMIN", 11111, "description1", "address1", dataPrimaZi1, 1);
        UserService.addAppointmentInAppointmentsList("ADMIN", appointment1);
    }

    @Start
    void start(Stage stage) throws IOException {
        UserService.initDatabase();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("seePreviousAppointmentsList.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void SeePreviousAppointmentsTest(FxRobot robot) {

        assertThat(UserService.getAllAppointments().size()).isEqualTo(2);


        robot.clickOn("#appointmentTable");
        robot.type(KeyCode.DOWN);
        robot.moveTo("ADMIN").doubleClickOn();

        robot.moveTo("#usernamePetSitter");
        Assertions.assertThat(robot.lookup("#usernamePetSitter").queryText()).hasText("1");

        robot.moveTo("#numarDeZileLabel");
        Assertions.assertThat(robot.lookup("#numarDeZileLabel").queryText()).hasText("1");


        robot.clickOn("#buttonLogOut");
    }


}
