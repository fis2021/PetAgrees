package org.fis.student.sre.controllers;

import org.apache.commons.io.FileUtils;
import org.fis.student.sre.exceptions.AppointmentAlreadyExistsException;
import org.fis.student.sre.exceptions.NotExistingAppointmentException;
import org.fis.student.sre.exceptions.UsernameAlreadyExistsException;
import org.fis.student.sre.model.Appointment;
import org.fis.student.sre.services.FileSystemService;
import org.fis.student.sre.services.UserService;
import org.junit.jupiter.api.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class RequestControllerJUnitTest2 {
    @BeforeAll
    static void beforeAll() {
        System.out.println("Before All");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After All");
    }


    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-PetAgrees";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        UserService.initDatabase();
    }

    @Test
    @DisplayName("Appointments are added in database and saved in list")
    void testListWithAppointmentsIsCreated() throws UsernameAlreadyExistsException, AppointmentAlreadyExistsException {
        UserService.addUser("ADMIN", "ADMIN", "PetSitter");

        Calendar dataPrimaZi1 = new GregorianCalendar(2021, 1, 1, 1, 1);
        Calendar dataPrimaZi2 = new GregorianCalendar(2021, 2, 2, 2, 2);
        Calendar dataPrimaZi3 = new GregorianCalendar(2021, 3, 3, 3, 3);

        Appointment appointment1 = new Appointment("ADMIN", "1", 11111, "description1", "address1", dataPrimaZi1, 1);
        Appointment appointment2 = new Appointment("ADMIN", "2", 22222, "description2",  "address2", dataPrimaZi2, 2);
        Appointment appointment3 = new Appointment("ADMIN", "3", 33333, "description3",  "address3", dataPrimaZi3, 3);

        UserService.addAppointmentInAppointmentsList("ADMIN", appointment1);
        UserService.addAppointmentInAppointmentsList("ADMIN", appointment2);
        UserService.addAppointmentInAppointmentsList("ADMIN", appointment3);
        SeeAllAppointmentsController.getAppointments();
        assertThat(SeeAllAppointmentsController.getAppointments()).isNotNull();
        assertThat(SeeAllAppointmentsController.getAppointments()).size().isEqualTo(2);
    }

    @Test
    void testIfARequestIsDeny() throws UsernameAlreadyExistsException, NotExistingAppointmentException {
        UserService.addUser("ADMIN", "ADMIN", "PetSitter");
        Calendar dataPrimaZi1 = new GregorianCalendar(2021, 1, 1, 1, 1);
        Appointment appointment1 = new Appointment("ADMIN", "1", 11111, "description1", "address1", dataPrimaZi1, 1);

        UserService.denyAppointment(appointment1);
        assertThat(appointment1.getStatus()).isEqualTo("DENY");

    }

    @Test
    void testIfARequestIsAccept() throws UsernameAlreadyExistsException, NotExistingAppointmentException {
        UserService.addUser("ADMIN", "ADMIN", "PetSitter");
        Calendar dataPrimaZi1 = new GregorianCalendar(2021, 1, 1, 1, 1);
        Appointment appointment1 = new Appointment("ADMIN", "1", 11111, "description1", "address1", dataPrimaZi1, 1);

        UserService.acceptAppointment(appointment1);
        assertThat(appointment1.getStatus()).isEqualTo("ACCEPT");

    }

}