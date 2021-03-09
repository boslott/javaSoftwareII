
package com.boslott.DataAccess;

import com.boslott.model.Division;
import com.boslott.model.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * A UCertdbUserDAO object is a UserDAO object used to interact with User
 * data from the UCertify database data source
 *
 * @author Steven Slott
 */
public class UCertdbUserDAO implements UserDAO {

    /**
     * A connection to the UCertify database or User data
     */
    private static Connection userConn;
    
    /**
     * A User object containing the property values of the User currently
     * logged into the application
     */
    private static User currentUser;
    
    /**
     * Constructs a UCertdbUserDAO object and gets a connection to the UCertify
     * database
     *
     * @throws SQLException if there is a database access error
     */
    public UCertdbUserDAO() throws SQLException {
        userConn = UCertdbDAOFactory.getConnectionInstance();
    }

    /**
     * Calls the private class method to get a single User entry from the
     * UCertify database with the userName property value of name
     *
     * @param name the userName property value of the User entry to search for
     * @return the User object containing the property values of the User entry
     * with the userName name
     */
    @Override
    public synchronized User findUser(String name) {
        User user = this.findUserFromDB(name);
        return user;
        
    }

    /**
     * A method that searches the UCertify database for a User with the userName
     * property value of name. If found, the User entry password is checked against
     * the String pass. If the User entry password equals the String pass, 
     * true is returned
     *
     * @param name the inputted userName to search the UCertify database for
     * @param pass the inputted password to compare to the database entry password
     * @return true if the passwords are equal, false if the passwords are 
     * not equal
     */
    @Override
    public Boolean isCorrectPassword(String name, String pass) {
        if(name != null) {
            User user = this.findUser(name);
            System.out.println("Name parameter at login: " + name);
            System.out.println("User at login: " + user);
            
            if(user != null) {
                if(user.getUserName().equals(name)) {
                    // Username is correct. Is the password?
                    return user.getPassword().equals(pass); // Correct credentials ...
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        else return false;
    }
    
    /**
     * A private class method to get a single User entry from the UCertify
     * database with the userName property value of name
     * 
     * @param name the userName property value to search the UCertify database for
     * @return the User object containing the User data for the User entry with
     * the userName property value name
     */
    private User findUserFromDB(String name) {
        User user = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            
            stmt = userConn.createStatement();
            
            rs = stmt.executeQuery("SELECT * FROM users WHERE User_Name = '" + name + "'");

            while (rs.next()) {
                
                user = new User();
                
                user.setUserID(rs.getInt("User_ID"));
                user.setUserName(rs.getString("User_Name"));
                user.setPassword(rs.getString("Password"));
                user.setCreateDate(rs.getDate("Create_Date"));
                user.setCreatedBy(rs.getString("Created_By"));
                user.setLastUpdate(rs.getTimestamp("Last_Update"));
                user.setLastUpdatedBy(rs.getString("Last_Updated_By"));
            } 
            
//            System.out.println("Current user info: " + user.getUserName() + ", userID: " + user.getUserID() + ", password is correct: " + this.isCorrectPassword(name, user.getPassword()));
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
        
        return user;
    }
    
    /**
     * Gets all User data from the UCertify database 
     *
     * @return an ArrayList of all User objects from the UCertify database
     */
    @Override
    public ArrayList<User> findAllUsers() {
        ArrayList<User> users = new ArrayList();
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            
            stmt = userConn.createStatement();
            
            rs = stmt.executeQuery("SELECT * FROM users");

            while (rs.next()) {
                
                User user = new User();
                
                user.setUserID(rs.getInt("User_ID"));
                user.setUserName(rs.getString("User_Name"));
                user.setPassword(rs.getString("Password"));
                user.setCreateDate(rs.getDate("Create_Date"));
                user.setCreatedBy(rs.getString("Created_By"));
                user.setLastUpdate(rs.getTimestamp("Last_Update"));
                user.setLastUpdatedBy(rs.getString("Last_Updated_By"));
                
                users.add(user);
            } 
            
//            System.out.println("Current user info: " + user.getUserName() + ", userID: " + user.getUserID() + ", password is correct: " + this.isCorrectPassword(name, user.getPassword()));
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
        
        return users;
    }
    
}
