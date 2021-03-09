
package com.boslott.controller;

import com.boslott.DataAccess.UCertdbAppointmentDAO;
import com.boslott.DataAccess.UCertdbUserDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class for the Dashboard Main Scene
 *
 * @author Steven Slott
 */
public class DashboardMainController implements Initializable {
    
    @FXML Button toCustomersButton;
    @FXML Button toAppointmentsButton;
    @FXML Button toReportsButtons;
    @FXML Button logoutButton;
    @FXML Label helloNameLabel;
    @FXML Label soonApptIDLabel;
    @FXML Label soonApptDateLabel;
    @FXML Label soonApptTimeLabel;
    @FXML Label soonApptIDTitleLabel;
    @FXML Label soonApptDateTitleLabel;
    @FXML Label soonApptTimeTitleLabel;
    @FXML Label haveApptLabel;
    
    private UCertdbAppointmentDAO apptDAO;
    private UCertdbUserDAO userDAO;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.apptDAO = App.getUCertdbDAOFactory().getAppointmentDAO();
            this.userDAO = App.getUCertdbDAOFactory().getUserDAO();
            this.haveApptLabel.setText("");
            if(this.soonApptIDTitleLabel != null) this.soonApptIDTitleLabel.setText("");
            if(this.soonApptDateTitleLabel != null) this.soonApptDateTitleLabel.setText("");
            if(this.soonApptTimeTitleLabel != null) this.soonApptTimeTitleLabel.setText("");
        } catch (SQLException ex) {
            Logger.getLogger(DashboardMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.welcomeTheUser();
        this.checkForUpcomingAppointments();
    }    
    
    /**
     * Gets the application currentUser userName property and sets the 
     * helloNameLabel Label to its value
     */
    private void welcomeTheUser() {
        this.helloNameLabel.setText(App.getCurrentUser().getUserName());
    }
    
    /**
     * Gets all the Appointment entries from the UCertify database data source,
     * loops through the ArrayList of Appointment objects, compares only the 
     * Appointment objects related to the application currentUser, and compares
     * the Appointment objects' startDateTimes with the current Local time. If an
     * Appointment object has a startDateTime within 15 minutes of now, a message
     * with Appointment information is displayed on the Dashboard Main Scene. If an
     * Appointment cannot be found within 15 of now, a different message stating
     * as such is displayed
     * 
     * A lambda expression is used as the Consumer for the ArrayList.forEach method
     * in order to compare the userID related to each Appointment with the userID
     * of the currently logged-in user
     */
    private void checkForUpcomingAppointments() {
        LocalDateTime rightNow = LocalDateTime.now();
        this.apptDAO.findAllAppointments(true).forEach(appt -> {
            if(appt.getUserID() == App.getCurrentUser().getUserID()) {
                
                Timestamp localStartTime = AppointmentsUpdateSceneController.utcToLocalTimestamp(appt.getStartDateTime());
                LocalDateTime apptStartLDT = LocalDateTime.ofInstant(localStartTime.toInstant(), ZoneId.systemDefault());
                LocalDateTime nowLDT = LocalDateTime.now();
                DateTimeFormatter apptDateFormat = DateTimeFormatter.ofPattern("MMM d yyyy");
                DateTimeFormatter apptTimeFormat = DateTimeFormatter.ofPattern("hh:mm");
                long minutes = ChronoUnit.MINUTES.between(nowLDT, apptStartLDT);
                
                if(Math.abs(minutes) <= 14) {
                    if(minutes > 0) {
                        // Update Dashboard Scene with appt information
                        this.haveApptLabel.setText("There is an Appointment soon!");
                        this.soonApptIDTitleLabel.setText("APPT ID:");
                        this.soonApptDateTitleLabel.setText("DATE:");
                        this.soonApptTimeTitleLabel.setText("TIME:");
                        this.soonApptIDLabel.setText(Integer.toString(appt.getAppointmentID()));
                        this.soonApptDateLabel.setText(apptStartLDT.format(apptDateFormat));
                        this.soonApptTimeLabel.setText(apptStartLDT.format(apptTimeFormat));
                        return;
                    }
                    else {
                        this.haveApptLabel.setText("There is currently an Appointment that started\n" + Math.abs(minutes) + " minutes ago!\n");
                        this.soonApptIDTitleLabel.setText("APPT ID:");
                        this.soonApptDateTitleLabel.setText("DATE:");
                        this.soonApptTimeTitleLabel.setText("TIME:");
                        this.soonApptIDLabel.setText(Integer.toString(appt.getAppointmentID()));
                        this.soonApptDateLabel.setText(apptStartLDT.format(apptDateFormat));
                        this.soonApptTimeLabel.setText(apptStartLDT.format(apptTimeFormat));
                        return;
                    }
                } else {
                    if(this.haveApptLabel != null) this.haveApptLabel.setText("There are not any upcoming Appointments");
                    if(this.soonApptIDTitleLabel != null) this.soonApptIDTitleLabel.setText("");
                    if(this.soonApptDateTitleLabel != null) this.soonApptDateTitleLabel.setText("");
                    if(this.soonApptTimeTitleLabel != null) this.soonApptTimeTitleLabel.setText("");
                    if(this.soonApptIDLabel != null) this.soonApptIDLabel.setText("");
                    else this.soonApptIDLabel.setText("");
                    if(this.soonApptDateLabel != null) this.soonApptDateLabel.setText("");
                    else this.soonApptDateLabel.setText("");
                    if(this.soonApptTimeLabel != null) this.soonApptTimeLabel.setText("");
                    else this.soonApptTimeLabel.setText("");
                }
            
            }
            else {
                if(this.haveApptLabel != null) this.haveApptLabel.setText("There are not any upcoming Appointments");
                if(this.soonApptIDTitleLabel != null) this.soonApptIDTitleLabel.setText("");
                if(this.soonApptDateTitleLabel != null) this.soonApptDateTitleLabel.setText("");
                if(this.soonApptTimeTitleLabel != null) this.soonApptTimeTitleLabel.setText("");
                if(this.soonApptIDLabel != null) this.soonApptIDLabel.setText("");
                else this.soonApptIDLabel.setText("");
                if(this.soonApptDateLabel != null) this.soonApptDateLabel.setText("");
                else this.soonApptDateLabel.setText("");
                if(this.soonApptTimeLabel != null) this.soonApptTimeLabel.setText("");
                else this.soonApptTimeLabel.setText("");
            }
        });
    }
    
    /**
     * Changes the application main Stage Scene to the Customers Main Scene
     *
     * @throws IOException if there is an FXML loading error
     */
    public void goToCustomers() throws IOException {
        SceneMediator.changeScene("CustomersMainScene");
    }
    
    /**
     * Changes the application main Stage Scene to the Appointments Main Scene
     *
     * @throws IOException if there is an FXML loading error
     */
    public void goToAppointments() throws IOException {
        SceneMediator.changeScene("AppointmentsMainScene");
    }
    
    /**
     * Changes the application main Stage Scene to the Report Scene
     *
     * @throws IOException if there is an FXML loading error
     */
    public void goToReports() throws IOException {
        SceneMediator.changeScene("ReportScene");
    }
    
    /**
     * Changes the application main Stage Scene to the Login Scene
     *
     * @throws IOException if there is an FXML loading error
     */
    public void logout() throws IOException {
        SceneMediator.changeScene("LoginScene");
    }
    
}
