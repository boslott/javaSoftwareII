
package com.boslott.model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Provides a model for appointments with the needed getter and
 * setter methods to interact with this Appointment object's property values
 * 
 * @author Steven Slott
 */
public class Appointment {
    
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp startDateTime;
    private Timestamp endDateTime;
    private Date createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int customerID;
    private int userID;
    private int contactID;
    
    /**
     * Gets this Appointment object's appointmentID property value
     *
     * @return this Appointment object's appointmentID
     */
    public int getAppointmentID() {
        return this.appointmentID;
    }
    
    /**
     * Gets this Appointment object's title property value
     *
     * @return this Appointment object's String title
     */
    public String getTitle() {
        return this.title;
    }
    
    /**
     * Gets this Appointment object's description property value
     *
     * @return this Appointment object's String description
     */
    public String getDescription() {
        return this.description;
    }
    
    /**
     * Gets this Appointment object's location property value
     *
     * @return this Appointment object's String location
     */
    public String getLocation() {
        return this.location;
    }
    
    /**
     * Gets this Appointment object's type property value
     *
     * @return this Appointment object's String type
     */
    public String getType() {
        return this.type;
    }
    
    /**
     * Gets this Appointment object's startDateTime property value
     *
     * @return this Appointment object's Timestamp startDateTime in UTC
     */
    public Timestamp getStartDateTime() {
        return this.startDateTime;
    }

    /**
     * Gets this Appointment object's endDateTime property value
     *
     * @return this Appointment object's Timestamp endDateTime in UTC
     */
    public Timestamp getEndDateTime() {
        return this.endDateTime;
    }

    /**
     * Gets this Appointment object's createDate property value
     *
     * @return this Appointment object's Date createDate
     */
    public Date getCreateDate() {
        return this.createDate;
    }
    
    /**
     * Gets this Appointment object's createdBy property value
     *
     * @return this Appointment object's String createdBy
     */
    public String getCreatedBy() {
        return this.createdBy;
    }
    
    /**
     *  Gets this Appointment object's lastUpdate property value
     *
     * @return this Appointment object's Timestamp lastUpdate
     */
    public Timestamp getLastUpdate() {
        return this.lastUpdate;
    }
    
    /**
     * Gets this Appointment object's lastUpdatedBy property value
     *
     * @return this Appointment object's String lastUpdatedBy
     */
    public String getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }
    
    /**
     * Gets this Appointment object's customerID property value
     *
     * @return this Appointment object's primitive int customerID
     */
    public int getCustomerID() {
        return this.customerID;
    }
    
    /**
     * Gets this Appointment object's userID property value
     *
     * @return this Appointment object's primitive int userId
     */
    public int getUserID() {
        return this.userID;
    }
    
    /**
     * Get this Appointment object's contactID property value
     *
     * @return this Appointment object's primitive int contactID
     */
    public int getContactID() {
        return this.contactID;
    }
    
    /**
     * Sets this Appointment object's appointmentID property to the given value
     *
     * @param id - this Appointment object's new ID
     */
    public void setAppointmentID(int id) {
        this.appointmentID = id;
    }
    
    /**
     * Sets this Appointment object's title property to the given value
     *
     * @param newTitle - this Appointment object's new title
     */
    public void setTitle(String newTitle) {
        this.title = newTitle;
    } 
    
    /**
     * Sets this Appointment object's description property to the given value
     *
     * @param newDescription - this Appointment object's new description
     */
    public void setDescription(String newDescription) {
        this.description = newDescription;
    }
    
    /**
     * Sets this Appointment object's location property to the given value
     *
     * @param newLocation - this Appointment object's new location
     */
    public void setLocation(String newLocation) {
        this.location = newLocation;
    }
    
    /**
     * Sets this Appointment object's type property to the given value
     *
     * @param newType - this Appointment object's new type
     */
    public void setType(String newType) {
        this.type = newType;
    }
    
    /**
     * Sets this Appointment object's startDateTime property to the given value
     *
     * @param start - this Appointment object's new startDateTime Timestamp in UTC
     */
    public void setStartDateTime(Timestamp start) {
        this.startDateTime = start;
    }

    /**
     * Sets this Appointment object's endDateTime property to the given value
     *
     * @param end - this Appointment object's new endDateTime Timestamp in UTC
     */
    public void setEndDateTime(Timestamp end) {
        this.endDateTime = end;
    }
    
    /**
     * Sets this Appointment object's createDate property to the given value
     *
     * @param newDate - this Appointment object's new createDate
     */
    public void setCreateDate(Date newDate) {
        this.createDate = newDate;
    }
    
    /**
     * Sets this Appointment object's createdBy property to the given value
     *
     * @param creator - this Appointment object's new createdBy
     */
    public void setCreatedBy(String creator) {
        this.createdBy = creator;
    }
    
    /**
     * Sets this Appointment object's lastUpdate property to the given value
     *
     * @param update - this Appointment object's new lastUpdate Timestamp
     */
    public void setLastUpdate(Timestamp update) {
        this.lastUpdate = update;
    }
    
    /**
     * Sets this Appointment object's lastUpdatedBy property to the given value
     *
     * @param updator - this Appointment object's new lastUpdatedBy
     */
    public void setLastUpdatedBy(String updator) {
        this.lastUpdatedBy = updator;
    }
    
    /**
     * Sets this Appointment object's customerID property to the given value
     *
     * @param custID - this Appointment object's new customerID
     */
    public void setCustomerID(int custID) {
        this.customerID = custID;
    }
    
    /**
     * Sets this Appointment object's userId property to the given value
     *
     * @param uID - this Appointment object's new userID
     */
    public void setUserID(int uID) {
        this.userID = uID;
    }
    
    /**
     * Sets this Appointment object's contactID property to the given value
     *
     * @param conID - this Appointment object's new contactID
     */
    public void setContactID(int conID) {
        this.contactID = conID;
    }
    
}
