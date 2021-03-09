
package com.boslott.DataAccess;

import com.boslott.model.Appointment;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * An AppointmentDAO is a Data Access Object with the methods needed to interact
 * with Appointment data
 *
 * @author Steven Slott
 */
public interface AppointmentDAO {
    
    /**
     * Gets all Appointments from the data source. If this DAO chooses to cache
     * the results, the refetch parameters signals whether to send the cached
     * data or to call to the data source again
     *
     * @param refetch if true, this method will call to the data source to get
     * data; if false, this method will return locally cached data or will call 
     * to the data source again (if this method chooses to not cache results)
     * @return an ArrayList of all Appointments from the data source
     */
    public ArrayList<Appointment> findAllAppointments(Boolean refetch);

    /**
     * Gets all Appointments from the data source related to one customer,
     * data source searched based on customer name
     *
     * @param customerName the name of the customer to search for related
     * Appointments
     * @return an ArrayList of Appointments related to the customer with
     * the name customerName
     */
    public ArrayList<Appointment> findAllCustomerAppointments(String customerName);


    /**
     * Inserts a new Appointment entry in the data source, populated with
     * data from the newAppt object
     *
     * @param newAppt the object containing all the new Appointment property values
     * (except the appointmentID)
     * @return true if the insertion successfully completes, false if the 
     * insertion does not successfully complete
     */
    public Boolean insertAppointment(Appointment newAppt);

    /**
     * Updates an existing Appointment entry in the data source, updated with
     * data from the apptToUpdate object
     *
     * @param apptToUpdate the object containing all the data needed to update
     * the property values of the Appointment, including the ID of the Appointment
     * of which to update
     * @return true if the update successfully completes, false if the update
     * does not complete successfully
     */
    public Boolean updateAppointment(Appointment apptToUpdate);

    /**
     * Deletes an existing Appointment, with the appointmentID apptID, from the data source
     *
     * @param apptID the appointmentID of the Appointment to delete
     * @return true if the deletion successfully completes, false if the deletion
     * does not successfully complete
     */
    public Boolean deleteAppointment(int apptID);

    /**
     * Deletes all the Appointments of the Customer with customerID of customerID
     *
     * @param customerID the customerID of the Customer to delete all related Appointments
     * @return true if the deletion successfully completes, false if the deletion
     * does not successfully complete
     */
    public Boolean deleteAllAppointmentsOfCustomer(int customerID);
    
}
