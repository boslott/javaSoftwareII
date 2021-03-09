
package com.boslott.DataAccess;

import com.boslott.model.Appointment;
import com.boslott.model.Contact;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * A UCertdbContactDAO object is a Contact Data Access Object used to interact
 * with Contact data from a UCertify database
 *
 * @author Steven Slott
 */
public class UCertdbContactDAO implements ContactDAO {

    /**
     * A connection to the UCertify database for Contact data
     */
    private static Connection contactConn;
    
    /**
     * The local cache of Contact objects
     */
    private static ArrayList<Contact> contacts;

    /**
     * Constructs an UCertifydbContactDAO object and gets a connection to 
     * the UCertify database data source
     *
     * @throws SQLException if there is a database access error
     */
    public UCertdbContactDAO() throws SQLException {
        contactConn = UCertdbDAOFactory.getConnectionInstance();
        System.out.println("ContactDAO has obtained a connection");
    }
    
    /**
     * Gets all Contact data from the local ArrayList of Contact objects
     * or calls the private class method to get data from the UCertify database
     *
     * @return an ArrayList of all Contact objects from the UCertify database
     */
    @Override
    public ArrayList<Contact> findAllContacts() {
        if(contacts == null) {
            contacts = getAllContactsFromDB();
        }
        return this.contacts;
    }
    
    /**
     * Gets a single Contact entry from the UCertify database that has the
     * contactID of id
     *
     * @param id the contactID of the Contact entry to search the UCertify
     * database for
     * @return a Contact object with the data of the Contact entry in the UCertify
     * database with the contactID of id
     */
    @Override
    public Contact findContact(int id) {
        Contact con = new Contact();
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = contactConn.createStatement();
            
            rs = stmt.executeQuery("SELECT * FROM contacts WHERE Contact_ID = " + id);

            while (rs.next()) {
                con.setContactID(rs.getInt("Contact_ID"));
                con.setContactName(rs.getString("Contact_Name"));
                con.setContactEmail(rs.getString("Email"));
            }
            
            
        }
        catch (SQLException exception){
            // handle any errors
            System.out.println("SQLException: " + exception.getMessage());
            System.out.println("SQLState: " + exception.getSQLState());
            System.out.println("VendorError: " + exception.getErrorCode());
        }
        finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) { }

            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) { }

            }
        }
        
        return con;
    }
    
    /**
     * Gets all the Contact from the UCertify database
     * 
     * @return an ArrayList of all Contact objects from the UCertify database
     */
    private static ArrayList<Contact> getAllContactsFromDB() {
        ArrayList<Contact> allContactsList = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = contactConn.createStatement();
            
            rs = stmt.executeQuery("SELECT * FROM contacts");

            while (rs.next()) {
                
                Contact newContact = new Contact();
                
                newContact.setContactID(rs.getInt("Contact_ID"));
                newContact.setContactName(rs.getString("Contact_Name"));
                newContact.setContactEmail(rs.getString("Email"));
                
                allContactsList.add(newContact);
            }
            
            
        }
        catch (SQLException exception){
            // handle any errors
            System.out.println("SQLException: " + exception.getMessage());
            System.out.println("SQLState: " + exception.getSQLState());
            System.out.println("VendorError: " + exception.getErrorCode());
        }
        finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) { }

            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) { }

            }
        }
        
        return allContactsList;
    }

    /**
     * Gets a single Contact entry from the UCertify database that has the
     * contactName property value equal to name
     *
     * @param name the contactName property value of the Contact entry to search
     * the UCertify database for
     * @return the Contact object containing all the data for the Contact entry requested
     */
    @Override
    public Contact findContactByName(String name) {
        Contact con = new Contact();
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = contactConn.createStatement();
            System.out.println("UCertContactDAO is about to findContactByName, contact name: " + name);
            
            rs = stmt.executeQuery("SELECT * FROM contacts WHERE Contact_Name = '" + name + "'");

            while (rs.next()) {
                con.setContactID(rs.getInt("Contact_ID"));
                con.setContactName(rs.getString("Contact_Name"));
                con.setContactEmail(rs.getString("Email"));
            }
            
            
        }
        catch (SQLException exception){
            // handle any errors
            System.out.println("SQLException: " + exception.getMessage());
            System.out.println("SQLState: " + exception.getSQLState());
            System.out.println("VendorError: " + exception.getErrorCode());
        }
        finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) { }

            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) { }

            }
        }
        
        return con;
    }
    
}
