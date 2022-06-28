/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package schedulingapp.model;

/**
 * Initializes the contact object class.
 * @author Sam
 */
public class Contact {
    private int contact_ID;
    private String contact_Name;
    private String email;
    
    /** creates the Contact object
     * 
     * @param contact_ID integer for contact id#
     * @param contact_Name string for contact name
     * @param email string for contact email
     */
    
    public Contact(int contact_ID, String contact_Name, String email) {
        this.contact_ID = contact_ID;
        this.contact_Name = contact_Name;
        this.email = email;
    }
    
    /**
     * @return the contact_ID;
     */
    public int getContactID() {
        return contact_ID;
    }
    
    /**
     * @param contact_ID the contact_ID to set
     */
    public void setContactID(int contact_ID) {
        this.contact_ID = contact_ID;
    }
    
    /**
     * @return the contact_Name;
     */
    public String getContactName() {
        return contact_Name;
    }
    
    /**
     * @param contact_Name the contact_Name to set
     */
    public void setContactID(String contact_Name) {
        this.contact_Name = contact_Name;
    }
    
    /**
     * @return the email;
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
}

