
package com.boslott.controller;

import com.boslott.DataAccess.UCertdbAppointmentDAO;
import com.boslott.DataAccess.UCertdbContactDAO;
import com.boslott.DataAccess.UCertdbCustomerDAO;
import com.boslott.DataAccess.UCertdbUserDAO;
import com.boslott.model.Appointment;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.sql.Date;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class for the Appointments Update Scene
 *
 * @author Steven Slott
 */
public class AppointmentsUpdateSceneController implements Initializable {
    
    /**
     * The TextField to display the Appointment appointmentID property value
     */
    @FXML TextField apptIDField;
    
    /**
     * The TextField to display the Appointment title property value
     */
    @FXML TextField titleField;
    
    /**
     * The TextArea to display the Appointment description property value
     */
    @FXML TextArea descriptionField;
    
    /**
     * The TextField to display the Appointment location property value
     */
    @FXML TextField locationField;
    
    /**
     * The TextField to display the Appointment type property value
     */
    @FXML TextField typeField;
    
    /**
     * The ComboBox to display the contactName options
     */
    @FXML ComboBox contactComboBox;
    
    /**
     * The ComboBox to display the customerName options
     */
    @FXML ComboBox customerComboBox;
    
    /**
     * The ComboBox to display the userName of the currently-logged in User
     */
    @FXML ComboBox userComboBox;
    
    /**
     * The DatePicker to display and select the date value of the startDateTime property
     */
    @FXML DatePicker startDatePicker;
    
    /**
     * The DatePicker to display and select the date value of the endDateTime property
     */
    @FXML DatePicker endDatePicker;
    
    /**
     * The ComboBox to display the time value of the startDateTime property
     */
    @FXML ComboBox startTimeComboBox;
    
    /** 
     * The ComboBox to display the time value of the endDateTime property
     */
    @FXML ComboBox endTimeComboBox;
    
    /**
     * The label to modify if there is an error message to display in the UI
     */
    @FXML Label errorMessageLabel;
    
    /**
     * The button to click to activate the insert-into-database method
     */
    @FXML Button createNewButton;
    
    /**
     * The button to click to activate the update-database method
     */
    @FXML Button updateButton;

    /**
     * The AppointmentDAO to use to interact with data in the UCertify database
     */
    private UCertdbAppointmentDAO apptDAO;
    
    /**
     * The ContactDAO to use to interact with data in the UCertify database
     */
    private UCertdbContactDAO contactDAO;
    
    /**
     * The UserDAO to use to interact with data in the UCertify database
     */
    private UCertdbUserDAO userDAO;
    
    /**
     * The CustomerDAO to use to interact with data in the UCertify database
     */
    private UCertdbCustomerDAO customerDAO;
    
    /**
     * An Appointment object to hold the property values of the Appointment to update
     */
    private Appointment appointmentToUpdate;
    
    /**
     * An ArrayList of LocalDatTime objects used to create user choices for the
     * start and end time ComboBoxes 
     */
    private ArrayList<LocalDateTime> timesList;
    

    /**
     * Initializes this controller class. Lambda expressions are used for the
     * event handlers to set the actions for the date-time-related input controls.
     * The onAction methods accept a functional interface, an event handler, so
     * a lambda expression is used
     * 
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.apptDAO = App.getUCertdbDAOFactory().getAppointmentDAO();
            this.contactDAO = App.getUCertdbDAOFactory().getContactDAO();
            this.userDAO = App.getUCertdbDAOFactory().getUserDAO();
            this.customerDAO = App.getUCertdbDAOFactory().getCustomerDAO();
            this.apptIDField.setDisable(true);
            if(AppointmentsMainSceneController.getCurrentAppointment() != null) this.prePopulateAllFields(AppointmentsMainSceneController.getCurrentAppointment());
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentsUpdateSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.clearErrorMessage();
        this.timesList = new TimesArrayList().getTimesList();
        this.populateContactComboBox();
        this.populateUserComboBox();
        this.populateCustomerComboBox();
        this.populateTimeComboBoxes();
        this.customerComboBox.setOnAction(value -> {
            this.startDatePicker.setValue(null);
            this.clearComboBox(this.startTimeComboBox);
            this.endDatePicker.setValue(null);
            this.clearComboBox(this.endTimeComboBox);
            this.validateChosenDateTimes();
        });
        this.startDatePicker.setOnAction(date -> {
            if(this.endDatePicker.getValue() != null && this.endTimeComboBox.getValue() != null) {
                this.validateChosenDateTimes();
            }
        });
        this.endDatePicker.setOnAction(date -> {
            this.validateChosenDateTimes();
        });
        this.startTimeComboBox.setOnAction(time -> {
            this.validateChosenDateTimes();
        });
        this.endTimeComboBox.setOnAction(time -> {
            this.validateChosenDateTimes();
        });
        
    }  
    
    /**
     * A validation method used to determine whether or not all input Fields,
     * ComboBoxes, and DatePickers have values before sending off to the data source
     * @return true if all input controls have a value, false if not all of the
     * input controls have a value
     */
    private Boolean allFieldsHaveValue() {
        Boolean hasTitle = !Validation.hasNothing(this.titleField);
        Boolean hasDescription = !Validation.hasNothing(this.descriptionField);
        Boolean hasLocation = !Validation.hasNothing(this.locationField);
        Boolean hasType = !Validation.hasNothing(this.typeField);
        Boolean hasCustomer = !Validation.hasNothing(this.customerComboBox);
        Boolean hasContact = !Validation.hasNothing(this.contactComboBox);
        Boolean hasUser = !Validation.hasNothing(this.userComboBox);
        Boolean hasStart = this.startDatePicker.getValue() == null ?
            false : !Validation.hasNothing(this.startDatePicker);
        Boolean hasStartTime = !Validation.hasNothing(this.startTimeComboBox);
        Boolean hasEnd = this.endDatePicker.getValue() == null ?
            false : !Validation.hasNothing(this.endDatePicker);
        Boolean hasEndTime = !Validation.hasNothing(this.endTimeComboBox);
        
        return hasTitle &&
            hasDescription &&
            hasLocation &&
            hasType &&
            hasCustomer &&
            hasUser &&
            hasStart &&
            hasStartTime &&
            hasEnd &&
            hasEndTime;
    }
    
