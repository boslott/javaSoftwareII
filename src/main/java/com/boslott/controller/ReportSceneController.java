
package com.boslott.controller;

import com.boslott.DataAccess.UCertdbAppointmentDAO;
import com.boslott.DataAccess.UCertdbContactDAO;
import com.boslott.DataAccess.UCertdbCountryDAO;
import com.boslott.DataAccess.UCertdbCustomerDAO;
import com.boslott.DataAccess.UCertdbDivisionDAO;
import com.boslott.model.Appointment;
import com.boslott.model.Contact;
import com.boslott.model.Country;
import com.boslott.model.Customer;
import com.boslott.model.Division;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * FXML Controller class for the Reports Scene
 *
 * @author boslott
 */
public class ReportSceneController implements Initializable {
    
    @FXML Button backToDashboardButton;
    @FXML ComboBox chooseReportComboBox;
    @FXML TableView totalApptTableView;
    @FXML Label reportTitleLabel;
    @FXML VBox reportResultsVBox;
    @FXML ScrollPane reportScrollPane;
    
    private UCertdbAppointmentDAO apptDAO;
    private UCertdbContactDAO contactDAO;
    private UCertdbCustomerDAO customerDAO;
    private UCertdbDivisionDAO divisionDAO;
    private UCertdbCountryDAO countryDAO;
    private ArrayList<Appointment> allAppointments;
    private ArrayList<Contact> allContacts;
    private ArrayList<Customer> allCustomers;
    private ArrayList<Division> allDivisions;
    private ArrayList<Country> allCountries;
    private HashMap<String, Integer> apptTypes;
    private HashMap<String, Integer> apptByMonths;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.apptDAO = App.getUCertdbDAOFactory().getAppointmentDAO();
            this.contactDAO = App.getUCertdbDAOFactory().getContactDAO();
            this.customerDAO = App.getUCertdbDAOFactory().getCustomerDAO();
            this.divisionDAO = App.getUCertdbDAOFactory().getDivisionDAO();
            this.countryDAO = App.getUCertdbDAOFactory().getCountryDAO();
        } catch (SQLException ex) {
            Logger.getLogger(ReportSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.reportScrollPane.pannableProperty().set(true);
        this.allAppointments = this.apptDAO.findAllAppointments(true);
        this.allContacts = this.contactDAO.findAllContacts();
        this.allCustomers = this.customerDAO.findAllCustomers(true);
        this.allDivisions = this.divisionDAO.findAllDivisions();
        this.allCountries = this.countryDAO.findAllCountries();
        this.apptTypes = new HashMap<>();
        this.apptByMonths = new HashMap<>();
        this.populateChooseComboBox();
        this.countAppointmentsByType();
        this.countAppointmentsByMonth();
    }   
    
    /**
     * Changes the application main Stage Scene to the Dashboard Main Scene
     *
     * @throws IOException if there is an FXML loading error
     */
    public void goBackToDashboard() throws IOException {
        SceneMediator.changeScene("DashboardMain");
    }
    
    /**
     * Populates the ChooseComboBox with user choices of Report Types
     */
    private void populateChooseComboBox() {
        ObservableList<String> ob = FXCollections.observableArrayList();
        
        ob.add("Total Appointments By Type");
        ob.add("Total Appointments By Month");
        ob.add("All Contacts - All Schedules");
        ob.add("Customers By Country Division");
        
        this.chooseReportComboBox.setItems(ob);
        this.chooseReportComboBox.setPromptText("--- Choose Report ---");
    }
    
    /**
     * Calls the respective method for the user choice 
     */
    public void chooseReport() {
        switch(this.chooseReportComboBox.getValue().toString()) {
            case "Total Appointments By Type":
                this.chooseAppointmentByTypeReport();
                break;
            case "Total Appointments By Month":
                this.chooseAppointmentsByMonthReport();
                break;
            case "All Contacts - All Schedules":
                this.chooseAllSchedulesReport();
                break;
            case "Customers By Country Division":
                this.chooseCustomersByDivisionReport();
                break;
            default:
                break;
        }
    }
    
    /**
     * Creates the Customers By Division report. This method first determines
     * how many Customer entries are related to each Division entry. Then this
     * method creates the necessary UI features to display the data.
     */
    private void chooseCustomersByDivisionReport() {
        this.reportResultsVBox.getChildren().clear();
        this.reportTitleLabel.setText("Total Customers By Country Division");
        this.createHeaderRow("Division, Country", "Total Customers");
        
        for(int i = 0; i < this.allDivisions.size(); i++) {
            // For this Division, need a key == DivisionName, CountryName
            String divisionName = this.allDivisions.get(i).getDivision();
            String countryName = null;
            for(int j = 0; j < this.allCountries.size(); j++) {
                if(this.allCountries.get(j).getCountryID() == this.allDivisions.get(i).getCountryID()) {
                    countryName = this.allCountries.get(j).getCountry();
                }
            }
            
            // How many customers live in this division?
            Integer customersInDivision = 0;
            for(int k = 0; k < this.allCustomers.size(); k++) {
                if(this.allCustomers.get(k).getDivisionID() == this.allDivisions.get(i).getDivisionID()) {
                    customersInDivision += 1;
                }
            }
            
            this.populateNewScheduleRow(divisionName + ", " + countryName, customersInDivision.toString());
        }
        
    }
    
    /**
     * Creates the Appointments By Type report
     */
    private void chooseAppointmentByTypeReport() {
        this.reportResultsVBox.getChildren().clear();
        this.createHeaderRow("Appt Type", "Num Appts");
        this.reportTitleLabel.setText("Total Appointments By Type");
        
        this.populateResultsVBox(this.apptTypes);
    }
    
    /**
     * Creates the Appointments By Month report
     */
    private void chooseAppointmentsByMonthReport() {
        this.reportResultsVBox.getChildren().clear();
        this.createHeaderRow("Month", "Num Appts");
        this.reportTitleLabel.setText("Total Appointments By Month");
        
        this.populateResultsVBox(this.apptByMonths);
    }
    
    /**
     * This method creates a row with two columns, respectively labeled col1Header
     * and col2Header
     * 
     * @param col1Header the value to set the col1Header Label text property
     * @param col2Header the value to set the col2Header Label text property
     */
    private void createHeaderRow(String col1Header, String col2Header) {
        HBox newRow = new HBox();
        newRow.setPrefSize(900, 30);
        newRow.setLayoutY(0);
        newRow.setAlignment(Pos.CENTER);
        
        HBox newFirstHalfOfRow = new HBox();
        newFirstHalfOfRow.setPrefSize(450.0, 30.0);
        newFirstHalfOfRow.setLayoutY(0);
        newFirstHalfOfRow.setLayoutX(0);
        newFirstHalfOfRow.setAlignment(Pos.CENTER);

        // Create the second half of the row
        HBox newSecondHalfOfRow = new HBox();
        newSecondHalfOfRow.setPrefSize(450.0, 30.0);
        newSecondHalfOfRow.setLayoutY(0);
        newSecondHalfOfRow.setLayoutX(450);
        newSecondHalfOfRow.setAlignment(Pos.CENTER);
        
        Label col1Label = new Label(col1Header);
        col1Label.setPadding(new Insets(0, 20, 0, 20));
        col1Label.setFont(new Font("Arial", 24));
        newFirstHalfOfRow.getChildren().add(col1Label);
        
        Label col2Label = new Label(col2Header);
        col2Label.setPadding(new Insets(0, 20, 0, 20));
        col2Label.setFont(new Font("Arial", 24));
        newSecondHalfOfRow.getChildren().add(col2Label);
        
        newRow.getChildren().add(newFirstHalfOfRow);
        newRow.getChildren().add(newSecondHalfOfRow);
        
        this.reportResultsVBox.getChildren().add(newRow);
    }
    
    /**
     * This method creates the display for the report results
     * 
     * @param map the HashMap of data to populate the VBox with
     */
    private void populateResultsVBox(HashMap<String, Integer> map) {
        
        int index = 0;
        
        for(String key : map.keySet()) {
            // Create an HBox
            HBox newRow = new HBox();
            newRow.setPrefSize(900.0, 38.0);
            newRow.setLayoutY((40 + (index * 40)));
            newRow.setAlignment(Pos.CENTER);
            
            // For layout ease, use two halves for each row
            // Create the first half of the row
            HBox newFirstHalfOfRow = new HBox();
            newFirstHalfOfRow.setPrefSize(450.0, 38.0);
            newFirstHalfOfRow.setLayoutY((40 + (index * 40)));
            newFirstHalfOfRow.setAlignment(Pos.CENTER);
            
            // Create the second half of the row
            HBox newSecondHalfOfRow = new HBox();
            newSecondHalfOfRow.setPrefSize(450.0, 38.0);
            newSecondHalfOfRow.setLayoutY((40 + (index * 40)));
            newSecondHalfOfRow.setLayoutX(450);
            newSecondHalfOfRow.setAlignment(Pos.CENTER);
            
            
            // Create and Insert key label
            Label col1Value = new Label();
            col1Value.setPadding(new Insets(0, 20, 0, 20));
            col1Value.setFont(new Font("Arial", 18));
            col1Value.setText(key);
            newFirstHalfOfRow.getChildren().add(col1Value);
            
            
            // Create and Insert value label
            Label col2Value = new Label();
            col2Value.setPadding(new Insets(0, 20, 0, 20));
            col2Value.setFont(new Font("Arial", 18));
            col2Value.setText(Integer.toString(map.get(key)));
            newSecondHalfOfRow.getChildren().add(col2Value);
            
            newRow.getChildren().add(newFirstHalfOfRow);
            newRow.getChildren().add(newSecondHalfOfRow);
            
            
            // Insert the HBox into the VBox
            this.reportResultsVBox.getChildren().add(newRow);
            
            
            // Finally increase the index
            index += 1;
        }
    }
    
    /**
     * Creates and displays the Schedules Report
     */
    private void chooseAllSchedulesReport() {
        this.reportResultsVBox.getChildren().clear();
        this.reportTitleLabel.setText("All Schedules");
        
        for(int i = 0; i < this.allContacts.size(); i++) {
            this.populateNewContactTitleRow(this.allContacts.get(i));
            for(int j = 0; j < this.allAppointments.size(); j++) {
                if(this.allAppointments.get(j).getContactID() == this.allContacts.get(i).getContactID()) {
                    Appointment appt = this.allAppointments.get(j);
                    LocalDateTime apptStart = LocalDateTime.ofInstant(appt.getStartDateTime().toInstant(), ZoneOffset.UTC);
                    LocalDateTime apptEnd = LocalDateTime.ofInstant(appt.getEndDateTime().toInstant(), ZoneOffset.UTC);
                    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMM d yyyy hh:mm");
                    
                    this.populateNewScheduleRow("Appointment: ", Integer.toString(appt.getAppointmentID()));
                    this.populateNewScheduleRow("Title: ", appt.getTitle());
                    this.populateNewScheduleRow("Type: ", appt.getType());
                    this.populateNewScheduleRow("Description: ", appt.getDescription());
                    this.populateNewScheduleRow("Start: ", apptStart.format(dateFormat));
                    this.populateNewScheduleRow("End: ", apptEnd.format(dateFormat));
                    this.populateNewScheduleRow("CustomerID: ", Integer.toString(appt.getCustomerID()));
                    this.populateFinalScheduleRow();
                }
            }
        }
    }
    
    /**
     * This method creates a new title row for the Contact section of the Schedules
     * Report
     * 
     * @param contact the Contact object holding the Contact property values
     */
    private void populateNewContactTitleRow(Contact contact) {
        HBox newRow = new HBox();
        newRow.setPrefSize(900, 38);
//        newRow.setLayoutY(0);
        newRow.setAlignment(Pos.CENTER);
        
        Label contactNameLabel = new Label("Scedule For " + contact.getContactName());
        contactNameLabel.setFont(new Font("Arial", 24));
        
        newRow.getChildren().add(contactNameLabel);
        this.reportResultsVBox.getChildren().add(newRow);
    }
    
    /**
     * This method creates and formats the a specific row for the Schedules Report
     */
    private void populateFinalScheduleRow() {
        HBox newRow = new HBox();
        newRow.setPrefSize(900, 20);
        newRow.setAlignment(Pos.CENTER);
        
        Label label = new Label("------------------------");
        label.setPadding(new Insets(10, 0, 10, 0));
        label.setFont(new Font("Arial", 18));
        
        newRow.getChildren().add(label);
        this.reportResultsVBox.getChildren().add(newRow);
    }
    
    /**
     * This method creates and formats the a specific row for the Schedules Report
     * 
     * @param key the property key
     * @param value the property value
     */
    private void populateNewScheduleRow(String key, String value) {
        HBox newRow = createNewFullRow(key, value);
        this.reportResultsVBox.getChildren().add(newRow);
    }
    
    /**
     * This method creates and formats the a specific row for the Schedules Report
     * 
     * @param keyLabelText the text value to set the key Label text property to
     * @param valueLabelText the text value to set the valueLabel text property to
     * @return a newly created and formatted HBox
     */
    private HBox createNewFullRow(String keyLabelText, String valueLabelText) {
        // Create an HBox
            HBox newRow = new HBox();
            newRow.setPrefSize(900.0, 38.0);
            newRow.setLayoutY(0);
            newRow.setAlignment(Pos.CENTER);
            
            // For layout ease, use two halves for each row
            // Create the first half of the row
            HBox newFirstHalfOfRow = new HBox();
            newFirstHalfOfRow.setPrefSize(450.0, 38.0);
            newFirstHalfOfRow.setLayoutY(0);
            newFirstHalfOfRow.setAlignment(Pos.CENTER);
            
            // Create the second half of the row
            HBox newSecondHalfOfRow = new HBox();
            newSecondHalfOfRow.setPrefSize(450.0, 38.0);
            newSecondHalfOfRow.setLayoutY(0);
            newSecondHalfOfRow.setLayoutX(450);
            newSecondHalfOfRow.setAlignment(Pos.CENTER);
            
            Label keyLabel = new Label(keyLabelText);
            keyLabel.setFont(new Font("Arial", 18));
            keyLabel.setPadding(new Insets(0, 20, 0, 20));
            newFirstHalfOfRow.getChildren().add(keyLabel);
            
            Label valueLabel = new Label(valueLabelText);
            valueLabel.setFont(new Font("Arial", 18));
            valueLabel.setPadding(new Insets(0, 20, 0, 20));
            newSecondHalfOfRow.getChildren().add(valueLabel);
            
            newRow.getChildren().add(newFirstHalfOfRow);
            newRow.getChildren().add(newSecondHalfOfRow);
            
            return newRow;
    }
    
    /**
     * Gets all Appointment data and aggregates how many of each time of Appointment
     */
    private void countAppointmentsByType() {
        for(int i = 0; i < this.allAppointments.size(); i++) {
            Boolean keyExists = false;
            String type = this.allAppointments.get(i).getType();
            for(String key : this.apptTypes.keySet()) {
                if(type.equals(key)) {
                    keyExists = true;
                }
            }
            if(keyExists) this.apptTypes.put(type, this.apptTypes.get(type) + 1);
            else this.apptTypes.put(type, 1);
        }
        
        
    }
    
    /**
     * Gets all Appointment data and aggregates how many Appointments are in each month
     * of the current year
     */
    private void countAppointmentsByMonth() {
        for(int i = 0; i < this.allAppointments.size(); i++) {
            Instant startDateTime = this.allAppointments.get(i).getStartDateTime().toInstant();
            LocalDateTime localStart = LocalDateTime.ofInstant(startDateTime, ZoneOffset.UTC);
            Integer month = localStart.getMonthValue();
            switch(month) {
                case 1:
                    if(this.apptByMonths.containsKey("January")) this.apptByMonths.put("January", (this.apptByMonths.get("January") + 1));
                    else this.apptByMonths.put("January", 1);
                    break;
                case 2:
                    if(this.apptByMonths.containsKey("February")) this.apptByMonths.put("February", (this.apptByMonths.get("February") + 1));
                    else this.apptByMonths.put("February", 1);
                    break;
                case 3:
                    if(this.apptByMonths.containsKey("March")) this.apptByMonths.put("March", (this.apptByMonths.get("March") + 1));
                    else this.apptByMonths.put("March", 1);
                    break;
                case 4:
                    if(this.apptByMonths.containsKey("April")) this.apptByMonths.put("April", (this.apptByMonths.get("April") + 1));
                    else this.apptByMonths.put("April", 1);
                    break;
                case 5:
                    if(this.apptByMonths.containsKey("May")) this.apptByMonths.put("May", (this.apptByMonths.get("May") + 1)); 
                    else this.apptByMonths.put("May", 1);
                    break;
                case 6:
                    if(this.apptByMonths.containsKey("June")) this.apptByMonths.put("June", (this.apptByMonths.get("June") + 1));
                    else this.apptByMonths.put("June", 1);
                    break;
                case 7:
                    if(this.apptByMonths.containsKey("July")) this.apptByMonths.put("July", (this.apptByMonths.get("July") + 1));
                    else this.apptByMonths.put("July", 1);
                    break;
                case 8:
                    if(this.apptByMonths.containsKey("August")) this.apptByMonths.put("August", (this.apptByMonths.get("August") + 1));
                    else this.apptByMonths.put("August", 1);
                    break;
                case 9:
                    if(this.apptByMonths.containsKey("September")) this.apptByMonths.put("September", (this.apptByMonths.get("September") + 1));
                    else this.apptByMonths.put("September", 1);
                    break;
                case 10:
                    if(this.apptByMonths.containsKey("October")) this.apptByMonths.put("October", (this.apptByMonths.get("October") + 1));
                    else this.apptByMonths.put("October", 1);
                    break;
                case 11:
                    if(this.apptByMonths.containsKey("November")) this.apptByMonths.put("November", (this.apptByMonths.get("November") + 1));
                    else this.apptByMonths.put("November", 1);
                    break;
                case 12:
                    if(this.apptByMonths.containsKey("December")) this.apptByMonths.put("December", (this.apptByMonths.get("December") + 1));
                    else this.apptByMonths.put("December", 1);
                    break;
                default:
                    break;
            }
        }
    }
    
    
    
}
