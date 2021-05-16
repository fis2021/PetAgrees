package org.fis.student.sre.controllers;

import org.apache.commons.io.FileUtils;
import org.fis.student.sre.exceptions.AppointmentAlreadyExistsException;
import org.fis.student.sre.exceptions.UsernameAlreadyExistsException;
import org.fis.student.sre.model.Appointment;
import org.fis.student.sre.services.FileSystemService;
import org.fis.student.sre.services.UserService;
import org.junit.jupiter.api.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

class SeeAvailablePetSitterControllerJUnitTest {
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
    @DisplayName("PetSitters are added in database and saved in list")
    void testListWithOffersOfAnAgencyIsCreated() throws UsernameAlreadyExistsException, AppointmentAlreadyExistsException {
        UserService.addUser("ADMIN", "ADMIN", "ADMIN");
        UserService.addUser("PetSitter1", "parola1", "PetSitter");
        UserService.addUser("PetSitter2", "parola2", "PetSitter");
        UserService.addUser("PetSitter3", "parola3", "PetSitter");

        SeeAvailablePetSitterController.getPetSitters();
        assertThat(SeeAvailablePetSitterController.getPetSitters()).isNotNull();
        assertThat(SeeAvailablePetSitterController.getPetSitters()).size().isEqualTo(2);
    }

}