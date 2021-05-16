package org.fis.student.sre.model;



public class Owner extends User{
    private int telephoneOwner;
    private String descriptionOwner;
    private String addressOwner;

    public Owner (String username, String password, String role,  int telephoneOwner, String descriptionOwner, String addressOwner) {
        super(username, password, role);
        this.telephoneOwner = telephoneOwner;
        this.descriptionOwner = descriptionOwner;
        this.addressOwner = addressOwner;
    }

    public Owner(String username, String password, int  telephoneOwner, String descriptionOwner, String addressOwner) {
        super(username, password);
        this.telephoneOwner = telephoneOwner;
        this.descriptionOwner = descriptionOwner;
        this.addressOwner = addressOwner;
    }

    public int getTelephoneOwner() { return this.telephoneOwner; }
    public String getDescriptionOwner() { return  this.descriptionOwner; }
    public String getAddressOwner() { return this.addressOwner; }
}
