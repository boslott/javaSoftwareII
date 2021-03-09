
package com.boslott.DataAccess;

import com.boslott.model.Customer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * A UCertdbCustomerDAO object is a CustomerDAO object used to interact with
 * Customer data from a UCertify database data source
 *
 * @author Steven Slott
 */
public class UCertdbCustomerDAO implements CustomerDAO {
    
    /**
     * A connection to the UCertify database for Customer data
     */
    private static Connection customerConn;
    
    /**
     * The local cache of Customer objects
     */
    private static ArrayList<Customer> customers;

    /**
     * Constructs an UCertdbCustomerDAO object and gets a connection to the
     * UCertify database data source
     *
     * @throws SQLException if there is a database access error
     */
    public UCertdbCustomerDAO() throws SQLException {
        customerConn = UCertdbDAOFactory.getConnectionInstance();
    }
    
    /**
     * Gets a single Customer entry from the UCertify database with the 
     * customerID property value of the given id
     *
     * @param id the customerID of the Customer entry requested from the
     * UCertify database
     * @return the Customer object containing the data of the Customer entry
     * requested with the customerID of id
     */
    @Override
    public Customer findCustomer(int id) {
        Customer cust = new Customer();
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = customerConn.createStatement();
            
            rs = stmt.executeQuery("SELECT * FROM customers WHERE Customer_ID = " + id);

            while (rs.next()) {
                cust.setCustomerID(rs.getInt("Customer_ID"));
                cust.setCustomerName(rs.getString("Customer_Name"));
                cust.setAddress(rs.getString("Address"));
                cust.setPostalCode(rs.getString("Postal_Code"));
                cust.setPhone(rs.getString("Phone"));
                cust.setCreateDate(rs.getDate("Create_Date"));
                cust.setCreatedBy(rs.getString("Created_By"));
                cust.setLastUpdate(rs.getTimestamp("Last_Update"));
                cust.setLastUpdatedBy(rs.getString("Last_Updated_By"));
                cust.setDivisionID(rs.getInt("Division_ID"));
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
        
        return cust;
    }
    
    /**
     * Gets all Customer objects from the local ArrayList or calls the private
     * class method to get all Customer objects from the UCertify database. If
     * refetch is true or if the local ArrayList of Customer objects is null,
     * the method calls to the UCertdb again. If retch is false, the method returns
     * the locally cached results.
     *
     * @param refetch whether to call the UCertify database again or not
     * @return an ArrayList of all Customer Objects
     */
    @Override
    public ArrayList<Customer> findAllCustomers(Boolean refetch) {
        if(customers == null || refetch == true) {
            customers = getAllCustomersFromDB();
        }
        return customers;
    }
    
    /**
     * A private class method to call the UCertify database and get all 
     * Customer data.
     * @return an ArrayList of all Customer objects from the UCertify database
     */
    private static ArrayList<Customer> getAllCustomersFromDB() {
        ArrayList<Customer> allCustomersList = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = customerConn.createStatement();
            
            rs = stmt.executeQuery("SELECT * FROM customers");

            while (rs.next()) {
                
                Customer newCust = new Customer();
                
                newCust.setCustomerID(rs.getInt("Customer_ID"));
                newCust.setCustomerName(rs.getString("Customer_Name"));
                newCust.setAddress(rs.getString("Address"));
                newCust.setPostalCode(rs.getString("Postal_Code"));
                newCust.setPhone(rs.getString("Phone"));
                newCust.setCreateDate(rs.getDate("Create_Date"));
                newCust.setCreatedBy(rs.getString("Created_By"));
                newCust.setLastUpdate(rs.getTimestamp("Last_Update"));
                newCust.setLastUpdatedBy(rs.getString("Last_Updated_By"));
                newCust.setDivisionID(rs.getInt("Division_ID"));
                
                allCustomersList.add(newCust);
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
        
        return allCustomersList;
    };
    
    /**
     * Inserts a new Customer entry into the UCertify database with the property
     * values of those contained in the newCustomer object (except the customerID)
     *
     * @param newCustomer a Customer object with the property values for the new
     * Customer entry being inserted into the UCertify database
     * @return true if the insertion successfully completes, false if the 
     * insertion does not successfully complete
     */
    @Override
    public Boolean insertCustomer(Customer newCustomer) {
        Statement stmt = null;
        int rs = -1;
        
        try {
            stmt = customerConn.createStatement();
            
            rs = stmt.executeUpdate("ALTER TABLE customers AUTO_INCREMENT=3");
            
            System.out.println("Just executed the add auto_incr, rs: " + rs);
            
            rs = stmt.executeUpdate("INSERT INTO customers"
                + "(Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID, Last_Update, Create_Date, Created_By, Last_Updated_By)"
                    
                + "VALUES ("
                + null + ", '"
                + newCustomer.getCustomerName()
                + "', '"
                + newCustomer.getAddress()
                + "', '"
                + newCustomer.getPostalCode()
                + "', '"
                + newCustomer.getPhone()
                + "', "
                + newCustomer.getDivisionID()
                + ", '"
                + newCustomer.getLastUpdate()
                + "', '"
                + new java.sql.Date(newCustomer.getCreateDate().getTime())
                + "', '"
                + newCustomer.getCreatedBy()
                + "', '"
                + newCustomer.getLastUpdatedBy()
                + "' )"
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
        
        
        return rs != -1;
    };
    
    /**
     * Updates a Customer entry in the UCertify database with the property values
     * of those contained in the custToUpdate object
     *
     * @param custToUpdate the Customer object containing the property values with
     * which to update the Customer entry, including the customerID of the requested
     * Customer entry
     * @return true if the update successfully completes, false if the update
     * does not successfully complete
     */
    @Override
    public Boolean updateCustomer(Customer custToUpdate) {
        Statement stmt = null;
        int rs = -1;
        
        try {
            stmt = customerConn.createStatement();
            
            rs = stmt.executeUpdate("UPDATE customers SET "
                + "Customer_Name='" + custToUpdate.getCustomerName() + "', "
                + "Address='" + custToUpdate.getAddress() + "', "
                + "Postal_Code='" + custToUpdate.getPostalCode() + "', "
                + "Phone='" + custToUpdate.getPhone() + "', "
                + "Division_ID=" + custToUpdate.getDivisionID() + ", "
                + "Last_Update='" + new Timestamp(new Date().getTime()) + "', "
                + "Last_Updated_By='" + custToUpdate.getLastUpdatedBy() + "' "
                + "WHERE Customer_ID=" + custToUpdate.getCustomerID()
            );

            System.out.println("Just executed the Update statement, rs: " + rs);

            
            
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
    };
    
    /**
     * A Customer entry may not be deleted if it has Appointment entries related 
     * to it. This method calls to the UCertify database and requests all Appointment
     * entries for the Customer entry with the customerID IDToDelete. If the Customer
     * entry has Appointment entries related to, the method returns false.
     *
     * @param IDToDelete the customerID of the Customer entry with which to search the
     * UCertify database for related Appointment entries
     * @return true if there are not any related Appointment entries to the Customer
     * entry, false if there are related Appointment entries
     */
    @Override
    public Boolean isAllowedToDelete(int IDToDelete) {
        ArrayList<Integer> apptIDs = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = customerConn.createStatement();
            rs = stmt.executeQuery("SELECT Appointment_ID FROM appointments WHERE Customer_ID = " + IDToDelete);
            
            while (rs.next()) {
                
                // Now have a list of all the appointmentIDs belonging to this one customer
                // If there are any appointmentIDs, the customer cannot be deleted
                apptIDs.add(rs.getInt("Appointment_ID"));
                System.out.println("ApptID just added to the list, so Customer cannot be deleted");
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
        
        System.out.println("ApptIDs List size ? " + apptIDs.size());
        return apptIDs.size() <= 0;
    }
    
    /**
     * Deletes a Customer entry from the UCertify database that has the customerID
     * of ID
     *
     * @param ID the customerID of the Customer entry to delete from the
     * UCertify database
     * @return true if the deletion successfully completes, false if the
     * deletion does not successfully complete
     */
    @Override
    public Boolean deleteCustomer(int ID) {
        Statement stmt = null;
        int rs = -1;
        
        if(ID == -1) return false;
        
        try {
            stmt = customerConn.createStatement();
            
            rs = stmt.executeUpdate("DELETE FROM customers WHERE Customer_ID='" + ID + "'");
            
            System.out.println("Just executed the deleteUser with ID: " + ID + " rs: " + rs);
           
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
    };

    /**
     * Gets a single Customer entry from the UCertify database that has the 
     * customerName property value of the given String name
     *
     * @param name the customerName property value of the Customer entry
     * being requested from the UCertify database
     * @return the Customer object containing the data for the Customer
     * entry in the UCertify database with the customerName of name
     */
    @Override
    public Customer findCustomerByName(String name) {
        Customer cust = new Customer();
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = customerConn.createStatement();
            System.out.println("UCertCustomerDAO is about to findCustomerByName, customer name: " + name);
            
            rs = stmt.executeQuery("SELECT * FROM customers WHERE Customer_Name = '" + name + "'");

            while (rs.next()) {
                cust.setCustomerID(rs.getInt("Customer_ID"));
                cust.setCustomerName(rs.getString("Customer_Name"));
                cust.setAddress(rs.getString("Address"));
                cust.setPostalCode(rs.getString("Postal_Code"));
                cust.setPhone(rs.getString("Phone"));
                cust.setCreateDate(rs.getDate("Create_Date"));
                cust.setCreatedBy(rs.getString("Created_By"));
                cust.setLastUpdate(rs.getTimestamp("Last_Update"));
                cust.setLastUpdatedBy(rs.getString("Last_Updated_By"));
                cust.setDivisionID(rs.getInt("Division_ID"));
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
        
        return cust;
    }
    
}
