
package com.boslott.DataAccess;

import com.boslott.model.Division;
import java.util.ArrayList;

/**
 * A DivsionDAO is a Data Access Object with the needed methods to interact
 * with First-Level Division data
 *
 * @author Steven Slott
 */
public interface DivisionDAO {
    
    /**
     * Gets all Divisions from the data source
     *
     * @return an ArrayList of all Division objects from the data source
     */
    public ArrayList<Division> findAllDivisions();
    
}
