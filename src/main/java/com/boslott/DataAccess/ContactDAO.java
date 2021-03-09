
package com.boslott.DataAccess;

import com.boslott.model.Contact;
import java.util.ArrayList;

/**
 * A ContactDAO is a Data Access Object with the methods needed to interact with
 * Contact data
 *
 * @author Steven Slott
 */
public interface ContactDAO {
    
    /**
     * Gets all Contacts from the data source
     *
     * @return an ArrayList of all Contact objects
     */
    public ArrayList<Contact> findAllContacts();

    /**
     * Gets the data from the data source for one Contact with the contactID of id
     *
     * @param id the contactID of the Contact to get data for
     * @return the Contact object with the data for the Contact with contactID contactID
     */
    public Contact findContact(int id);

    /**
     * Gets the data from the data source for one Contact with the contactName of name
     *
     * @param name the contactName of the Contact to get data for
     * @return the Contact object with the data for the Contact with the contactName name
     */
    public Contact findContactByName(String name);
    
}
