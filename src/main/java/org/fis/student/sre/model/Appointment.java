package org.fis.student.sre.model;
import java.util.*;
import javax.xml.crypto.Data;

public class Appointment {
    protected String usernamePetSitter;
    protected String usernameOwner;
    protected int telephoneOwner;
    protected String description;
    protected String status;
    protected String address;
    protected Calendar dataPrimaZi;
    protected int numarDeZile;

    public Appointment (String usernamePetSitter,String usernameOwner, int telephoneOwner, String address, String description, int anpz, int lunapz, int zipz, int orapz, int minutpz, int numarDeZile) {
        this.description = description;
        this.telephoneOwner = telephoneOwner;
        this.usernameOwner = usernameOwner;
        this.usernamePetSitter = usernamePetSitter;
        this.address = address;
        this.status = "processing";
        this.dataPrimaZi.set(anpz, lunapz, zipz, orapz, minutpz);
        this.numarDeZile = numarDeZile;
    }


    public Appointment (String usernamePetSitter,String usernameOwner, int telephoneOwner, String address, String description, String status,  int anpz, int lunapz, int zipz, int orapz, int minutpz, int numarDeZile) {
        this.description = description;
        this.telephoneOwner = telephoneOwner;
        this.usernameOwner = usernameOwner;
        this.usernamePetSitter = usernamePetSitter;
        this.address = address;
        this.status = status;
        this.dataPrimaZi.set(anpz, lunapz, zipz, orapz, minutpz);
        this.numarDeZile = numarDeZile;
    }

    public Appointment (String usernamePetSitter,String usernameOwner, int telephoneOwner, String address, String description, Calendar dataPrimaZi, int numarDeZile) {
        this.description = description;
        this.telephoneOwner = telephoneOwner;
        this.usernameOwner = usernameOwner;
        this.usernamePetSitter = usernamePetSitter;
        this.address = address;
        this.status = "processing";
        this.dataPrimaZi = dataPrimaZi;
        this.numarDeZile = numarDeZile;
    }

    public String toString() {
        String s = "Pet sitter username: " + this.usernamePetSitter + "\n";
        s = s + "Owner username: " + this.usernameOwner + "\n";
        s = s + "Owner telephone: " + this.telephoneOwner + "\n";
        s = s + "Address: " + this.address;
        s = s + "Description: " + this.description;
        s = s + "Status: " + this.status;


        return s;
    }

    public String toStringSmallDetails() {
        String s = "Owner username: " + this.usernameOwner + "\n";
        s = s + "Address: " + this.address;
        s = s + "Status: " + this.status;

        return s;
    }

    public String getUsernamePetSitter() {
        return this.usernamePetSitter;
    }
    public String getUsernameOwner() {
        return this.usernameOwner;
    }
    public int getTelephoneOwner() {
        return this.telephoneOwner;
    }
    public String getDescription() {
        return this.description;
    }
    public String getStatus() {
        return this.status;
    }
    public String getAddress() {
        return this.address;
    }
    public Calendar getDataPrimaZi() { return  this.dataPrimaZi;}
    public int getNumarDeZile() { return this.numarDeZile;}

    public void setStatusAsAccept() {
        this.status = "ACCEPT";
    }

    public void setStatusAsDeny() {
        this.status = "DENY";
    }

    public String getDataPrimaZiAsString() { return  this.dataPrimaZi.toString();}

}
