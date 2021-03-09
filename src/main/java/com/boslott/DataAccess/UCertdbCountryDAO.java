
package com.boslott.DataAccess;

import com.boslott.model.Country;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * A UCertdbCountryDAO object is a Country Data Access Object used to interact
 * with Country data from a UCertify database data source
 *
 * @author Steven Slott
 */
public class UCertdbCountryDAO implements CountryDAO {
    
    /**
     * A connection to the UCertify database for Connection data
     */
    private static Connection countryConn;
    
    /**
     * The local cache of Country objects
     */
    private static ArrayList<Country> countries;
    
    /**
     * Constructs an UCertifydbCountryDAO object and gets a connection to the
     * UCertify database data source
     *
     * @throws SQLException if there is a database access error
     */
    public UCertdbCountryDAO() throws SQLException {
        countryConn = UCertdbDAOFactory.getConnectionInstance();
        System.out.println("CountryDAO has obtained a connection");
    }

    /**
     * Gets all Country data from the local ArrayList of Country
     * objects or calls the private class method to get data from the UCertify database
     *
     * @return an ArrayList of all Country objects
     */
    @Override
    public ArrayList<Country> findAllCountries() {
        if(countries == null) {
            countries = getAllCountriesFromDB();
        }
        return countries;
    }
    
    /**
     * A private class method that makes a call to the UCertify database and
     * gets all Country data
     * 
     * @return an ArrayList of all Country objects from the UCertify database
     * 
     */
    private static ArrayList<Country> getAllCountriesFromDB() {
        ArrayList<Country> allCountriesList = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = countryConn.createStatement();
            
            rs = stmt.executeQuery("SELECT * FROM countries");

            while (rs.next()) {
                
                Country newCountry = new Country();
                
                newCountry.setCountryID(rs.getInt("Country_ID"));
                newCountry.setCountry(rs.getString("Country"));
                newCountry.setCreateDate(rs.getDate("Create_Date"));
                newCountry.setCreatedBy(rs.getString("Created_By"));
                newCountry.setLastUpdate(rs.getTimestamp("Last_Update"));
                newCountry.setLastUpdatedBy(rs.getString("Last_Updated_By"));
                
                allCountriesList.add(newCountry);
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
        
        return allCountriesList;
    }
    
}
