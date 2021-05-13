package org.fis.student.sre.services;

import org.dizitart.no2.Cursor;
import org.dizitart.no2.FindOptions;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectFilter;
import org.dizitart.no2.objects.ObjectRepository;
import org.fis.student.sre.exceptions.AppointmentAlreadyExistsException;
import org.fis.student.sre.exceptions.UsernameAlreadyExistsException;
import org.fis.student.sre.model.Appointment;
import org.fis.student.sre.model.User;
import org.fis.student.sre.services.UserService;

import javax.swing.text.Element;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.logging.Filter;

import static org.fis.student.sre.services.FileSystemService.getPathToFile;

public class AppointmentService {
    private static ObjectRepository<Appointment> appointmentRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("PetAgrees.db").toFile())
                .openOrCreate("test", "test");


    }






}
