
package com.boslott.DataAccess;

import com.boslott.model.Appointment;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.ArrayList;

/**
 * A UCertdbAppointmentDAO object is an Appointment Data Access Object used to interact
 * with Appointment data from a UCertify database
 *
 * @author Steven Slott
 */
public class UCertdbAppointmentDAO implements AppointmentDAO {
    
    /**
     * The connection to the UCertify database for Appointment data
     */
    private static Connection appointmentConn;
    
    /**
     * The local cache of Appointment objects
     */
    private static ArrayList<Appointment> appointments;

    /**
     * Constructs an UCertdbAppointmentDAO object and gets a connection to the 
     * UCertify database data source
     *
     * @throws SQLException if there is a database access error
     */
    public UCertdbAppointmentDAO() throws SQLException {
        appointmentConn = UCertdbDAOFactory.getConnectionInstance();
    }
    
    /**
     * Gets all Appointment data from the local ArrayList of Appointment
     * objects or calls the private class method to get data from the UCertify database.
     * If refetch is true or if the local ArrayList of Appointment objects is null,
     * the method calls to the UCertdb again. If retch is false, the method returns
     * the locally cached results.
     *
     * @param refetch whether to call to the UCertdb again or not
     * @return an ArrayList of all Appointment objects
     */
    @Override
    public ArrayList<Appointment> findAllAppointments(Boolean refetch) {
        if(appointments == null || refetch) {
            appointments = getAllAppointmentsFromDB();
        }
        return appointments;
    }
    
    /**
     * Gets all Appointment data from the UCertify database related to one Customer
     * entry with the customerName property value of the given customerName String
     *
     * @param customerName the customerName value of the customerName property of
     * the Customer entry to search the UCertify database for
     * @return an ArrayList of all Appointment objects related to one Customer entry
     * with the customerName property value of the given customerName String
     */
    @Override
    public ArrayList<Appointment> findAllCustomerAppointments(String customerName) {
        
        ArrayList<Appointment> allCustomerAppts = new ArrayList();
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = appointmentConn.createStatement();
            
            rs = stmt.executeQuery("SELECT * FROM appointments WHERE Customer_ID = (SELECT Customer_ID FROM customers WHERE Customer_Name = '" + customerName + "')");

            while (rs.next()) {
                Appointment newAppt = new Appointment();
                
                newAppt.setAppointmentID(rs.getInt("Appointment_ID"));
                newAppt.setStartDateTime(rs.getTimestamp("Start"));
                newAppt.setEndDateTime(rs.getTimestamp("End"));
                newAppt.setCustomerID(rs.getInt("Customer_ID"));
                
                allCustomerAppts.add(newAppt);
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
        return allCustomerAppts;
    }

    /**
     * Inserts a new Appointment entry into the UCertify database with the data
     * contained in the newAppt object
     *
     * @param newAppt the object containing the property values (except the appointmentID) of the new
     * Appointment entry being inserted into the UCertdb
     * @return true if the insert successfully completes, false if the insert
     * does not successfully complete
     */
    @Override
    public Boolean insertAppointment(Appointment newAppt) {
        Statement stmt = null;
        int rs = -1;

        try {
            stmt = appointmentConn.createStatement();
            
            rs = stmt.executeUpdate("ALTER TABLE appointments AUTO_INCREMENT=3");
            
            System.out.println("Just executed the add auto_incr, rs: " + rs);
            
            rs = stmt.executeUpdate("INSERT INTO appointments"
                + "(Appointment_ID, Title, Description, Location, Type, Start, End, Last_Update, Create_Date, Created_By, Last_Updated_By, Contact_ID, Customer_ID, User_ID)"   
                + "VALUES ("
                + null + ", '"
                + newAppt.getTitle()
                + "', '"
                + newAppt.getDescription()
                + "', '"
                + newAppt.getLocation()
                + "', '"
                + newAppt.getType()
                + "', '"
                + newAppt.getStartDateTime().toString()
                + "', '"
                + newAppt.getEndDateTime().toString()
                + "', '"
                + newAppt.getLastUpdate()
                + "', '"
                + newAppt.getCreateDate()
                + "', '"
                + newAppt.getCreatedBy()
                + "', '"
                + newAppt.getLastUpdatedBy()
                + "', "
                + newAppt.getContactID()
                + ", "
                + newAppt.getCustomerID()
                + ", "
                + newAppt.getUserID()
                + " )"
            );

            System.out.println("Just executed the INSERT statement, rs: " + rs);

            
            
            
        }
        catch (SQLException exception){
            // handle any errors
            System.out.println("SQLException: " + exception.getMessage());
            System.out.println("SQLState: " + exception.getSQLState());
            System.out.println("VendorError: " + exception.getErrorCode());
        }
        finally {

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) { }

            }
        }
        
