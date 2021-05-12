package org.fis.student.sre.model;

import org.dizitart.no2.objects.Id;
import java.awt.*;
import java.awt.image.BufferedImage;

public class User {
    @Id
    private String username;
    private String password;
    private String role;
    protected BufferedImage imageOfCertification;

    public User(String username, String password, String role, BufferedImage imageOfCertification) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.imageOfCertification = imageOfCertification;
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

    public Graphics getImageOfCertification() {

        return this.imageOfCertification.getGraphics();
    }

    public void setImageOfCertification(BufferedImage role) {

        this.imageOfCertification = imageOfCertification;
    }

    public int getTypeOfImage() {

        return this.imageOfCertification.getType();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (imageOfCertification != null ? !imageOfCertification.equals(user.imageOfCertification) : user.imageOfCertification != null) return false;
        return role != null ? role.equals(user.role) : user.role == null;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }
}
