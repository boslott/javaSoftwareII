
package com.boslott.controller;

import com.boslott.DataAccess.UCertdbCustomerDAO;
import com.boslott.model.Customer;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class for Customers Main Scene
 *
 * @author Steven Slott
 */
public class CustomersMainSceneController implements Initializable {
    
    @FXML TableView customerTable;
    @FXML Button modifyCustomersButton;
    @FXML Button backToDashboardButton;
    @FXML Label messageLabel;
    
    private UCertdbCustomerDAO custDAO;
    private ArrayList<Customer> customers;



    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.custDAO = App.getUCertdbDAOFactory().getCustomerDAO();
            this.getAllCustomers();
            this.initializeCustomerTable();
            if(App.getHasMessageForCustomerScene()) {
                this.updateCustomerMainSceneMessage(App.getMessageForCustomerScene());
                App.setHasMessageForCustomerScene(false);
                App.setMessageForCustomerScene("");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CustomersMainSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    /**
     * Sets the message Label on the Customer Main Scene text property to the provided value 
     *
     * @param message the new message to display on the Customers Main Scene
     */
    public void updateCustomerMainSceneMessage(String message) {
        this.messageLabel.setText(message);
    }
    
    /**
     * Gets all the Customer data from the UCertify database data source, utilizing
     * the refetch flag to be true to ensure the data is up to date
     */
    private void getAllCustomers() {
        if(this.customers == null || this.customers.isEmpty()) {
            this.customers = new ArrayList<>(this.custDAO.findAllCustomers(true));
        }
    }
    
    /**
     * Initializes the CustomerTableView by creating columns and cell property
     * factories, adds the data-less columns to the table, and populates the
     * TableView with all the Customer objects from the local Customers ArrayList
     */
    private void initializeCustomerTable() {
        
        TableColumn customerIDColumn = new TableColumn<>("ID");
        TableColumn customerNameColumn = new TableColumn<>("Name");
        TableColumn addressColumn = new TableColumn<>("Address");
        TableColumn postalCodeColumn = new TableColumn<>("Postal Code");
        TableColumn phoneColumn = new TableColumn<>("Phone");
        TableColumn createDateColumn = new TableColumn<>("Create Date");
        TableColumn createdByColumn = new TableColumn<>("Created By");
        TableColumn lastUpdateColumn = new TableColumn<>("Last Update");
        TableColumn lastUpdatedByColumn = new TableColumn("Last Updated By");
        TableColumn divisionIDColumn = new TableColumn("Division ID");
        
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerIDColumn.setStyle("-fx-text-alignment: CENTER");
        
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerNameColumn.setStyle("-fx-text-alignment: CENTER");
        
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressColumn.setStyle("-fx-text-alignment: CENTER");
        
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        postalCodeColumn.setStyle("-fx-text-alignment: CENTER");
        
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        phoneColumn.setStyle("-fx-text-alignment: CENTER");
        
        createDateColumn.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        createDateColumn.setStyle("-fx-text-alignment: CENTER");
        
        createdByColumn.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        createdByColumn.setStyle("-fx-text-alignment: CENTER");
        
        lastUpdateColumn.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        lastUpdateColumn.setStyle("-fx-text-alignment: CENTER");
        
        lastUpdatedByColumn.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        lastUpdatedByColumn.setStyle("-fx-text-alignment: CENTER");
        
        divisionIDColumn.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
        divisionIDColumn.setStyle("-fx-text-alignment: CENTER");
        
        this.customerTable.getColumns().add(customerIDColumn);
        this.customerTable.getColumns().add(customerNameColumn);
        this.customerTable.getColumns().add(addressColumn);
        this.customerTable.getColumns().add(postalCodeColumn);
        this.customerTable.getColumns().add(phoneColumn);
        this.customerTable.getColumns().add(createDateColumn);
        this.customerTable.getColumns().add(createdByColumn);
        this.customerTable.getColumns().add(lastUpdateColumn);
        this.customerTable.getColumns().add(lastUpdatedByColumn);
        this.customerTable.getColumns().add(divisionIDColumn);
        
        
        for(int i = 0; i < this.customers.size(); i++) {
            this.customerTable.getItems().add(customers.get(i));
        }
    }
    
    /**
     * Changes the application main Stage Scene to the Modify Customer Scene
     *
     * @throws IOException if there is an FXML loading error
     */
    public void modifyCustomers() throws IOException {
        SceneMediator.changeScene("CustomersUpdateScene");
    }
    
    /**
     * Changes the application main Stage Scene to the Main Dashboard Scene
     *
     * @throws IOException if there is an FXML loading error
     */
    public void goBackToDashboard() throws IOException {
        SceneMediator.changeScene("DashboardMain");
    }
    
    
}
