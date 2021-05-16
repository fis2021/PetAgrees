package org.fis.student.sre.services;


import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.fis.student.sre.exceptions.*;
import org.fis.student.sre.model.Appointment;
import org.fis.student.sre.model.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import static org.fis.student.sre.services.FileSystemService.getPathToFile;

public class UserService {

    private static ObjectRepository<User> userRepository;
    private static ObjectRepository<Appointment> appointmentRepository;

    public static void initDatabase() {
        FileSystemService.initDirectory();
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("PetAgrees.db").toFile())
                .openOrCreate("test", "test");
        userRepository = database.getRepository(User.class);

        FileSystemService.initDirectory();
        Nitrite database2 = Nitrite.builder()
                .filePath(getPathToFile("Appointment.db").toFile())
                .openOrCreate("test", "test");

        appointmentRepository = database.getRepository(Appointment.class);
    }

    public static void addUser(String username, String password, String role) throws UsernameAlreadyExistsException {
        checkUserDoesNotAlreadyExist(username);
        userRepository.insert(new User(username, encodePassword(username, password), role));
    }

    public static void addOwner(String username, String password,  int telephoneOwner, String descriptionOwner, String addressOwner) throws UsernameAlreadyExistsException {
        checkUserDoesNotAlreadyExist(username);
        userRepository.insert(new Owner(username, encodePassword(username, password), telephoneOwner, descriptionOwner, addressOwner));
    }

    public static User getUser(String username){
        for (User user : userRepository.find()) {
            if (Objects.equals(username, user.getUsername()))
                return user;

        }
        return null;
    }

    public static List<User> getAllUsers() {
        return userRepository.find().toList();
    }

    private static void checkUserDoesNotAlreadyExist(String username) throws UsernameAlreadyExistsException {
        for (User user : userRepository.find()) {
            if (Objects.equals(username, user.getUsername()))
                throw new UsernameAlreadyExistsException(username);
        }
    }

    public static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        // This is the way a password should be encoded when checking the credentials
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

    public static ArrayList <Appointment> getAppointmentsList(String username) {
        ArrayList <Appointment> s = new ArrayList<Appointment> (50);
        for(User user : userRepository.find()){
            if(Objects.equals(username, user.getUsername())){
                if (user.getMax() > 50) {
                    ArrayList <Appointment> aux = new ArrayList<Appointment> (user.getMax());
                    s = aux;
                }
                for (Appointment ap: user.getAppointments()) {
                    if (!Objects.equals(ap.getStatus(), "processing")) {
                        s.add(ap);
                    }
                }
            }
        }
        return s;
    }

    public static void addAppointmentInAppointmentsList(String username, Appointment appointment) throws AppointmentAlreadyExistsException {
        checkAppointmentDoesNotAlreadyExist(appointment.getUsernamePetSitter(), appointment.getDescription());
        for(User user : userRepository.find()){
            if(Objects.equals(username, user.getUsername())){
                appointmentRepository.insert(new Appointment(appointment.getUsernamePetSitter(), appointment.getUsernameOwner(), appointment.getTelephoneOwner(), appointment.getAddress(), appointment.getDescription(), appointment.getDataPrimaZi(), appointment.getNumarDeZile()));
                user.addAppointmentInList(appointment);
                userRepository.update(user);
            }
        }
    }


    private static void checkAppointmentDoesNotAlreadyExist(String usernamePetSitter, String description) throws AppointmentAlreadyExistsException {
        for (Appointment appointment : appointmentRepository.find()) {
            if (Objects.equals(usernamePetSitter, appointment.getUsernamePetSitter()))
                throw new AppointmentAlreadyExistsException(usernamePetSitter, description);
        }
    }

    public static void acceptAppointment(Appointment appointment) throws NotExistingAppointmentException {
        String usernamePetSitter = new String();
        String usernameOwner  = new String();
        for (Appointment app : appointmentRepository.find()) {
            if (Objects.equals(app, appointment)) {//update the status of appointment in appointmentRepository for appointment
                appointment.setStatusAsAccept();
                appointmentRepository.update(appointment);
                usernamePetSitter = app.getUsernamePetSitter();
                usernameOwner = app.getUsernameOwner();

            }
        }
        if (usernamePetSitter != null) {  //update the status of appointment in userRepository for PetSitter
            updateInUserRepositoryAsAccept(usernamePetSitter, appointment);
        }
        if (usernameOwner != null) { //update the status of appointment in userRepository for Owner
            updateInUserRepositoryAsAccept(usernameOwner, appointment);
        }
        if (Objects.equals(usernamePetSitter, null) || Objects.equals(usernameOwner, null))
            throw new NotExistingAppointmentException();

    }

    public static void updateInUserRepositoryAsAccept (String username, Appointment appointment) {
        for (User user : userRepository.find()) {
            if (Objects.equals(username, user.getUsername())) {
                ArrayList<Appointment> appointmentList = user.getAppointments();
                for (Appointment ap : appointmentList) {
                    if (Objects.equals(ap, appointment)) {
                        ap.setStatusAsAccept();
                        userRepository.update(user);
                    }
                }
            }
        }
    }



    public static void denyAppointment(Appointment appointment) throws NotExistingAppointmentException {
        String usernamePetSitter = new String();
        String usernameOwner  = new String();
        for (Appointment app : appointmentRepository.find()) {
            if (Objects.equals(app, appointment)) {//update the status of appointment in appointmentRepository for appointment
                appointment.setStatusAsDeny();
                appointmentRepository.update(appointment);
                usernamePetSitter = app.getUsernamePetSitter();
                usernameOwner = app.getUsernameOwner();

            }
        }
        if (usernamePetSitter != null) {  //update the status of appointment in userRepository for PetSitter
            updateInUserRepositoryAsDeny(usernamePetSitter, appointment);
        }
        if (usernameOwner != null) { //update the status of appointment in userRepository for Owner
            updateInUserRepositoryAsDeny(usernameOwner, appointment);
        }
        if (Objects.equals(usernamePetSitter, null) || Objects.equals(usernameOwner, null))
            throw new NotExistingAppointmentException();

    }

    public static void updateInUserRepositoryAsDeny (String username, Appointment appointment) {
        for (User user : userRepository.find()) {
            if (Objects.equals(username, user.getUsername())) {
                ArrayList<Appointment> appointmentList = user.getAppointments();
                for (Appointment ap : appointmentList) {
                    if (Objects.equals(ap, appointment)) {
                        ap.setStatusAsDeny();
                        userRepository.update(user);
                    }
                }
            }
        }
    }

    public static ArrayList <Appointment> getRequestList(String username) {
        int index = 0;
        ArrayList <Appointment> requestList = new ArrayList<Appointment> (50);
        for(Appointment request : appointmentRepository.find()){
            if(Objects.equals(username, request.getUsernamePetSitter()) && Objects.equals(request.getStatus(), "processing")){
                if (index == 49) {
                    ArrayList <Appointment> aux = new ArrayList<Appointment> (index + 50);
                    requestList = aux;
                }
                requestList.add(request);
                index++;
            }
        }
        return requestList;
    }

    public static String getHashedUserPassword(String username) throws UserNotFoundException{
        return getUser(username).getPassword();
    }

    public static List<Appointment> getAllAppointments() {
        return appointmentRepository.find().toList();
    }

    public static ObjectRepository<Appointment> getAppointmentRepository() {
        return appointmentRepository;
    }
    public static ObjectRepository<User> getUserRepository() {
        return userRepository;
    }


}
