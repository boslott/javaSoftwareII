
package com.boslott.controller;

import com.boslott.DataAccess.UCertdbCountryDAO;
import com.boslott.DataAccess.UCertdbCustomerDAO;
import com.boslott.DataAccess.UCertdbDivisionDAO;
import com.boslott.DataAccess.UCertdbUserDAO;
import com.boslott.model.Country;
import com.boslott.model.Customer;
import com.boslott.model.Division;
import com.boslott.model.User;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class for the Customers Update Scene
 *
 * @author Steven Slott
 */
public class CustomersUpdateSceneController implements Initializable {
    
    @FXML TextField customerNameSearchField;
    @FXML ComboBox customerNameSearchComboBox;
    @FXML TextField custModIDField;
    @FXML TextField custModNameField;
    @FXML TextField custModAddressField;
    @FXML TextField custModPostalCodeField;
    @FXML TextField custModPhoneField;
    @FXML ComboBox custModCountryComboBox;
    @FXML ComboBox custModDivisionComboBox;
    @FXML Button clearButton;
    @FXML Button createCustomerButton;
    @FXML Button updateCustomerButton;
    @FXML Button deleteCustomerButton;
    @FXML Button cancelCustModButton;
    @FXML Label errorMessageLabel;
    
    // Data Access Implementation Objects
    private UCertdbCustomerDAO customerDAO;
    private UCertdbCountryDAO countryDAO;
    private UCertdbDivisionDAO divisionDAO;
    private UCertdbUserDAO userDAO;
    
