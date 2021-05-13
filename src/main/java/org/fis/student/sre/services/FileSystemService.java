package org.fis.student.sre.services;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSystemService {
    private static final String APPLICATION_FOLDER = ".registration-example";
    private static final String USER_FOLDER = System.getProperty("user.home");
    private static final String APPOINTMENT_FOLDER = System.getProperty("appointment.home");
    public static final Path APPLICATION_HOME_PATH = Paths.get(USER_FOLDER, APPLICATION_FOLDER);
    public static final Path APPOINTMENT_HOME_PATH = Paths.get(APPOINTMENT_FOLDER, APPLICATION_FOLDER);

    public static Path getPathToFile(String... path) {
        return APPLICATION_HOME_PATH.resolve(Paths.get(".", path));
    }
    public static Path getPathToFileAppointment(String... path) {
        return APPOINTMENT_HOME_PATH.resolve(Paths.get(".", path));
    }

}
