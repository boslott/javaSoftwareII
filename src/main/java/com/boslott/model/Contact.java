
package com.boslott.model;

/**
 * Provides a model for contacts with the needed getter and setter
 * methods to interact with this Contact object's property values
 *
 * @author Steven Slott
 */
public class Contact {
    
    private int contactID;
    private String contactName;
    private String contactEmail;
    
    

    /**
     * Gets this Contact object's contactID property value
     *
     * @return this Contact object's contactID
     */
    
    public int getContactID() {
        return this.contactID;
    }
    
    /**
     * Gets this Contact object's contactName property value
     *
     * @return this Contact object's contactName
     */
    public String getContactName() {
        return this.contactName;
    }
    
    /**
     * Gets this Contact object's contactEmail property value
     *
     * @return this Contact object's contactEmail
     */
    public String getContactEmail() {
        return this.contactEmail;
    }
    
    
    

    /**
     * Sets this Contact object's contactID property with the given value
     *
     * @param newID this Contact object's new contactID
     */
    
    public void setContactID(int newID) {
        this.contactID = newID;
    } 
    
    /**
     * Sets this Contact object's contactName property with the given value
     *
     * @param newName this Contact object's new contactName
     */
    public void setContactName(String newName) {
        this.contactName = newName;
    }
    
    /**
     * Sets this Contact object's contactEmail property to the given value
     *
     * @param newEmail this Contact object's new contactEmail
     */
    public void setContactEmail(String newEmail) {
        this.contactEmail = newEmail;
    }
    
}