    /**
     * Creates a new Appointment entry in a data source. This method directs validation
     * of all required inputs, communicating with a data source via a Data Access
     * Object, and clearing and changing the application's main Stage Scene
     *
     * @return true if a new Appointment entry is successfully completed, false
     * if the process does not successfully complete
     * @throws IOException if an error occurs with loading the FXML
     */
    public Boolean createNew() throws IOException {
        // Grab all the data from the inputs
        Appointment newAppt = null;
        Boolean hasNoID = Validation.hasNothing(this.apptIDField);
        
        // Validate the data
        if(hasNoID) {
            if(this.allFieldsHaveValue()) {
                if(!this.errorMessageLabel.getText().equals("")) {
                    this.createNewButton.setDisable(true);
                    return false;
                }
                newAppt = this.finalDataGrab();

                Boolean successfulCreation = this.apptDAO.insertAppointment(newAppt);

                if(successfulCreation) {
                    this.clear();
                    this.setErrorMessage("");
                    App.setHasMessageForAppointmentScene(true);
                    App.setMessageForAppointmentScene("A New Appointment Has Been Created");
                    AppointmentsMainSceneController.setCurrentAppointment(null);
                    SceneMediator.changeScene("AppointmentsMainScene");
                    return true;
                } else {
                    this.setErrorMessage("Oh No! An Error Has Happened While Inserting Data Into Database");
                    return false;
                }
//                    
            } else {
                this.setErrorMessage("All Fields Must Have A Value To Proceed");
                return false;
            }
        } else {
            // If ID already exists, this is an Update
            this.setErrorMessage("Wrong Button ... ID Exists, This Is An Update");
            return false;
        }
    }
    
    /**
     * Updates an existing Appointment in a data source. This method directs the 
     * process of updating the Appointment, ensuring validating takes place of  the 
     * user input, setting/removing error messages on the UI, communicating with 
     * the AppointmentDAO to update the Appointment entry, and clearing and changing
     * the application main Stage Scene
     *
     * @throws IOException if an error occurs with loading the FXML
     */
    public void update() throws IOException {
        Appointment apptToUpdate = null;
        Boolean notNew = !Validation.hasNothing(this.apptIDField);
        
        if(notNew) {
            if(this.allFieldsHaveValue()) {
                if(!this.errorMessageLabel.getText().equals("")) {
                    this.updateButton.setDisable(true);
                    return;
                }
                apptToUpdate = this.finalDataGrab();
                Boolean successfulUpdate = this.apptDAO.updateAppointment(apptToUpdate);
                if(successfulUpdate) {
                    this.setErrorMessage("");
                    this.clear();
                    App.setHasMessageForAppointmentScene(true);
                    App.setMessageForAppointmentScene(
                        "Appointment With ID: " + apptToUpdate.getAppointmentID() + " Has Been Updated"
                    );
                    SceneMediator.changeScene("AppointmentsMainScene");
                }
            } else {
                this.setErrorMessage("All Fields Must Have A Value To Proceed");
            }
        } else {
            // If ID is blank, this is a Create, not an Update
            this.setErrorMessage("Wrong Button ... This Is A New ID And So Is A Create New");
        }
    }
    
