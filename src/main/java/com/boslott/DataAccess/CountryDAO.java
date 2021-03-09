
package com.boslott.DataAccess;

import com.boslott.model.Country;
import java.util.ArrayList;

/**
 * A CountryDAO is a Data Access Object with the methods needed to interact 
 * with Country data
 *
 * @author Steven Slott
 */
public interface CountryDAO {
    
    /**
     * Gets all Countries from the data source
     *
     * @return an ArrayList of all Country objects from the data source
     */
    public ArrayList<Country> findAllCountries();
    
    
}
