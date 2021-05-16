package org.fis.student.sre.model;

import org.dizitart.no2.objects.Id;

import java.util.ArrayList;

public class User {
    @Id
    private String username;
    private String password;
    private String role;
    private ArrayList<Appointment> appointments;
    private int listIndex;
    private int max = 50;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.appointments = new ArrayList <Appointment>(max);
        this.listIndex = 0;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public String getRole() {

        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        return role != null ? role.equals(user.role) : user.role == null;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    private void listIsFull() {
        max += 50;
        ArrayList <Appointment> aux;
        aux = new ArrayList<Appointment>(max);
        for (Appointment ap : appointments) {
            aux.add(ap);
        }
        appointments = aux;
    }

    public void addAppointmentInList(Appointment ap) {
        this.appointments.add(ap);
        this.listIndex++;
        if (this.listIndex == max) {
            listIsFull();
        }
    }

    public int getMax() {
        return this.max;
    }

    public ArrayList <Appointment> getAppointments() {
        return this.appointments;
    }

}
