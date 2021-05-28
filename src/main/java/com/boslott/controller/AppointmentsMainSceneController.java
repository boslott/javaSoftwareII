
package com.boslott.controller;

import com.boslott.DataAccess.UCertdbAppointmentDAO;
import com.boslott.DataAccess.UCertdbContactDAO;
import com.boslott.DataAccess.UCertdbCustomerDAO;
import com.boslott.model.Appointment;
import com.boslott.model.Customer;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;


/**
 * FXML Controller class for the Appointments Main Scene
 *
 * @author Steven Slott
 */
public class AppointmentsMainSceneController implements Initializable {
    
    /**
     * Button to filter to show all Appointment objects
     */
    @FXML RadioButton allApptsRadioButton;
    
    /**
     * Button to filter to show only Appointment objects that have a startDateTime
     * within the same current Month
     */
    @FXML RadioButton byMonthRadioButton;
    
    /**
     * Button to filter to show only Appointment objects that have a startDateTime
     * within the same 7-day week span as the current day
     */
    @FXML RadioButton byWeekRadioButton;
    
    /**
     * The display table to show all Appointment data
     */
    @FXML TableView appointmentsTableView;
    
    /**
     * Button to click to change to the Appointments Update Scene
     */
    @FXML Button modifyApptButton;
    
    /**
     * Button to click to return to the Main Dashboard Scene
     */
    @FXML Button backToDashboardButton;
    
    /**
     * The label to modify if there is a message to display on the Appointments Scene
     */
    @FXML Label messageLabel;
    
    private UCertdbAppointmentDAO appointmentDAO;
    private UCertdbCustomerDAO customerDAO;
    private UCertdbContactDAO contactDAO;
    private ArrayList<Appointment> filteredAppointments = new ArrayList<>();
    private static Appointment currentAppointment = null;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.appointmentDAO = App.getUCertdbDAOFactory().getAppointmentDAO();
            this.customerDAO = App.getUCertdbDAOFactory().getCustomerDAO();
            this.contactDAO = App.getUCertdbDAOFactory().getContactDAO();
            this.updateAppointmentSceneMessage("");
            if(App.getHasMessageForAppointmentScene()) {
                this.updateAppointmentSceneMessage(App.getMessageForAppointmentScene());
                App.setHasMessageForAppointmentScene(false);
                App.setMessageForAppointmentScene("");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentsMainSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.populateTable();
        this.selectRadioButton("all");
        this.appointmentsTableView.setOnMouseClicked(e -> {
            this.selectThisAppointmentToModify(e);
        });
        
    }    
    
    private void updateAppointmentSceneMessage(String message) {
        this.messageLabel.setText(message);
    }
    
    /**
     * Changes the application mainStage Scene to the Appointments Update Scene
     *
     * @throws IOException if there is an FXML loading error
     */
    public void modifyAppt() throws IOException {
        SceneMediator.changeScene("AppointmentsUpdateScene");
    }
    
    /**
     * Changes the application mainStage Scene to the Dashboard Main Scene
     *
     * @throws IOException if there is an FXML loading error
     */
    public void goBackToDashboard() throws IOException {
        SceneMediator.changeScene("DashboardMain");
    }
    
    /**
     * Gets the value of the currentAppointment property
     *
     * @return the Appointment object containing the property values of the
     * currentAppointment object
     */
    public static Appointment getCurrentAppointment() {
        return currentAppointment;
    }
    
    /**
     * Sets the currentAppointment property to the given Appointment value
     *
     * @param appt the Appointment object to set the currentAppointment property to
     */
    public static void setCurrentAppointment(Appointment appt) {
        currentAppointment = appt;
    }
    