    private ArrayList<Customer> filteredCustomerList;
    private ArrayList<Country> countries;
    private ArrayList<Division> divisions;
    private Customer selectedCustomer;
    private Country selectedCountry;
    

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.customerDAO = App.getUCertdbDAOFactory().getCustomerDAO();
            this.countryDAO = App.getUCertdbDAOFactory().getCountryDAO();
            this.divisionDAO = App.getUCertdbDAOFactory().getDivisionDAO();
            this.userDAO = App.getUCertdbDAOFactory().getUserDAO();
        } catch (SQLException ex) {
            Logger.getLogger(CustomersUpdateSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.errorMessageLabel.setText("");
        this.filteredCustomerList = customerDAO.findAllCustomers(false);
        this.countries = countryDAO.findAllCountries();
        this.divisions = divisionDAO.findAllDivisions();
        this.populateCustomerNameSearchComboBox();
        this.populateCustModCountryComboBox();
        this.custModIDField.setDisable(true);
        
        // Change the focus when the Scene is shown to the ComboBox
        this.customerNameSearchField.setFocusTraversable(false);
        this.customerNameSearchComboBox.setFocusTraversable(true);
    }    
    
    /**
     * Filters the list of all Customers based on the user inputted value
     * in the customerNameSearchField
     */
    public void filterCustomerChoices() {
        ArrayList<Customer> fullList = new ArrayList<>(customerDAO.findAllCustomers(false));
        ArrayList<Customer> tmpList = new ArrayList<>();
        String searchString = this.customerNameSearchField.getText().toLowerCase();
        
        if(searchString.isBlank()) {
            tmpList.addAll(customerDAO.findAllCustomers(false));
        } else {
            fullList.forEach(customer -> {
                if(customer.getCustomerName().toLowerCase().contains(searchString)) {
                    tmpList.add(customer);
                }
            });
        }
        
        this.filteredCustomerList = tmpList;
        this.populateCustomerNameSearchComboBox();
    }
    
    /**
     * Populates the Customer Name Field with the user selected name as well as
     * sets the selectedCustomer object property values to that of the user selected
     * Customer entry
     */
    public void selectThisCustomer() {
        String selectedCustomerName = "";
        
        this.setErrorMessage("");
        if(this.customerNameSearchComboBox.getValue() != null) {
            selectedCustomerName = this.customerNameSearchComboBox.getValue().toString();
        }
        this.customerNameSearchField.setText(selectedCustomerName);
        this.populateSelectedCustomer(selectedCustomerName);
    }
    
    /**
     * Calls the method to populate Division names when the user selects
     * a Country name
     */
    public void selectThisCountry() {
        this.custModDivisionComboBox.setItems(null);
        String selectedCountryName = "";
        if(this.custModCountryComboBox.getValue() != null) {
            selectedCountryName = this.custModCountryComboBox.getValue().toString();
        }
        this.populateSelectedCountryDivisions(selectedCountryName);
    }
    
    /**
     * Populates the First-Level Division data in the divisionComboBox with
     * Division names for the user selected country
     * 
     * @param countryName the countryName to use for Division data searching
     */
    public void populateSelectedCountryDivisions(String countryName) {
        int countryID;
        
        // Grab all the country's data
        for(int i = 0; i < this.countries.size(); i++) {
            if(this.countries.get(i).getCountry().equals(countryName)) {
                this.selectedCountry = this.countries.get(i);
            }
        }
        
        // Populate the first level divisions of the selected country
        this.populateCustModDivisionComboBox();
        
    }
    
    /**
     * Clears all input controls and the stored selectedCustomer value
     */
    public void clear() {
        this.clearAllInputs();
        this.selectedCustomer = null;
    }
    
    /**
     * This method directs the process of creating a new Customer entry in the
     * UCertify database data source. This method ensures validation of the user
     * inputs, communicates with the data source with a DAO, and updates the UI
     * display message accordingly
     *
     * @return true if the insertion successfully completes, false if the 
     * insertion does not successfully complete
     * @throws IOException if there is an FXML loading error
     * @throws SQLException if there is a data access error
     */
    public Boolean createCustomer() throws IOException, SQLException {
        // This must be a new customer ... cannot have an ID
        String input = this.custModIDField.getText();
        Boolean hasNoID = Validation.hasNothing(this.custModIDField);
        Boolean successfulCreation = false;
        
        if(hasNoID) {
            this.finalDataGrab();
            if(this.allFieldsHaveValue()) {
                if(this.selectedCustomer.getLastUpdate() != null) {
                    successfulCreation = customerDAO.insertCustomer(this.selectedCustomer);

                    this.cancelCustMod("New Customer " + this.selectedCustomer.getCustomerName() + " has been created!");
                    return successfulCreation;
                } else {
                    return false;
                }
            } else {
                this.setErrorMessage("All Fields Must Have A Value To Proceed");
                return successfulCreation;
            }
        } else {
            this.setErrorMessage("Wrong Button ... This is an update, not a new Customer");
            return successfulCreation;
        }
    }
    
    /**
     * A validation method to checks whether or not all inputs have a value
     * 
     * @return true if all field have a value, false if all field do not have a value
     */
    private Boolean allFieldsHaveValue() {
        Boolean hasName = !Validation.hasNothing(this.custModNameField);
        Boolean hasAddress = !Validation.hasNothing(this.custModAddressField);
        Boolean hasPostalCode = !Validation.hasNothing(this.custModPostalCodeField);
        Boolean hasPhone = !Validation.hasNothing(this.custModPhoneField);
        Boolean hasDivisionID = !Validation.hasNothing(this.custModDivisionComboBox);
        
        return hasName && hasAddress && hasPostalCode && hasPhone && hasDivisionID;
    }
    
    /**
     * Sets the text property of the errorMessageLabel to the provided message
     * String value
     * @param message the value to set the text property to 
     */
    private void setErrorMessage(String message) {
        this.errorMessageLabel.setText(message);
    }
    
    /**
     * This method directs the process of updating a Customer entry in the
     * UCertify database data source. This method ensures validation of the user
     * input, interacts with the Customer data through a DAO, and sets UI messages
     * accordingly
     *
     * @return true if the update successfully completes, false if the update
     * does not successfully complete
     * @throws IOException if there is an FXML loading error
     * @throws SQLException if there is a database access error
     */
    public Boolean updateCustomer() throws IOException, SQLException {
        Boolean notNew = !Validation.hasNothing(this.custModIDField);
        Boolean successfulUpdate = false;
        
        if(notNew) {
            this.finalDataGrab();
            if(this.allFieldsHaveValue()) {
                successfulUpdate = customerDAO.updateCustomer(this.selectedCustomer);
                String message = "Customer " + this.selectedCustomer.getCustomerName() + " has been updated";
                this.cancelCustMod(message);
            }
            else {
                this.setErrorMessage("All Fields Must Have A Value To Proceed");
            }
        } else {
            this.setErrorMessage("Wrong Button ... This is a new Customer, not an update");
        }
        return successfulUpdate;
    }
    
    /**
     * This method directs the deletion of a Customer entry from the UCertify
     * database data source.
     *
     * @throws IOException if there is an FXML loading error
     * @throws SQLException if there is a database access error
     */
    public void deleteCustomer() throws IOException, SQLException {
        int IDToDelete = -1;
        IDToDelete = Validation.hasNothing(this.custModIDField) ? -1 : Integer.parseInt(this.custModIDField.getText());
        Boolean existsToDelete = IDToDelete != -1;
        Boolean hasNoAppointments = existsToDelete ? this.customerDAO.isAllowedToDelete(IDToDelete) : false;
        Boolean successfulDeletion = false;
        
        if(existsToDelete) {
            if(hasNoAppointments) {
                successfulDeletion = customerDAO.deleteCustomer(IDToDelete);
            } else {
                this.setErrorMessage("Customer Has Appointments And Cannot Be Deleted.\nPlease Delete All Appointments For This Customer First");
            }

            if(successfulDeletion) {
                this.clear();
                App.setHasMessageForCustomerScene(true);
                App.setMessageForCustomerScene("Customer with ID: " + IDToDelete + " has been deleted");
                SceneMediator.changeScene("CustomersMainScene");
            }
        } else {
            this.setErrorMessage("Oh no! There is not any information to delete!");
        }
        
    }
    
    /**
     * This method calls to clear all inputs and changes the application
     * main Stage Scene to the Customers Main Scene
     *
     * @throws IOException if there is an FXML loading error
     */
    public void cancel() throws IOException {
        this.clearAllInputs();
        SceneMediator.changeScene("CustomersMainScene");
    }
    
    /**
     * This method calls to clear all inputs, set a flag that there is a message to 
     * display on the Customers Main Scene, and set the message to display
     *
     * @param message the message to display on the Customer Main Scene
     * @throws IOException if there is an FXML loading error
     * @throws SQLException if there is a database access error
     */
    public void cancelCustMod(String message) throws IOException, SQLException {
        this.clearAllInputs();
        App.setHasMessageForCustomerScene(true);
        App.setMessageForCustomerScene(message);
        SceneMediator.changeScene("CustomersMainScene");
    }
    
    /**
     * Clears the provided TextField value
     * 
     * @param field the TextField to clear
     */
    private void clearField(TextField field) {
        field.clear();
    }
    
    /**
     * Clears the provided ComboBox value
     * 
     * @param cb the ComboBox to clear
     */
    private void clearComboBox(ComboBox cb) {
        cb.setValue(null);
    }
    
    /**
     * Clears all inputs on the Update Customers Scene
     */
    private void clearAllInputs() {
        this.clearField(this.customerNameSearchField);
        this.clearField(this.custModIDField);
        this.clearField(this.custModNameField);
        this.clearField(this.custModAddressField);
        this.clearField(this.custModPostalCodeField);
        this.clearField(this.custModPhoneField);
        this.clearComboBox(this.customerNameSearchComboBox);
        this.clearComboBox(this.custModCountryComboBox);
        this.clearComboBox(this.custModDivisionComboBox);
    }
    
    /**
     * Populates the CustomerNameSearchComboBox with observable user selections
     * so when one is chosen, an event is observed
     */
    private void populateCustomerNameSearchComboBox() {
        ObservableList<String> ob = FXCollections.observableArrayList();
        
        for(int i = 0; i < this.filteredCustomerList.size(); i++) {
            ob.add(this.filteredCustomerList.get(i).getCustomerName());
        }
        
        this.customerNameSearchComboBox.setItems(ob);
        this.customerNameSearchComboBox.setPromptText("--- Choose Customer ---");
    }
    
    /**
     * Populates the CountryComboBox with observable user selections
     * so when one is chosen, an event is observed
     */
    private void populateCustModCountryComboBox() {
        ObservableList<String> ob = FXCollections.observableArrayList();
        
        for(int i = 0; i < this.countries.size(); i++) {
            ob.add(this.countries.get(i).getCountry());
        }
        
        this.custModCountryComboBox.setItems(ob);
        this.custModCountryComboBox.setPromptText("--- Choose Country ---");
    }
    
    /**
     * Populates the DivisionComboBox with observable user selections
     * so when one is chosen, an event is observed
     */
    private void populateCustModDivisionComboBox() {
        ObservableList<String> ob = FXCollections.observableArrayList();
        
        for(int i = 0; i < this.divisions.size(); i++) {
            if(this.divisions.get(i).getCountryID() == this.selectedCountry.getCountryID()) {
                ob.add(this.divisions.get(i).getDivision());
            }
        }
        
        this.custModDivisionComboBox.setItems(ob);
        this.custModDivisionComboBox.setPromptText("--- Choose First Level Division ---");
    }
    
    /**
     * Sets the selectedCustomer property with the property values of the Customer
     * entry with the customerName value of name
     * 
     * @param name the customerName to search for
     */
    private void populateSelectedCustomer(String name) {
        this.filteredCustomerList.forEach(customer -> {
            if(name.equals(customer.getCustomerName())) {
                this.selectedCustomer = customer;
                this.populateSelectedCustomerFields();
            }
        });
    }
    
    /**
     * Populates all the inputs with data from the selectedCustomer object
     */
    private void populateSelectedCustomerFields() {
        Customer cust = this.selectedCustomer;
        this.custModIDField.setText(Integer.toString(cust.getCustomerID()));
        this.custModNameField.setText(cust.getCustomerName());
        this.custModAddressField.setText(cust.getAddress());
        this.custModPostalCodeField.setText(cust.getPostalCode());
        this.custModPhoneField.setText(cust.getPhone());
        this.populateSelectedCustomerDivisionComboBox();
    }
    
    /**
     * Populates the DivisionComboBox with the value of the selectedCustomer
     * Division
     */
    private void populateSelectedCustomerDivisionComboBox() {
        this.divisions.forEach(division -> {
            if(division.getDivisionID() == this.selectedCustomer.getDivisionID()) {
                this.populateSelectedCustomerCountryComboBox(division.getCountryID());
                this.custModDivisionComboBox.setValue(division.getDivision());
            }
        });
    }
    
    /**
     * Populates the CountryComboBox with the value of the selectedCustomer
     * Country
     * 
     * @param countryID the countryID of the Country entry to search for
     */
    private void populateSelectedCustomerCountryComboBox(int countryID) {
        // Grab the selected country, but backwards by the division
        this.countries.forEach(country -> {
            if(country.getCountryID() == countryID) {
                this.selectedCountry = country;
                this.custModCountryComboBox.setValue(country.getCountry());
            }
        });
        this.populateCustModDivisionComboBox();
    }
    
    /**
     * Grabs all of the input data in order to create a new Customer or update
     * an existing Customer
     */
    private void finalDataGrab() {
        Customer cust = new Customer();
        Timestamp lastUpdate = new Timestamp(new Date().getTime());
        User curUser = App.getCurrentUser();
        
        if(this.allFieldsHaveValue()) {
            if(!this.custModIDField.getText().isEmpty() && !this.custModIDField.getText().isBlank()) cust.setCustomerID(Integer.parseInt(this.custModIDField.getText()));
            cust.setCustomerName(this.custModNameField.getText());
            cust.setAddress(this.custModAddressField.getText());
            cust.setPostalCode(this.custModPostalCodeField.getText());
            cust.setPhone(this.custModPhoneField.getText());
            cust.setLastUpdate(lastUpdate);
            cust.setDivisionID(this.getDivisionIDFromName(this.custModDivisionComboBox.getValue().toString()));
            cust.setLastUpdatedBy(curUser.getUserName());
            // Add a creator if the ID Field is null menaing this is a new customer
            if(this.custModIDField.getText().isEmpty() || this.custModIDField.getText().isBlank()) {
                // If ID Field is null, this is a new customer
                cust.setCreatedBy(curUser.getUserName());
                cust.setCreateDate(new Date());
            }
        }
        
        this.selectedCustomer = cust;
    }
    
    /**
     * Loops through all the Divisions and compares divisionID and division (name)
     * 
     * @param divisionName the division property of the Division to search for
     * @return the divisionID of the Division entry with the divisionName of the provided
     * divisionName
     */
    private int getDivisionIDFromName(String divisionName) {
        int idToReturn = 0;
        for(int i = 0; i < this.divisions.size(); i++) {
            if(this.divisions.get(i).getDivision().equals(divisionName)) {
                idToReturn = this.divisions.get(i).getDivisionID();
            }
        }
        return idToReturn;
    }
    
}
