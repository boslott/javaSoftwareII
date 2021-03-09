
package com.boslott.model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Provides a model for users with the needed getter and setter methods to
 * interact with this User object's property values
 *
 * @author Steven Slott
 */
public class User {
    
    private int userID;
    private String userName;
    private String password;
    private Date createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    
    

    /**
     * Gets this User object's userID property value
     *
     * @return this User object's userID
     */
    
    public int getUserID() {
        return this.userID;
    }
    
    /**
     * Gets this User object's userName property value
     *
     * @return this User object's userName
     */
    public String getUserName() {
        return this.userName;
    }
    
    /**
     * Gets this User object's password property value
     *
     * @return this User object's password
     */
    public String getPassword() {
        return this.password;
    }
    
    /**
     * Gets this User object's createDate property value
     *
     * @return this User object's createDate
     */
    public Date getCreateDate() {
        return this.createDate;
    }
    
    /**
     * Gets this User object's createdBy property value
     *
     * @return this User object's createdBy
     */
    public String getCreatedBy() {
        return this.createdBy;
    }
    
    /**
     * Gets this User object's lastUpdate property value
     *
     * @return this User object's lastUpdate Timestamp
     */
    public Timestamp getLastUpdate() {
        return this.lastUpdate;
    }
    
    /**
     * Gets this User object's lastUpdatedBy property value
     *
     * @return this User object's lastUpdatedBy
     */
    public String getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }
    
    
    

    /**
     * Sets this User object's userID property to the given value
     *
     * @param ID this User object's new  userID
     */
    
    public void setUserID(int ID) {
        this.userID = ID;
    }
    
    /**
     * Sets this User object's userName property to the given value
     * 
     * @param name this User object's new userName
     */
    public void setUserName(String name) {
        this.userName = name;
    }
    
    /**
     * Sets this User object's password property to the given value
     *
     * @param pass this User object's new password
     */
    public void setPassword(String pass) {
        this.password = pass;
    }
    
    /**
     * Sets this User object's createDate property to the given value
     *
     * @param date this User object's new createDate
     */
    public void setCreateDate(Date date) {
        this.createDate = date;
    }
    
    /**
     * Sets this User object's createdBy property to the given value
     *
     * @param creator this User object's new createdBy
     */
    public void setCreatedBy(String creator) {
        this.createdBy = creator;
    }
    
    /**
     * Sets this User object's lastUpdate property to the given value
     *
     * @param update this User object's new lastUpdate
     */
    public void setLastUpdate(Timestamp update) {
        this.lastUpdate = update;
    }
    
    /**
     * Sets this User object lastUpdatedBy property to the given value
     *
     * @param updator this User object's new lastUpdatedBy
     */
    public void setLastUpdatedBy(String updator) {
        this.lastUpdatedBy = updator;
    }
    
}
