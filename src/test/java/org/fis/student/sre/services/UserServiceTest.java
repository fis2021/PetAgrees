package org.fis.student.sre.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.fis.student.sre.exceptions.UsernameAlreadyExistsException;
import org.fis.student.sre.model.User;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testfx.assertions.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-PetAgrees";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        UserService.initDatabase();
    }

    @Test
    @DisplayName("Database is initialized, and there are no users")
    void testDatabaseIsInitializedAndNoUserIsPersisted() {
        assertThat(UserService.getAllUsers()).isNotNull();
        assertThat(UserService.getAllUsers()).isEmpty();
    }

    @Test
    @DisplayName("User is successfully persisted to Database")
    void testUserIsAddedToDatabase() throws UsernameAlreadyExistsException {
        UserService.addUser("ADMIN", "ADMIN", "ADMIN");
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
        User user = UserService.getAllUsers().get(0);
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("ADMIN");
        assertThat(user.getPassword()).isEqualTo(UserService.encodePassword("ADMIN", "ADMIN"));
        assertThat(user.getRole()).isEqualTo("ADMIN");
    }

    @Test
    @DisplayName("User can not be added twice")
    void testUserCanNotBeAddedTwice() {
        assertThrows(UsernameAlreadyExistsException.class, () -> {
            UserService.addUser("ADMIN", "ADMIN", "ADMIN");
            UserService.addUser("ADMIN", "ADMIN", "ADMIN");
        });
    }

}