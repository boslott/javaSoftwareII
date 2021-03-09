
package com.boslott.model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Provides a model for countries with the needed getter and setter 
 * methods to interact with this Country object's property values
 *
 * @author Steven Slott
 */
public class Country {
    
    private int countryID;
    private String country;
    private Date createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    
    

    /**
     * Gets this Country object's countryID property value
     *
     * @return this Country object's countryID
     */
    
    public int getCountryID() {
        return this.countryID;
    }
    
    /**
     * Gets this Country object's country property value
     *
     * @return this Country object's country name
     */
    public String getCountry() {
        return this.country;
    }
    
    /**
     * Gets this Country object's createDate property value
     *
     * @return this Country object's createDate
     */
    public Date getCreateDate() {
        return this.createDate;
    }
    
    /**
     * Gets this Country object's createdBy property value
     *
     * @return this Country object's createdBy
     */
    public String getCreatedBy() {
        return this.createdBy;
    }
    
    /**
     * Gets this Country object's lastUpdate property value
     *
     * @return this Country object's lastUpdate Timestamp
     */
    public Timestamp getLastUpdate() {
        return this.lastUpdate;
    }
    
    /**
     * Gets this Country object's lastUpdatedBy property value
     *
     * @return this Country object's lastUpdatedBy
     */
    public String getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }
    
   

    /**
     * Sets this Country object's countryID to the given value
     *
     * @param id this Country object's new ID
     */
    
    public void setCountryID(int id) {
        this.countryID = id;
    }
    
    /**
     * Sets this Country object's country property to the given value
     *
     * @param country this Country object's new country
     */
    public void setCountry(String country) {
        this.country = country;
    }
    
    /**
     * Sets this Country object's createDate property to the given value
     *
     * @param date this Country object's new createDate
     */
    public void setCreateDate(Date date) {
        this.createDate = date;
    }
    
    /**
     * Sets this Country object's createdBy property to the given value
     *
     * @param creator this Country object's new createdBy name
     */
    public void setCreatedBy(String creator) {
        this.createdBy = creator;
    }
    
    /**
     * Sets this Country object's lastUpdate property to the given value
     *
     * @param update this Country object's new lastUpdate Timestamp
     */
    public void setLastUpdate(Timestamp update) {
        this.lastUpdate = update;
    }
    
    /**
     * Sets this Country object's lastUpdatedBy property to the given value
     *
     * @param updator this Country object's new lastUpdatedBy name
     */
    public void setLastUpdatedBy(String updator) {
        this.lastUpdatedBy = updator;
    }
    
}
