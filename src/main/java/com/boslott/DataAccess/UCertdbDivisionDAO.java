
package com.boslott.DataAccess;

import com.boslott.model.Country;
import com.boslott.model.Division;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * An UCertdbDivisionDAO object is a DivisionDAO object used to interact with
 * Division data from the UCertify database data source
 *
 * @author Steven Slott
 */
public class UCertdbDivisionDAO implements DivisionDAO {
    
    /**
     * A connection to the UCertify database for Division data
     */
    private static Connection divisionConn;
    
    /**
     * The local cache of Division objects
     */
    private static ArrayList<Division> divisions;
    
    /**
     * Constructs a new UCertdbDivisionDAO object and gets a connection to the
     * UCertify database
     *
     * @throws SQLException if there is a database access error
     */
    public UCertdbDivisionDAO() throws SQLException {
        divisionConn = UCertdbDAOFactory.getConnectionInstance();
        System.out.println("DivisionDAO has obtained a connection");
    }

    /**
     * Gets all Division objects from the local ArrayList or calls to the private
     * class method to get all Division data from the UCertify database
     *
     * @return an ArrayList of all Division objects
     */
    @Override
    public ArrayList<Division> findAllDivisions() {
        if(divisions == null) {
            divisions = getAllDivisionsFromDB();
        }
        return divisions;
    }
    
    /**
     * A private class method that makes a call to the UCertify database and
     * gets all Division data
     * 
     * @return an ArrayList of all Division objects from the UCertify database
     */
    private static ArrayList<Division> getAllDivisionsFromDB() {
        ArrayList<Division> allDivisionsList = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = divisionConn.createStatement();
            
            rs = stmt.executeQuery("SELECT * FROM first_level_divisions");

            while (rs.next()) {
                
                Division newDiv = new Division();
                
                newDiv.setDivisionID(rs.getInt("Division_ID"));
                newDiv.setDivision(rs.getString("Division"));
                newDiv.setCreateDate(rs.getDate("Create_Date"));
                newDiv.setCreatedBy(rs.getString("Created_By"));
                newDiv.setLastUpdate(rs.getTimestamp("Last_Update"));
                newDiv.setLastUpdatedBy(rs.getString("Last_Updated_By"));
                newDiv.setCountryID(rs.getInt("Country_ID"));
                
                allDivisionsList.add(newDiv);
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
        
        return allDivisionsList;
    }
    
}
