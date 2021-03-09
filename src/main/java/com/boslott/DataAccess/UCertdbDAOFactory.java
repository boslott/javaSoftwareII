
package com.boslott.DataAccess;

import java.sql.*;
import java.util.Properties;

/**
 * A UCertdbDAOFactory object is a factory object used to create
 * UCertdbDAO objects. It also holds the connection to the UCertify database
 * that all UCertDAO objects use to obtain data with
 *
 * @author Steven Slott
 */
public class UCertdbDAOFactory {
    
    /**
     * The server name to connect to hosting the UCertify database
     */
    private static final String SERVER_NAME = "wgudb.ucertify.com";
    
    /**
     * The port to connect to on the UCertify database server
     */
    private static final int PORT = 3306;
    
    /**
     * The UCertify database name to connect to
     */
    private static final String DATABASE_NAME = "WJ066XQ";
    
    /**
     * The username required to connect to the UCertify database
     */
    private static final String USERNAME = "U066XQ";
    
    /**
     * The password required to connect to the UCertify database
     */
    private static final String PASSWORD = "53688692266";
    
    /**
     * The connection to the UCertify database accessed by all UCertdbDAO objects
     */
    private static Connection dbConnection;
    
    /**
     * The private class method used to get the connection to the UCertify
     * database using
     * 
     * @return the Connection object obtained by making a connection to the 
     * UCertify database data source
     * @throws SQLException if there is a database access error
     */
    private static Connection getConnection() throws SQLException {
        
        try {
            // The newInstance() call is a work around for some
            // broken Java implementations

            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            // handle the error
        }

        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", USERNAME);
        connectionProps.put("password", PASSWORD);

        conn = DriverManager.getConnection(
            "jdbc:mysql://" +
            SERVER_NAME +
            ":" + PORT + "/" + DATABASE_NAME,
            connectionProps
        );
        
        System.out.println("Connected to database ... conn: " + conn.toString());
        return conn;
    }
    
    /**
     * The method available to all UCertdbDAO objects used to obtain the 
     * singleton Connection object to the UCertify database data source
     *
     * @return the Connection object to the UCertify database data source
     * @throws SQLException if there is a database access error
     */
    public static Connection getConnectionInstance() throws SQLException {
        if(dbConnection == null) {
            dbConnection = getConnection();
        }
        return dbConnection;
    }
    
    /**
     * Gets a new UCertdbCustomerDAO object to use to interact with Customer data
     * from the UCertify database data source
     *
     * @return an UCertdbCustomerDAO object
     * @throws SQLException if the UCertdbCustomerDAO object's construction method creates
     * a database access error
     */
    public UCertdbCustomerDAO getCustomerDAO() throws SQLException {
        return new UCertdbCustomerDAO();
    }
    
    /**
     * Gets a new UCertdbCountryDAO object to use to interact with Country data
     * from the UCertify database data source
     *
     * @return an UCertifydbCountryDAO object
     * @throws SQLException if the UCertifydbCountryDAO object's construction method creates
     * a database access error
     */
    public UCertdbCountryDAO getCountryDAO() throws SQLException {
        return new UCertdbCountryDAO();
    }
    
    /**
     * Gets a new UCertdbUserDAO object to use to interact with User data from
     * the UCertify database data source
     *
     * @return an UCertifydbUserDAO object
     * @throws SQLException if the UCertdbUserDAO object's construction method creates
     * a database access error
     */
    public UCertdbUserDAO getUserDAO() throws SQLException {
        return new UCertdbUserDAO();
    }
    
    /**
     * Gets a new UCertdbAppointmentDAO object used to interact with Appointment
     * data from the UCertify database data source
     *
     * @return an UCertifydbAppointmentDAO object
     * @throws SQLException if the UCertdbAppointmentDAO object's constructor
     * method creates a database access error
     */
    public UCertdbAppointmentDAO getAppointmentDAO() throws SQLException {
        return new UCertdbAppointmentDAO();
    }
    
    /**
     * Gets a new UCertdbDivisionDAO object used to interact with Division data
     * from the UCertify database data source
     *
     * @return an UCertdbDivisionDAO object
     * @throws SQLException if the UCertdbDivisionDAO object's constructor method
     * creates a database access error
     */
    public UCertdbDivisionDAO getDivisionDAO() throws SQLException {
        return new UCertdbDivisionDAO();
    }
    
    /**
     * Gets a new UCertdbContactDAO object used to interact with Contact data
     * from the UCertify database data source
     *
     * @return an UCertdbContactDAO object
     * @throws SQLException if the UCertdbContactDAO object's constructor method
     * creates a database access error
     */
    public UCertdbContactDAO getContactDAO() throws SQLException {
        return new UCertdbContactDAO();
    }
    
    
}
