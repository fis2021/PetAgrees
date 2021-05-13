package org.fis.student.sre.model;

public class Appointment {
    protected String usernamePetSitter;
    protected String usernameOwner;
    protected int telephoneOwner;
    protected String description;
    protected String status;
    protected String address;

    public Appointment (String usernamePetSitter,String usernameOwner, int telephoneOwner, String address, String description) {
        this.description = description;
        this.telephoneOwner = telephoneOwner;
        this.usernameOwner = usernameOwner;
        this.usernamePetSitter = usernamePetSitter;
        this.address = address;
        this.status = "processing";
    }

    public Appointment (String usernamePetSitter,String usernameOwner, int telephoneOwner, String address, String description, String status) {
        this.description = description;
        this.telephoneOwner = telephoneOwner;
        this.usernameOwner = usernameOwner;
        this.usernamePetSitter = usernamePetSitter;
        this.address = address;
        this.status = status;
    }

    public String toString() {
        String s = "Owner username: " + this.usernameOwner + "\n";
        s = s + "Owner telephone: " + this.telephoneOwner + "\n";
        s = s + "Pet sitter username: " + this.usernamePetSitter + "\n";
        s = s + "Address: " + this.address;
        s = s + "Description: " + this.description;
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

    public void setStatusAsAccept() {
        this.status = "ACCEPT";
    }

    public void setStatusAsDeny() {
        this.status = "DENY";
    }

}
