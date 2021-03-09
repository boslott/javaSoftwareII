
package com.boslott.model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Provides a model for divisions with all the needed getter and setter
 * methods to interact with this Division object's property values
 *
 * @author Steven Slott
 */
public class Division {
    
    private int divisionID;
    private String division;
    private Date createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int countryID;
    
    

    /**
     * Gets this Division object's divisionID property value
     *
     * @return this Division object's divisionID
     */
    
    public int getDivisionID() {
        return this.divisionID;
    }
    
    /**
     * Gets this Division object's division property value
     *
     * @return this Division object's divisionID
     */
    public String getDivision() {
        return this.division;
    }
    
    /**
     * Gets this Division object's createDate property value
     *
     * @return this Division object's createDate
     */
    public Date getCreateDate() {
        return this.createDate;
    }
    
    /**
     * Gets this Division object's createdBy property value
     *
     * @return this Division object's createdBy
     */
    public String getCreatedBy() {
        return this.createdBy;
    }
    
    /**
     * Gets this Division object's lastUpdate property value
     *
     * @return this Division object's lastUpdate Timestamp
     */
    public Timestamp getLastUpdate() {
        return this.lastUpdate;
    }
    
    /**
     * Gets this Division object's lastUpdatedBy property value
     *
     * @return this Division object's lastUpdatedBy
     */
    public String getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }
    
    /**
     * Gets this Division object's countryID property value
     *
     * @return this Division object's countryID
     */
    public int getCountryID() {
        return this.countryID;
    }
    
    
    

    /**
     * Sets this Division object's divisionID property to the given value
     *
     * @param id this Division object's new divisionID
     */
    
    public void setDivisionID(int id) {
        this.divisionID = id;
    }
    
    /**
     * Sets this Division object's division property to the given value
     *
     * @param div this Division object's new division
     */
    public void setDivision(String div) {
        this.division = div;
    }
    
    /**
     * Sets this Division object's createDate property to the given value
     *
     * @param date this Division object's new createDate
     */
    public void setCreateDate(Date date) {
        this.createDate = date;
    }
    
    /**
     * Sets this Division object's createdBy property to the given value
     *
     * @param creator this Division object's new createdBy
     */
    public void setCreatedBy(String creator) {
        this.createdBy = creator;
    }
    
    /**
     * Sets this Division object's lastUpdate property to the given value
     *
     * @param update this Division object's new lastUpdate
     */
    public void setLastUpdate(Timestamp update) {
        this.lastUpdate = update;
    }
    
    /**
     * Sets this Division object's lastUpdatedBy property to the given value
     *
     * @param updator this Division object's new lastUpdatedBy
     */
    public void setLastUpdatedBy(String updator) {
        this.lastUpdatedBy = updator;
    }
    
    /**
     * Sets this Division object's countryID property to the given value
     *
     * @param id this Division object's new countryID
     */
    public void setCountryID(int id) {
        this.countryID = id;
    }
    
}