    /**
     * Deletes an Appointment entry from a data source. This method directs the process
     * of deleting an Appointment entry from a UCertify database, ensuring validation
     * takes place, setting/clearing UI error messages, communicating with the DAO,
     * and clearing and changing the application main Stage Scene
     *
     * @throws IOException if an error occurs with loading the FXML
     */
    public void delete() throws IOException {
        int apptIDToDelete = -1;
        apptIDToDelete = Validation.hasNothing(this.apptIDField) ? -1 : parseInt(this.apptIDField.getText());
        Boolean existsToDelete = apptIDToDelete != -1;
        
        if(existsToDelete) {
            String apptToDeleteType = AppointmentsMainSceneController.getCurrentAppointment().getType();
            Boolean successfulDeletion = this.apptDAO.deleteAppointment(apptIDToDelete);
            
            if(successfulDeletion) {
                this.setErrorMessage("");
                this.clear();
                App.setHasMessageForAppointmentScene(true);
                App.setMessageForAppointmentScene("Appointment With ID: " + apptIDToDelete + " and Type: " + apptToDeleteType + " has been deleted");
                SceneMediator.changeScene("AppointmentsMainScene");
            }
            
        } else {
            this.setErrorMessage("Oh No! There Is Not An ID To Delete!");
        }
        
        
        
    }
    
    /**
     * Clears all the inputs, changes the application current Appointment to null,
     * and changes the application main Stage Scene
     *
     * @throws IOException if an error occurs with loading the FXML
     */
    public void cancel() throws IOException {
        this.clearAllInputs();
        AppointmentsMainSceneController.setCurrentAppointment(null);
        SceneMediator.changeScene("AppointmentsMainScene");
    }
    
    /**
     * Populates the ContactComboBox with user choices for Contacts
     */
    private void populateContactComboBox() {
        ObservableList<String> ob = FXCollections.observableArrayList();
        
        for(int i = 0; i < this.contactDAO.findAllContacts().size(); i++) {
            ob.add(this.contactDAO.findAllContacts().get(i).getContactName());
        }
        
        this.contactComboBox.setItems(ob);
        this.contactComboBox.setPromptText("--- Choose Contact ---");
    }
    
    /**
     * Populates the UserComboBox with user choices for Users
     */
    private void populateUserComboBox() {
        ObservableList<String> ob = FXCollections.observableArrayList();
        
        
        ob.add(App.getCurrentUser().getUserName());
        
        
        this.userComboBox.setItems(ob);
        this.userComboBox.setValue(App.getCurrentUser().getUserName());
        this.userComboBox.setDisable(true);
//        this.userComboBox.setPromptText("--- Choose User ---");
    }
    
    /**
     * Populates the CustomerComboBox with user choices or Customers
     */
    private void populateCustomerComboBox() {
        ObservableList<String> ob = FXCollections.observableArrayList();
        
        for(int i = 0; i < this.customerDAO.findAllCustomers(false).size(); i++) {
            ob.add(this.customerDAO.findAllCustomers(false).get(i).getCustomerName());
        }
        
        this.customerComboBox.setItems(ob);
        this.customerComboBox.setPromptText("--- Choose Customer ---");
    }
    
    /**
     * Populates the TimeComboBoxes with user choices for start and end times
     */
    private void populateTimeComboBoxes() {
        this.startTimeComboBox.getItems().add("--- Choose Time ---");
        for(int i = 0; i < this.timesList.size(); i++) {
            this.startTimeComboBox.getItems().add(this.timesList.get(i).toLocalTime());
        }
        
        this.endTimeComboBox.getItems().add("--- Choose Time ---");
        for(int i = 0; i < this.timesList.size(); i++) {
            this.endTimeComboBox.getItems().add(this.timesList.get(i).toLocalTime());
        }
    }
    
    /**
     * If an Appointment is selected to be updated, this method populates all
     * the input controls with the Appointment-to-be-updated object's property
     * values when switching Scenes to the AppointmentsUpdateScene
     * 
     * @param appt the Appointment object used to hold the property values of
     * the Appointment entry to update, including the appointmentID
     */
    private void prePopulateAllFields(Appointment appt) {
        
        Timestamp startDateToUse = utcToLocalTimestamp(appt.getStartDateTime());
        Timestamp endDateToUse = utcToLocalTimestamp(appt.getEndDateTime());
        
        this.apptIDField.setText(String.valueOf(appt.getAppointmentID()));
        this.titleField.setText(appt.getTitle());
        this.descriptionField.setText(appt.getDescription());
        this.locationField.setText(appt.getLocation());
        this.typeField.setText(appt.getType());
        this.contactComboBox.setValue(this.contactDAO.findContact(appt.getContactID()).getContactName());
        this.customerComboBox.setValue(this.customerDAO.findCustomer(appt.getCustomerID()).getCustomerName());
        this.startDatePicker.setValue(LocalDateTime.ofInstant(
            startDateToUse.toInstant(), ZoneOffset.systemDefault()).toLocalDate()
        );
        this.endDatePicker.setValue(LocalDateTime.ofInstant(
            endDateToUse.toInstant(), ZoneOffset.systemDefault()).toLocalDate()
        );
        this.startTimeComboBox.setValue(LocalDateTime.ofInstant(
            startDateToUse.toInstant(), ZoneOffset.systemDefault()).toLocalTime()
        );
        this.endTimeComboBox.setValue(LocalDateTime.ofInstant(
            endDateToUse.toInstant(), ZoneOffset.systemDefault()).toLocalTime()
        );
    }
    