        return rs == 1;
    }

    /**
     * Updates an Appointment entry in the UCertify database with the property
     * values contained in the apptToUpdate object
     *
     * @param apptToUpdate the object containing all of the property values to 
     * update the Appointment entry, including the appointmentID of the Appointment
     * entry to update
     * @return true if the update successfully completes, false if the update
     * does not successfully complete
     */
    @Override
    public Boolean updateAppointment(Appointment apptToUpdate) {
        Statement stmt = null;
        int rs = -1;

        try {
            stmt = appointmentConn.createStatement();
            
            rs = stmt.executeUpdate("UPDATE appointments SET "
                + "Title='" + apptToUpdate.getTitle() + "', "
                + "Description='" + apptToUpdate.getDescription() + "', "
                + "Location='" + apptToUpdate.getLocation() + "', "
                + "Type='" + apptToUpdate.getType() + "', "
                + "Start='" + apptToUpdate.getStartDateTime().toString() + "', "
                + "End='" + apptToUpdate.getEndDateTime().toString() + "', "
                + "Last_Update='" + apptToUpdate.getLastUpdate() + "', "
                + "Last_Updated_By='" + apptToUpdate.getLastUpdatedBy() + "', "
                + "Contact_ID=" + apptToUpdate.getContactID() + ", "
                + "Customer_ID=" + apptToUpdate.getCustomerID() + ", "
                + "User_ID=" + apptToUpdate.getUserID()
                + " WHERE Appointment_ID=" + apptToUpdate.getAppointmentID()
            );

            System.out.println("Just executed the UPDATE statement, rs: " + rs);

            
            
            
        }
        catch (SQLException exception){
            // handle any errors
            System.out.println("SQLException: " + exception.getMessage());
            System.out.println("SQLState: " + exception.getSQLState());
            System.out.println("VendorError: " + exception.getErrorCode());
        }
        finally {

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) { }

            }
        }
        
        return rs == 1;
    
    }

    /**
     * Deletes a single Appointment entry with the appointmentID of apptID
     * from the UCertify database
     *
     * @param apptID the appointmentID of the Appointment entry to delete from
     * the UCertify database
     * @return true if the deletion successfully completes, false if the deletion
     * does not successfully complete
     */
    @Override
    public Boolean deleteAppointment(int apptID) {
        Statement stmt = null;
        int rs = -1;
        
        if(apptID == -1) return false;
        
        try {
            stmt = appointmentConn.createStatement();
            
            rs = stmt.executeUpdate("DELETE FROM appointments WHERE Appointment_ID=" + apptID);
            
            System.out.println("Just executed the delete Appointment with ID: " + apptID + " rs: " + rs);
           
        }
        catch (SQLException exception){
            // handle any errors
            System.out.println("SQLException: " + exception.getMessage());
            System.out.println("SQLState: " + exception.getSQLState());
            System.out.println("VendorError: " + exception.getErrorCode());
        }
        finally {


            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) { }

            }
        }
        
        
        return rs != -1;
    }
    
    /**
     * Gets all Appointment data for all Appointment entries in the UCertify
     * database.
     * 
     * @return an ArrayList of all Appointment objects from the UCertify database
     */
    private static ArrayList<Appointment> getAllAppointmentsFromDB() {
        ArrayList<Appointment> allAppointmentsList = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = appointmentConn.createStatement();
            
            rs = stmt.executeQuery("SELECT * FROM appointments");

            while (rs.next()) {
                
                Appointment newAppt = new Appointment();
                
                newAppt.setAppointmentID(rs.getInt("Appointment_ID"));
                newAppt.setTitle(rs.getString("Title"));
                newAppt.setDescription(rs.getString("Description"));
                newAppt.setLocation(rs.getString("Location"));
                newAppt.setType(rs.getString("Type"));
                newAppt.setStartDateTime(rs.getTimestamp("Start"));
                newAppt.setEndDateTime(rs.getTimestamp("End"));
                newAppt.setCreateDate(rs.getDate("Create_Date"));
                newAppt.setCreatedBy(rs.getString("Created_By"));
                newAppt.setLastUpdate(rs.getTimestamp("Last_Update"));
                newAppt.setLastUpdatedBy(rs.getString("Last_Updated_By"));
                newAppt.setCustomerID(rs.getInt("Customer_ID"));
                newAppt.setUserID(rs.getInt("User_ID"));
                newAppt.setContactID(rs.getInt("Contact_ID"));
                
                allAppointmentsList.add(newAppt);
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
        
        return allAppointmentsList;
    }
    
    /**
     * Deletes all of the Appointment entries from the UCertify database that
     * are related to the Customer with the customerID of the given customerID
     *
     * @param customerID the customerID of the Customer entry in the UCertify
     * database to search for related Appointment entries
     * @return true if the deletion successfully completes, false if the deletion
     * does not successfully complete
     */
    @Override
    public Boolean deleteAllAppointmentsOfCustomer(int customerID) {
        Boolean successfulDeletion = false;
        ArrayList<Integer> apptIDs = new ArrayList();
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = appointmentConn.createStatement();
            
            rs = stmt.executeQuery("SELECT Appointment_ID FROM appointments WHERE Customer_ID = " + customerID);

            while (rs.next()) {
                apptIDs.add(rs.getInt("Appointment_ID"));
            }
            
            // Now have all the appointments of the customer
            // Let's delete them ...
            for(int i = 0; i < apptIDs.size(); i++) {
                int deleteRS = stmt.executeUpdate("DELETE FROM appointments WHERE Appointment_ID=" + apptIDs.get(i));
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
        
        return apptIDs.isEmpty();
        
    }
    
}
