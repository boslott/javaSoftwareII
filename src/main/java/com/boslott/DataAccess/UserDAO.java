
package com.boslott.DataAccess;

import com.boslott.model.User;
import java.util.ArrayList;

/**
 * A UserDAO is a Data Access Object with the needed methods to interact with
 * User data
 *
 * @author Steven Slott
 */
public interface UserDAO {
    
    /**
     * Gets the data from the data source for the User with the userName name
     *
     * @param name the userName of the User to search the data source for
     * @return the User object with the data of the User with userName name
     */
    public User findUser(String name);

    /**
     * Gets all Users from the data source
     *
     * @return an ArrayList of all User objects from the data source
     */
    public ArrayList<User> findAllUsers();

    /**
     * Verifies if the user-inputted password is the same password stored in
     * the data source related to the User with userName name
     *
     * @param name the userName of the User to compare passwords
     * @param pass the user-inputted password to compare to the data source password
     * @return true if the passwords match, false if the passwords are not the same
     */
    public Boolean isCorrectPassword(String name, String pass);
    
}
