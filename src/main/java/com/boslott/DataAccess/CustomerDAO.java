
package com.boslott.DataAccess;

import com.boslott.model.Customer;
import java.util.ArrayList;

/**
 * A CustomerDAO is a Data Access Object with all the methods needed to interact
 * with Customer data
 *
 * @author Steven Slott
 */
public interface CustomerDAO {
    
    /**
     * Gets all Customer data from the data source. If the DAO chooses to cache
     * the results locally, the refetch parameter can be used to signal to the method
     * whether to return the cached data or to call to the data source again
     *
     * @param refetch if true, the method will call to the data source;
     * if false, the method will return cached data
     * @return an ArrayList of all Customer objects from the data source
     */
    public ArrayList<Customer> findAllCustomers(Boolean refetch);

    /**
     * Gets the data from the data source for one Customer with the customerID id
     *
     * @param id the customerID of the Customer to search the data source for
     * @return a Customer object with the data for the Customer with customerID id
     */
    public Customer findCustomer(int id);

    /**
     * Gets the data from the data source for one Customer with the customerName name
     *
     * @param name the customerName of the Customer to search the data source for
     * @return the Customer object with the data for the Customer with customerName name
     */
    public Customer findCustomerByName(String name);

    /**
     * Inserts a new Customer entry into the data source with data from the
     * newCustomer object
     *
     * @param newCustomer the object containing the property values for the 
     * new Customer entry
     * @return true if the insertion successfully completes, false if the 
     * insertion does not successfully complete
     */
    public Boolean insertCustomer(Customer newCustomer);

    /**
     * Updates an existing Customer entry in the data source, updated with data
     * from the custToUpdate object
     *
     * @param custToUpdate the object containing the property values of the Customer
     * entry to update, including the customerID of the Customer to update
     * @return true if the update successfully completes, false if the update
     * does not successfully completes
     */
    public Boolean updateCustomer(Customer custToUpdate);

    /**
     * Deletes a Customer entry from the data source with the customerID ID
     *
     * @param ID the customerID of the Customer entry to delete from the data source
     * @return true if the deletion successfully completes, false if the 
     * deletion does not successfully complete
     */
    public Boolean deleteCustomer(int ID);

    /**
     * A Customer may not be deleted if there are Appointment entries related to the 
     * Customer. This method is used to search the data source for the Customer
     * with customerID IDToDelete and return whether or not a Customer may be deleted,
     * in other words, whether the Customer entry has Appointments related to it or not
     *
     * @param IDToDelete the customerID of the Customer to search the data source for
     * @return true if the Customer does not have any related Appointments,
     * false if the Customer does have related Appointments
     */
    public Boolean isAllowedToDelete(int IDToDelete);
    
}
