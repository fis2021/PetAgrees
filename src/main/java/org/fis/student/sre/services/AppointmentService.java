/*package org.fis.student.sre.services;

import org.dizitart.no2.Cursor;
import org.dizitart.no2.FindOptions;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectFilter;
import org.dizitart.no2.objects.ObjectRepository;
import org.fis.student.sre.exceptions.AppointmentAlreadyExistsException;
import org.fis.student.sre.exceptions.UsernameAlreadyExistsException;
import org.fis.student.sre.model.Appointment;

import javax.swing.text.Element;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.logging.Filter;

import static org.fis.student.sre.services.FileSystemService.getPathToFileAppointment;



public class AppointmentService {
    private static ObjectRepository<Appointment> appointmentRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFileAppointment("appointment.db").toFile())
                .openOrCreate("test", "test");

        appointmentRepository = database.getRepository(Appointment.class);
    }

    public static void addAppointment(String usernamePetSitter, String usernameOwner, int telephoneOwner, String description, String status, String address) throws AppointmentAlreadyExistsException, UsernameAlreadyExistsException {
        checkAppointmentDoesNotAlreadyExist(usernamePetSitter, description);
        appointmentRepository.insert(new Appointment(usernamePetSitter, usernameOwner, telephoneOwner, address, description));
    }

    private static void checkAppointmentDoesNotAlreadyExist(String usernamePetSitter, String description) throws AppointmentAlreadyExistsException {
        for (Appointment appointment : appointmentRepository.find()) {
            if (Objects.equals(usernamePetSitter, appointment.getUsernamePetSitter()))
                throw new AppointmentAlreadyExistsException(usernamePetSitter, description);
        }
    }

    public static void acceptAppointment(String usernamePetSitter, String usernameOwner, int telephoneOwner, String description, String status, String address) throws AppointmentAlreadyExistsException, UsernameAlreadyExistsException {
        for (Appointment appointment : appointmentRepository.find()) {
            if (Objects.equals(usernamePetSitter, appointment.getUsernamePetSitter()))
                appointmentRepository.insert(new Appointment(appointment.getUsernamePetSitter(), appointment.getUsernameOwner(), appointment.getTelephoneOwner(), appointment.getAddress(), appointment.getDescription(), "ACCEPTED"));
            appointmentRepository.remove(appointment);
        }

    }

    public static void denyAppointment(String usernamePetSitter, String usernameOwner, int telephoneOwner, String description, String status, String address) throws AppointmentAlreadyExistsException, UsernameAlreadyExistsException {
        for (Appointment appointment : appointmentRepository.find()) {
            if (Objects.equals(usernamePetSitter, appointment.getUsernamePetSitter()))
                appointmentRepository.insert(new Appointment(appointment.getUsernamePetSitter(), appointment.getUsernameOwner(), appointment.getTelephoneOwner(), appointment.getAddress(), appointment.getDescription(), "DENIED"));
            appointmentRepository.remove(appointment);
        }

    }

/*
    private static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        // This is the way a password should be encoded when checking the credentials
        return new String(hashedPassword, StandardCharsets.UTF_8)
                .replace("\"", ""); //to be able to save in JSON format
    }

    private static String encodeImage(String salt, String imageAsString) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(imageAsString.getBytes(StandardCharsets.UTF_8));

        // This is the way a string should be encoded when checking the credentials
        return new String(hashedPassword, StandardCharsets.UTF_8)
                .replace("\"", ""); //to be able to save in JSON format
    }

    private static MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }

} 
*/