    /**
     * private class method that creates the table columns and properties, and
     * populates the table with the data-less columns
     * 
     * Because setting the CellValueFactory accepts a functional interface,
     * the PropertyValueFactory Callback, lambda expressions are used to set the CellFactory
     * for the start and the end columns in order to format the the display
     * of the Timestamp values
     */
    private void populateTable() {
        
        TableColumn idColumn = new TableColumn<Customer, String>("ID");
        TableColumn titleColumn = new TableColumn<>("Title");
        TableColumn descriptionColumn = new TableColumn<>("Description");
        TableColumn locationColumn = new TableColumn<>("Location");
        TableColumn typeColumn = new TableColumn<>("Type");
        TableColumn startColumn = new TableColumn<>("Start");
        TableColumn endColumn = new TableColumn<>("End");
        TableColumn<Appointment, String> customerNameColumn = new TableColumn<>("Customer Name");
        TableColumn<Appointment, String> contactNameColumn = new TableColumn<>("Contact Name");
        
        idColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        idColumn.setStyle("-fx-text-alignment: CENTER;");
        
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.setStyle("-fx-text-alignment: CENTER;");
        
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumn.setStyle("-fx-text-alignment: CENTER;");
        
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        locationColumn.setStyle("-fx-text-alignment: CENTER;");
        
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeColumn.setStyle("-fx-text-alignment: CENTER;");
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm - MM / dd / yyyy");
        
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        startColumn.setStyle("-fx-text-alignment: CENTER;");
        startColumn.setCellFactory(col -> {
            TableCell<Appointment, Timestamp> cell = new TableCell<>() {
                @Override
                protected void updateItem(Timestamp startTime, boolean empty) {
                    super.updateItem(startTime, empty);
                    
                    setText(null);
                    setGraphic(null);
                    
                    LocalDateTime startLDT = null;
                    
                    
                    if(!empty) {
                        // Timestamp comes in at UTC value, convert to local first
                        Timestamp localStartTime = AppointmentsUpdateSceneController.utcToLocalTimestamp(startTime);
                        
                        setText(formatter.format(LocalDateTime.ofInstant(localStartTime.toInstant(), ZoneId.systemDefault())));
                        
                    }
                }
            };
            
            return cell;
        });
        
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        endColumn.setStyle("-fx-text-alignment: CENTER;");
        endColumn.setCellFactory(col -> {
            TableCell<Appointment, Timestamp> cell = new TableCell<>() {
                @Override
                protected void updateItem(Timestamp endTime, boolean empty) {
                    super.updateItem(endTime, empty);
                    
                    setText(null);
                    setGraphic(null);
                    
                    LocalDateTime endLDT = null;
                    
                    
                    if(!empty) {
                        // First convert the UTC Timestamp to Local Timestamp
                        Timestamp localEndTime = AppointmentsUpdateSceneController.utcToLocalTimestamp(endTime);
                        
                        setText(formatter.format(LocalDateTime.ofInstant(localEndTime.toInstant(), ZoneId.systemDefault())));
               
                    }
                }
            };
            
            return cell;
        });
        
        // Find the Customer, then populate the table with the Customer name
//        String custName = this.customerDAO.findCustomer();
        
//        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameColumn.setCellValueFactory((cellData) -> {
            String name = this.customerDAO.findCustomer(cellData.getValue().getCustomerID()).getCustomerName();
            return new SimpleStringProperty(name);
        });
        customerNameColumn.setStyle("-fx-text-alignment: CENTER;");
        
        contactNameColumn.setCellValueFactory((cellData) -> {
            String name = this.contactDAO.findContact(cellData.getValue().getContactID()).getContactName();
            return new SimpleStringProperty(name);
        });
        
        this.appointmentsTableView.getColumns().add(idColumn);
        this.appointmentsTableView.getColumns().add(titleColumn);
        this.appointmentsTableView.getColumns().add(descriptionColumn);
        this.appointmentsTableView.getColumns().add(locationColumn);
        this.appointmentsTableView.getColumns().add(typeColumn);
        this.appointmentsTableView.getColumns().add(startColumn);
        this.appointmentsTableView.getColumns().add(endColumn);
        this.appointmentsTableView.getColumns().add(customerNameColumn);
        this.appointmentsTableView.getColumns().add(contactNameColumn);
        
    }
    
    /**
     * Populates the table with data from the Appointment objects in the
     * filteredAppointments ArrayList
     */
    private void populateTableWithData() {
        // First empty the table
        this.appointmentsTableView.getItems().clear();
        
        // Then refill it
        for(int i = 0; i < this.filteredAppointments.size(); i++) {
            this.appointmentsTableView.getItems().add(this.filteredAppointments.get(i));
        }
    }
    
    /**
     * When the user clicks and highlights an Appointment in the Main Appointments Table,
     * the application's currentAplication property is set to the value of the 
     * selected Appointment object
     *
     * @param e the mouse click event
     */
    public void selectThisAppointmentToModify(MouseEvent e) {
            if(e.getButton().equals(MouseButton.PRIMARY)) {
                int index = this.appointmentsTableView.getSelectionModel().getSelectedIndex();
                if(index >= 0) {
                    currentAppointment = this.appointmentDAO.findAllAppointments(false).get(index);
                }
            }
    }
    
    /**
     * Calls the selectRadioButton method with the all flag
     */
    public void selectAllRadioButton() {
        this.selectRadioButton("all");
    }
    
    /**
     * Calls the selectRadioButton method with the month flag
     */
    public void selectMonthRadioButton() {
        this.selectRadioButton("month");
    }
    
    /**
     * Calls the selectRadioButton method with the week flag
     */
    public void selectWeekRadioButton() {
        this.selectRadioButton("week");
    }
    
    /**
     * private class method to select a specific radio button and deselect the
     * other radio buttons
     * @param radioButtonLabel the label of the radio button to select 
     */
    private void selectRadioButton(String radioButtonLabel) {
        switch(radioButtonLabel) {
            case "all":
                this.allApptsRadioButton.setSelected(true);
                this.byMonthRadioButton.setSelected(false);
                this.byWeekRadioButton.setSelected(false);
                break;
            case "month":
                this.allApptsRadioButton.setSelected(false);
                this.byMonthRadioButton.setSelected(true);
                this.byWeekRadioButton.setSelected(false);
                break;
            case "week":
                this.allApptsRadioButton.setSelected(false);
                this.byMonthRadioButton.setSelected(false);
                this.byWeekRadioButton.setSelected(true);
                break;
            default:
                break;
        }
        
        this.filterAppointments();
    }
    
    /**
     * private class method to filter the Appointment object ArrayList based on
     * the user's radio button selection
     */
    private void filterAppointments() {
        if(this.allApptsRadioButton.isSelected()) this.filterAppointmentsAll();
        else if(this.byMonthRadioButton.isSelected()) this.filterAppointmentsByMonth();
        else if(this.byWeekRadioButton.isSelected()) this.filterAppointmentsByWeek();
    }
    
    /**
     * Filters the ArrayList to display all Appointments in the table view
     */
    public void filterAppointmentsAll() {
        this.filteredAppointments = this.appointmentDAO.findAllAppointments(true);
        this.populateTableWithData();
    }
    
    /**
     * Filters the ArrayList to display only Appointments with a startDateTime
     * property value within the same current month
     * 
     * A lambda expression is used as the Consumer for the allAppointments ArrayList.forEach
     * loop in order to compare the startDateTime of each Appointment in the ArrayList
     * to the current Month. If the same, the Appointment is added  to the tmp
     * ArrayList that will be returned
     */
    public void filterAppointmentsByMonth() {
        Month thisMonth = LocalDateTime.now().getMonth();
        ArrayList<Appointment> tmpList = new ArrayList<>();
        
        this.appointmentDAO.findAllAppointments(true).forEach(appt -> {
            if(LocalDateTime.ofInstant(appt.getStartDateTime().toInstant(), ZoneId.systemDefault()).getMonth().equals(thisMonth)) tmpList.add(appt);
        });
        
        this.filteredAppointments = tmpList;
        this.populateTableWithData();
    }
    
    /**
     * Filters the ArrayList to display only Appointments with a startDateTime
     * property value within the same current week period
     */
    public void filterAppointmentsByWeek() {
        ArrayList<Appointment> tmpList = new ArrayList<>();
        LocalDateTime today = LocalDateTime.now();
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.set(today.getYear(), today.getMonthValue(), today.getDayOfMonth());
        int week = currentCalendar.get(Calendar.WEEK_OF_YEAR);
        int year = currentCalendar.get(Calendar.YEAR);
        
        this.appointmentDAO.findAllAppointments(true).forEach(appt -> {
            
            
            DateTimeFormatter formatPattern = DateTimeFormatter.ofPattern("MM-dd-yyyy");

            String formatter = formatPattern.format(today);
            System.out.println(formatter);
            // 2018-05-12
            
            
            LocalDateTime apptDate = LocalDateTime.ofInstant(appt.getStartDateTime().toInstant(), ZoneId.systemDefault());
            Calendar targetCalendar = Calendar.getInstance();
            targetCalendar.setFirstDayOfWeek(1);
            targetCalendar.set(apptDate.getYear(), apptDate.getMonthValue(), apptDate.getDayOfMonth());
            
            int targetWeek = targetCalendar.get(Calendar.WEEK_OF_YEAR);
            int targetYear = targetCalendar.get(Calendar.YEAR);
            
            if(week == targetWeek && year == targetYear) tmpList.add(appt);
        });
        
        this.filteredAppointments = tmpList;
        this.populateTableWithData();
    
    }
    
    
    
}