    /**
     * This method is executed for a Create or an Update. This method gets all 
     * inputted values from input controls, sets the inputted values to the Appointment
     * object apptToUpdate's property values, and returns the newly created 
     * apptToUpdate Appointment object.
     * 
     * @return the Appointment object holding all the property values of the 
     * Appointment to create or update
     */
    private Appointment finalDataGrab() {
        
        Appointment apptToUpdate = new Appointment();
        String contactName = !Validation.hasNothing(this.contactComboBox) ?
            this.contactComboBox.getValue().toString() : null;
        String customerName = !Validation.hasNothing(this.customerComboBox) ?
            this.customerComboBox.getValue().toString() : null;
        String userName = !Validation.hasNothing(this.userComboBox) ?
            this.userComboBox.getValue().toString() : null;
        Timestamp startTimestamp = null;
        Timestamp endTimestamp = null;
        
        if(!Validation.hasNothing(this.startTimeComboBox)) {
            startTimestamp = this.createTimestampFromInputs(this.startTimeComboBox, this.startDatePicker, "UTC");

        }
        if(!Validation.hasNothing(this.endTimeComboBox)) {
            endTimestamp = this.createTimestampFromInputs(this.endTimeComboBox, this.endDatePicker, "UTC");
        }

        if(!this.apptIDField.getText().isEmpty() && !this.apptIDField.getText().isBlank()) {
            apptToUpdate.setAppointmentID(Integer.parseInt(this.apptIDField.getText()));
        }
        apptToUpdate.setTitle(this.titleField.getText());
        apptToUpdate.setDescription(this.descriptionField.getText());
        apptToUpdate.setLocation(this.locationField.getText());
        apptToUpdate.setType(this.typeField.getText());
        apptToUpdate.setLastUpdate(new Timestamp(new java.util.Date().getTime()));
        apptToUpdate.setLastUpdatedBy(App.getCurrentUser().getUserName());
        apptToUpdate.setContactID(this.contactDAO.findContactByName(contactName).getContactID());
        apptToUpdate.setCustomerID(this.customerDAO.findCustomerByName(customerName).getCustomerID());
        apptToUpdate.setUserID(this.userDAO.findUser(userName).getUserID());
        apptToUpdate.setStartDateTime(startTimestamp);
        apptToUpdate.setEndDateTime(endTimestamp);
        
        // Add a creator if the ID Field is null meaning this is a new customer
        if(this.apptIDField.getText().isEmpty() || this.apptIDField.getText().isBlank()) {
            // If ID Field is null, this is a new customer
            apptToUpdate.setCreatedBy(App.getCurrentUser().getUserName());
            apptToUpdate.setCreateDate(new Date(System.currentTimeMillis()));
        }
        
        return apptToUpdate;
    }
    
    /**
     * This method converts to inputted values from the input controls and returns
     * a Timestamp
     * 
     * @param timeBox the ComboBox to retrieve user input from
     * @param datePick the DatePicker to retrieve user input from
     * @param zoneIdOf the time zone to use to create the Timestamp
     * @return a Timestamp of the user-selected time, converted to the provided
     * time zone
     */
    private Timestamp createTimestampFromInputs(ComboBox timeBox, DatePicker datePick, String zoneIdOf) {
        // Get the values from the inputs
        LocalDate chosenDate = datePick.getValue();
        String timeString = timeBox.getValue().toString();
        String[] timeSplit = timeString.split(":", 2);
        
        LocalDateTime nonZonedLocalDateTime = LocalDateTime.of(
            chosenDate.getYear(),
            chosenDate.getMonth(),
            chosenDate.getDayOfMonth(),
            parseInt(timeSplit[0]),
            parseInt(timeSplit[1])
        );
        
        ZoneId estZoneId = ZoneId.of(ZoneId.SHORT_IDS.get("EST"));
        ZoneId utcZoneId = ZoneId.of("Z");
        ZoneId zoneIdToUse = null;
        
        if(zoneIdOf.equals("EST")) zoneIdToUse = estZoneId;
        if(zoneIdOf.equals("UTC")) zoneIdToUse = utcZoneId;
        else zoneIdToUse = utcZoneId;
        
        
        ZonedDateTime zonedLocalDateTime = nonZonedLocalDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime localToUTCDateTime = zonedLocalDateTime.withZoneSameInstant(utcZoneId);
        
        
        // Returns the time from the input converted to choice time zone
        return Timestamp.valueOf(localToUTCDateTime.toLocalDateTime());
        
    }
    
