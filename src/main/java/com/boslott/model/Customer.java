
package com.boslott.model;

import java.util.Date;
import java.sql.Timestamp;

/**
 * Provides a model for customers with the needed getter and setter
 * methods to interact with this Customer object's property values
 *
 * @author Steven Slott
 */
public class Customer {
    
    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private Date createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int divisionID;
    
    

    /**
     * Gets this Customer object's customerID property value
     *
     * @return this Customer object's customerID
     */
    
    public int getCustomerID() {
        return this.customerID;
    }
    
    /**
     * Gets this Customer object's customerName property value
     *
     * @return this Customer object's customerName 
     */
    public String getCustomerName() {
        return this.customerName;
    }
    
    /**
     * Gets this Customer object's address property value
     *
     * @return this Customer object's address
     */
    public String getAddress() {
        return this.address;
    }
    
    /**
     * Gets this Customer object's postalCode property value
     *
     * @return this Customer object's postalCode
     */
    public String getPostalCode() {
        return this.postalCode;
    }
    
    /**
     * Gets this Customer object's phone property value
     *
     * @return this Customer object's phone 
     */
    public String getPhone() {
        return this.phone;
    }
    
    /**
     * Gets this Customer object's createDate property value
     * 
     * @return this Customer object's createDate
     */
    public Date getCreateDate() {
        return this.createDate;
    }
    
    /**
     * Gets this Customer object's createdBy property value
     *
     * @return this Customer object's createdBy
     */
    public String getCreatedBy() {
        return this.createdBy;
    }
    
    /**
     * Gets this Customer object's lastUpdate property value
     *
     * @return this Customer object's lastUpdate Timestamp
     */
    public Timestamp getLastUpdate() {
        return this.lastUpdate;
    }
    
    /**
     * Gets this Customer object's lastUpdatedBy property value
     *
     * @return this Customer object's lastUpdatedBy
     */
    public String getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }
    
    /**
     * Gets this Customer object's divisionID property value
     *
     * @return this Customer object's First-Level Division ID
     */
    public int getDivisionID() {
        return this.divisionID;
    }
    
    

    /**
     * Sets this Customer object's customerID property to the given value
     *
     * @param ID this Customer object's new customerID
     */
    
    public void setCustomerID(int ID) {
        this.customerID = ID;
    }
    
    /**
     * Sets this Customer object's customerName property to the given value
     *
     * @param name this Customer object's new customerName
     */
    public void setCustomerName(String name) {
        this.customerName = name;
    }
    
    /**
     * Sets this Customer object's address property to the given value
     *
     * @param addr this Customer object's new address
     */
    public void setAddress(String addr) {
        this.address = addr;
    }
    
    /**
     * Sets this Customer object's postalCode property to the given value
     *
     * @param postal this Customer object's new postalCode
     */
    public void setPostalCode(String postal) {
        this.postalCode = postal;
    }
    
    /**
     * Sets this Customer object's phone property to the given value
     *
     * @param phoneNum this Customer object's new phone
     */
    public void setPhone(String phoneNum) {
        this.phone = phoneNum;
    }
    
    /**
     * Sets this Customer object's createDate property to the given value
     *
     * @param datetime this Customer object's new createDate
     */
    public void setCreateDate(Date datetime) {
        this.createDate = datetime;
    }
    
    /**
     * Sets this Customer object's createdBy property to the given value
     * 
     * @param creator this Customer object's new createdBy
     */
    public void setCreatedBy(String creator) {
        this.createdBy = creator;
    }
    
    /**
     * Sets this Customer object's lastUpdate property to the given value
     *
     * @param time this Customer object's new lastUpdate Timestamp
     */
    public void setLastUpdate(Timestamp time) {
        this.lastUpdate = time;
    }
    
    /** 
     * Sets this Customer object's lastUpdatedBy property to the given value
     *
     * @param updator this Customer object's new lastUpdatedBy
     */
    public void setLastUpdatedBy(String updator) {
        this.lastUpdatedBy = updator;
    }
    
    /**
     * Sets this Customer object's divisionID property to the given value
     *
     * @param ID this Customer object's new First-Level Division ID
     */
    public void setDivisionID(int ID) {
        this.divisionID = ID;
    }
    
    
}