    /**
     * This method converts the provided UTC Timestamp and returns the same-point-in-time
     * Timestamp for the user's local time zone
     *
     * @param utcTime the UTC Timestamp to convert to local time zone
     * @return a Timestamp of the provided UTC Timestamp, converted to the user's local time zone
     */
    public static Timestamp utcToLocalTimestamp(Timestamp utcTime) {
        ZonedDateTime utcZonedDateTime = utcTime.toLocalDateTime().atZone(ZoneId.of("Z"));
        ZonedDateTime utcConvertedToLocalZonedDateTime = utcZonedDateTime.withZoneSameInstant(ZoneId.systemDefault());
        
        return Timestamp.valueOf(utcConvertedToLocalZonedDateTime.toLocalDateTime());
    }
    

    
    
    
    
    
    
    // ------------------------------------------------------------------------
    //      Validate selected dateTimes are within EST business hours
    // ------------------------------------------------------------------------

    /**
     * This method directs the process of validating the chosen startDateTime
     * and the chosen endDateTime to ensure the times are within EST business
     * hours, that the start comes before the end, and that the times do not
     * cross over into multiple days
     */
    
    public void validateChosenDateTimes() {
        this.clearErrorMessage();
        // First validate to ensure if the inputs are not null
        // Then validate to ensure inputs are within business hours
        
        Boolean chosenStartDateTimeIsValid = this.validateChosenDateTime("start");
        
        Boolean chosenEndDateTimeIsValid = false;
        Boolean startComesBeforeEnd = false;
        
        if(chosenStartDateTimeIsValid) {
            chosenEndDateTimeIsValid = this.validateChosenDateTime("end");
            
            if(chosenEndDateTimeIsValid) {
                // Is startDateTime before endDateTime ?
                startComesBeforeEnd = this.startComesBeforeEnd();
                
                if(!startComesBeforeEnd) {
                    this.setErrorMessage("The START datetime must come before the END datetime\nPlease make another selection");
                }
            }
        }
        
        if(chosenStartDateTimeIsValid && chosenEndDateTimeIsValid && startComesBeforeEnd) {
            
            // The chosen DateTimes are with business hours
            // The chosen start comes before the chosen end
            // Are they within the same day?
            
            Boolean chosenDatesAreInSameDay = false;
            
            Timestamp chosenStartDate = this.createTimestampFromInputs(this.startTimeComboBox, this.startDatePicker, "UTC");
            Timestamp chosenEndDate = this.createTimestampFromInputs(this.endTimeComboBox, this.endDatePicker, "UTC");
            ZoneId zone = ZoneId.of("Z");
            
            int startDay = LocalDateTime.ofInstant(chosenStartDate.toInstant(), zone)
                .getDayOfMonth();
            int endDay = LocalDateTime.ofInstant(chosenEndDate.toInstant(), zone)
                .getDayOfMonth();
            
            long endMinusStart = chosenEndDate.getTime() - chosenStartDate.getTime();
            Boolean isWithinSame14HourBusinessDay = 50400000 - endMinusStart >= 0;
            
            if(isWithinSame14HourBusinessDay) {
                this.clearErrorMessage();
                chosenDatesAreInSameDay = true;
                
                // At this point the chosen times are within business hours
                // Chosen times are within the same business day
                // Chosen times are in the correct chronological order: start then end
                // Now compare to existing customer appointments ...
                
                this.compareCustomerAppointments(chosenStartDate, chosenEndDate);
                
                
                
            }
            else if(startDay < endDay) {
                this.setErrorMessage("Start and End must be on the same day\nPlease make another selection");
                chosenDatesAreInSameDay = false;
            }
            else if(startDay > endDay) {
                this.setErrorMessage("Start date must come before End date\nPlease make another selection");
                chosenDatesAreInSameDay = false;
            }
        }
    }
    
    
    /**
     * This method validates a specified user inputted time. First the user
     * inputs are obtained and a Timestamp is created in the equivalent UTC. This
     * Timestamp is used to compare that the time chosen falls within the EST business 
     * hours. UI error messages are set and removed to communicate with the user
     * 
     * @param type a designation of either "start" or "end" to tell the method
     * which user input controls to get data from
     * @return 
     */
    private Boolean validateChosenDateTime(String type) {
        // If the date is changed, this is called
        // If the time is changed, this is called
        
        Timestamp timeChoiceTimeStamp = null;
        
        // Check if both date and time are not null and make a Timestamp
        if(type.equals("start")) {
            if(this.startDatePicker.getValue() != null) {
                if(this.startTimeComboBox.getValue() != null) {
                    // Create a Timestamp of the local time chosen, but converted to UTC
                    timeChoiceTimeStamp = this.createTimestampFromInputs(this.startTimeComboBox, this.startDatePicker, "UTC");
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        else if(type.equals("end")) {
            if(this.endDatePicker.getValue() != null) {
                if(this.endTimeComboBox.getValue() != null) {
                    // Create a Timestamp of the local time chosen, but converted to UTC
                    timeChoiceTimeStamp = this.createTimestampFromInputs(this.endTimeComboBox, this.endDatePicker, "UTC");
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
        
        
        
        // Check that Timestamp is within Company business hours
        int chosenHourOfDay = LocalDateTime.ofInstant(timeChoiceTimeStamp.toInstant(), ZoneId.systemDefault()).getHour();
        int chosenMinOfDay = LocalDateTime.ofInstant(timeChoiceTimeStamp.toInstant(), ZoneId.systemDefault()).getMinute();
        
        // Timestamps are UTC times, so EST business hours are 0-3 || 12-24 UTC
        // 3 < X =< 8 ... Too late, after hours
        // 8 <= X < 12 ... To early, before hours
        
        System.out.println("The chosenHourOfDayzzz == " + chosenHourOfDay);
        System.out.println("The chosenMinOfDay == " + chosenMinOfDay);
        
        
        if(8 <= chosenHourOfDay && chosenHourOfDay < 12) {
            if(chosenMinOfDay > 0) {
                // Too early, business is not open yet
                this.setErrorMessage(
                    "The chosen " + (type.equals("start") ? "START" : "END")
                        + " time is before company hours: 8am - 10pm EST\nPlease make another selection"
                );
                return false;
            } else {
                // This is the end of the hour
                return true;
            }
            
            
        } else if(2 < chosenHourOfDay && chosenHourOfDay <= 8) {
            System.out.println("The chosen time is after 10pm");
            this.setErrorMessage(
                "The chosen " + (type.equals("start") ? "START" : "END")
                    + " time is after company hours: 8am - 10pm EST\nPlease make another selection"
            );
            return false;
            
        } else {
            if(chosenHourOfDay == 2 && chosenMinOfDay > 0) {
                System.out.println("The choen time is after 10pm by minutes");
                this.setErrorMessage("The chosen " + (type.equals("start") ? "START" : "END") + " time is after company hours: 8am - 10pnm EST\nPlease make another selection");
                return false;
            }
            // chosenHour is within business hours 
            this.clearErrorMessage();
            return true;
        }
        
    }
    
    /**
     * A method to validate whether the startdateTime Timestamp comes before the
     * endDateTime Timestamp or not
     * 
     * @return true if startDateTime Timestamp comes chronologically before the 
     * endDateTime Timestamp, false if end comes before start
     */
    private Boolean startComesBeforeEnd() {
        Timestamp startTime = this.createTimestampFromInputs(this.startTimeComboBox, this.startDatePicker, "UTC");
        Timestamp endTime = this.createTimestampFromInputs(this.endTimeComboBox, this.endDatePicker, "UTC");
        
        if(startTime.compareTo(endTime) > 0) {
            // starttime is after endtime
            return false;
        }
        return true;
    }
    
    
    
    //  --------------------------------------------------------------------
    //      Compare chosen times to existing Customer Appointment times
    //  --------------------------------------------------------------------
    
    /**
     * This method compares the user inputted chosen startDateTime and user inputted
     * chosen endDateTime with currently scheduled start and end of existing 
     * Appointments for the user chosen Customer. UI error messages are toggled
     * in order to communicate with the user.
     * 
     * Lambda expressions are used as the Consumer for the ArrayList.forEach method
     * in order to loop through all of the Appointments and get the Appointments
     * for the same "chosen" day, then as the Consumer for the forEach method to 
     * loop through all of the "chosen" day Appointments and compare the Timestamps.
     * 
     * @param chosenStartDate the user inputted startDateTime Timestamp in UTC
     * @param chosenEndDate  the user inputted endDateTime Timestamp in UTC
     */
    private void compareCustomerAppointments(Timestamp chosenStartDate, Timestamp chosenEndDate) {
        
        ArrayList<Appointment> allCustomerAppointments = new ArrayList<>();
        ArrayList<Appointment> sameDayAppointments = new ArrayList<>();
        
        if(this.customerIdNotNull()) {
            allCustomerAppointments = this.getApptsForChosenCustomer();
            
            allCustomerAppointments.forEach(appt -> {
                if(isSameDayOfSameYear(appt.getStartDateTime(), chosenStartDate)) {
                    sameDayAppointments.add(appt);
                }               
            });
            
            // Now compare the chosen times to the current appointments on the same day
            sameDayAppointments.forEach(appt -> {
                Boolean chosenStartEqualsCurrentApptStart = this.timestampsAreSameTime(chosenStartDate, appt.getStartDateTime());
                Boolean chosenStartEqualsCurrentApptEnd = this.timestampsAreSameTime(chosenStartDate, appt.getEndDateTime());
                Boolean chosenEndEqualsCurrentApptStart = this.timestampsAreSameTime(chosenEndDate, appt.getStartDateTime());
                Boolean chosenEndEqualsCurrentApptEnd = this.timestampsAreSameTime(chosenEndDate, appt.getEndDateTime());
                
                Boolean chosenStartIsAfterAndBefore = this.timestampIsAfterAndBefore(chosenStartDate, appt.getStartDateTime(), appt.getEndDateTime());
                Boolean chosenEndIsAfterAndBefore = this.timestampIsAfterAndBefore(chosenEndDate, appt.getStartDateTime(), appt.getEndDateTime());
                
                Boolean chosenTimesEngulfCurrentAppt = this.chosenTimesEngulf(chosenStartDate, chosenEndDate, appt.getStartDateTime(), appt.getEndDateTime());
                
                if(chosenStartEqualsCurrentApptStart) {
                    // Cannot start an appointment at the same time as another appointment
                    this.setErrorMessage("The selected Customer already has an appointment at that time\nPlease select another START time");
                }
                if(chosenStartEqualsCurrentApptEnd) {
                    this.setErrorMessage("Appointments must have a break between them. This customer already has an appointment ending at the chosen START time\nPlease make a different START selection");
                }
                if(chosenEndEqualsCurrentApptEnd) {
                    this.setErrorMessage("The selected Customer already has an appointment at that time\nPlease select another END time");
                }
                if(chosenEndEqualsCurrentApptStart) {
                    this.setErrorMessage("Appointments must have a break between them. This customer already has an appointment starting at the chosen END tim\nPlease make a differnet END selection");
                }
                
                if(chosenStartIsAfterAndBefore) {
                    this.setErrorMessage("Chosen Customer already has an appointment at that time\nPlease select a different START time");
                }
                if(chosenEndIsAfterAndBefore) {
                    this.setErrorMessage("Chosen Customer already has an appointment at that time\nPlease select a different END time");
                }
                
                if(chosenTimesEngulfCurrentAppt) {
                    this.setErrorMessage("Chosen Customer already has an appointment in the middle of the chosen appointment hours\nPlease make new time selections");
                }
                    
                
                
            });
        }
    }
    
    /**
     * Gets whether or not there is a user inputted value in the CustomerComboBox
     * or not
     * 
     * @return true if there is a value, false if there is not a value
     */
    private Boolean customerIdNotNull() {
        return this.customerComboBox.getValue() != null;
    }
    
    /**
     * Gets the user inputted Customer name, then gets all the Appointment objects
     * related to the Customer name from the UCertify database data source
     * 
     * @return an ArrayList of all Appointments related to the user chosen
     * Customer name
     */
    private ArrayList<Appointment> getApptsForChosenCustomer() {
        String name = this.customerComboBox.getValue().toString();
        return this.apptDAO.findAllCustomerAppointments(name);
    }
    
    /**
     * The method compares two provided Timestamps and returns whether or not
     * the Timestamps coincide during the same calendar year
     * 
     * @param apptStart the Timestamp of the existing Appointment start
     * @param chosenTime the Timestamp in question to compare
     * @return true if the Timestamps are in the same year, false if the
     * Timestamps are not in the same year
     */
    private Boolean isSameDayOfSameYear(Timestamp apptStart, Timestamp chosenTime) {
        LocalDateTime apptLdt = apptStart.toLocalDateTime();
        LocalDateTime chosenTimeLdt = chosenTime.toLocalDateTime();
        int apptYear = apptLdt.getYear();
        int chosenTimeYear = chosenTimeLdt.getYear();
        int apptMonth = apptLdt.getMonthValue();
        int chosenTimeMonth = chosenTimeLdt.getMonthValue();
        int apptDay = apptLdt.getDayOfMonth();
        int chosenTimeDay = chosenTimeLdt.getDayOfMonth();
        
        Boolean isSameYear = apptYear == chosenTimeYear;
        Boolean isSameMonth = apptMonth == chosenTimeMonth;
        Boolean isSameDay = apptDay == chosenTimeDay;
        
        return isSameYear && isSameMonth && isSameDay;
    }
    
    /**
     * Compares two provided Timestamps and returns whether or not they have 
     * the same hour and same minute
     * 
     * @param ts1 the first of two Timestamps to compare
     * @param ts2 the second of two Timestamps to compare
     * @return true if the provided Timestamps have the same hour and same minute,
     * false if the Timestamps do not have the same hour or same minute
     */
    private Boolean timestampsAreSameTime(Timestamp ts1, Timestamp ts2) {
        // The same means same hour, same minute
        LocalDateTime ts1LDT = ts1.toLocalDateTime();
        LocalDateTime ts2LDT = ts2.toLocalDateTime();
        
        int ts1Hour = ts1LDT.getHour();
        int ts2Hour = ts2LDT.getHour();
        int ts1min = ts1LDT.getMinute();
        int ts2min = ts2LDT.getMinute();
        
        if(ts1Hour == ts2Hour) {
            // Same hour ... same minute?
            if(ts1min == ts2min) {
                // Equal appointment times
                return true;
            }
            else return false;
        }
        else return false;
    }
    
    /**
     * The method returns whether or not a chosen Timestamp falls in between the
     * start and the end Timestamp of an existing Appointment
     * 
     * @param chosen the user inputted chosen Timestamp in question
     * @param start the start Timestamp of the existing Appointment
     * @param end the end Timestamp of the existing Appointment
     * @return true if the chosen Timestamp falls in between the existing start
     * and end Timestamps, false if the chosen does not fall in between the
     * existing start and end
     */
    private Boolean timestampIsAfterAndBefore(Timestamp chosen, Timestamp start, Timestamp end) {
        
        Boolean chosenIsBeforeStart = chosen.before(start);
        Boolean chosenIsAfterStart = start.before(chosen);
        Boolean chosenIsBeforeEnd = chosen.before(end);
        Boolean chosenIsAfterEnd = end.before(chosen);
        
        if(chosenIsBeforeStart) {
            // Chosen time is not between start and end
            return false;
        }
        else if(chosenIsAfterEnd) {
            // Chosen time is not between start and end
            return false;
        }
        else if(chosenIsAfterStart && chosenIsBeforeEnd) {
            // Chosen falls inbetween start and end
            return true;
        }
        else return false;
    }
    
    /**
     * The method returns whether or not a chosen start and chosen end Timestamp
     * engulf an existing appointment or not. Engulf means to completely surround
     * an Appointment - chosen starts before an existing and ends after and existing
     * 
     * @param chosenStartDate the user inputted chosen start
     * @param chosenEndDate the user inputted end
     * @param apptStart the existing Appointment start
     * @param apptEnd the existing Appointment end
     * @return true if the chosen start and chosen end engulf an existing Appointment,
     * false if the chosen start and chosen end do not engulf an existing Appointment
     */
    private Boolean chosenTimesEngulf(Timestamp chosenStartDate, Timestamp chosenEndDate, Timestamp apptStart, Timestamp apptEnd) {
        Boolean chosenStartIsBeforeApptStart = chosenStartDate.before(apptStart);
        Boolean chosenStartIsAfterApptStart = apptStart.before(chosenStartDate);
        Boolean chosenEndIsBeforeApptEnd = chosenEndDate.before(apptEnd);
        Boolean chosenEndIsAfterApptEnd = apptEnd.before(chosenEndDate);
        
        if(chosenStartIsAfterApptStart) {
            // If the start is after the Appt start, engulfment cannot occur
            return false;
        }
        else if(chosenEndIsBeforeApptEnd) {
            // If the chosenEnd is before the Appt end, engulfment cannot occur
            return false;
        }
        else if(chosenStartIsBeforeApptStart && chosenEndIsAfterApptEnd) {
            // Engulfment has occurred!
            return true;
        } else return false;
    }
 

    
    
    
    // --------------------------------------------------------
    // Clear Scene Methods
    // --------------------------------------------------------

    /**
     * This method clears all input controls, sets the appointmentToUpdate value to null,
     * and sets the application currentAppointment value to null
     */
    
    public void clear() {
        this.clearAllInputs();
        this.appointmentToUpdate = null;
        AppointmentsMainSceneController.setCurrentAppointment(null);
    }
    
    /**
     *  This method clears the input controls for the endStartDate
     */
    public void clearEndTime() {
        this.setErrorMessage("");
        if(this.endTimeComboBox.getValue() != null) {
//            this.validateEndTimeChoice();
        }
    }
    
    /**
     * This method clears the value for the provided TextField
     * 
     * @param field the provided TextField to clear the value
     */
    private void clearField(TextField field) {
        field.clear();
    }
    
    /**
     * This method clears the value for the provided TextArea
     * 
     * @param ta the provided TextArea to clear the value
     */
    private void clearTextArea(TextArea ta) {
        ta.clear();
    }
    
    /**
     * This method clears the value of the provided ComboBox
     * 
     * @param cb the provided ComboBox to clear the value
     */
    private void clearComboBox(ComboBox cb) {
        cb.setValue(null);
    }
    
    /**
     * This method clears all input controls
     */
    private void clearAllInputs() {
        this.clearField(this.apptIDField);
        this.clearField(this.titleField);
        this.clearTextArea(this.descriptionField);
        this.clearField(this.locationField);
        this.clearField(this.typeField);
        this.clearComboBox(this.customerComboBox);
        this.clearComboBox(this.contactComboBox);
        this.clearComboBox(this.userComboBox);
    }
    
    /**
     * Sets the errorMessage label to the value of the provided String message
     * 
     * @param message the provided String to set the errorMessageLabel value
     */
    private void setErrorMessage(String message) {
        this.createNewButton.setDisable(false);
        this.updateButton.setDisable(false);
        this.errorMessageLabel.setText(message);
    }
    
    /**
     * Clears the errorMessageLabel value
     */
    private void clearErrorMessage() {
        this.createNewButton.setDisable(false);
        this.updateButton.setDisable(false);
        this.errorMessageLabel.setText("");
    }
    
    
}
