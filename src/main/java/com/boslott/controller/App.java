package com.boslott.controller;

import com.boslott.DataAccess.UCertdbDAOFactory;
import com.boslott.model.User;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * JavaFX App
 */
public class App extends Application {

    /**
     * The application Stage object
     */
    private static Stage mainStage;
    
    /**
     * The application SceneMediator object used to control the UI Scene
     */
    private static SceneMediator sceneMediator;
    
    /**
     * The application UCertDAOFactory object used to obtain
     * UCertdbDAO objects
     */
    private static UCertdbDAOFactory daoFactory;
    
    /**
     * A toggle of whether or not to show a message on the Customer Scene
     */
    private static Boolean hasMessageForCustomerScene = false;
    
    /**
     * The message to show on the Customer Scene
     */
    private static String messageForCustomerScene = null;
    
    /**
     * A toggle of whether or not to show a message on the Appointment Scene
     */
    private static Boolean hasMessageForAppointmentScene = false;
    
    /**
     * The message to show on the Appointment Scene
     */
    private static String messageForAppointmentScene = null;
    
    /**
     * The User object containing the property values of the User currently
     * logged into the application
     */
    private static User currentUser = null;

    /**
     * Starts the application, sets the main application Stage object, creates
     * a new SceneMediator object, and creates a new UCertdbFactory object
     * 
     * @param stage the Stage object to set the application main Stage to
     * @throws IOException if there is an FXML loading error for the Scene
     * @throws SQLException if there is a database access error
     */
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        mainStage = stage;
        sceneMediator = new SceneMediator();
        daoFactory = new UCertdbDAOFactory();
        
    }
    
    /**
     * Gets the currentUser property value
     *
     * @return a User object containing the property value for currentUser
     */
    public static User getCurrentUser() {
        return currentUser;
    }
    
    /**
     * Sets the currentUser property to the provided newUser value
     *
     * @param newUser the User object to set the currentUser property to
     */
    public static void setCurrentUser(User newUser) {
        currentUser = newUser;
    }
    
    /**
     * Returns whether or not to show a message on the Customer Scene
     *
     * @return true if there is a message to show, false if there is not a message
     */
    public static Boolean getHasMessageForCustomerScene() {
        return hasMessageForCustomerScene;
    }
    
    /**
     * Gets the message to show on the Customer Scene
     *
     * @return the message to show on the Customer Scene
     */
    public static String getMessageForCustomerScene() {
        return messageForCustomerScene;
    }
    
    /**
     * Gets whether or not to show a message on the Appointment Scene
     *
     * @return true if there is a message to show, false if there is not a message
     * to show
     */
    public static Boolean getHasMessageForAppointmentScene() {
        return hasMessageForAppointmentScene;
    }
    
    /**
     * Gets the message to show on the Appointment Scene
     *
     * @return the message to show on the Appointment Scene
     */
    public static String getMessageForAppointmentScene() {
        return messageForAppointmentScene;
    }
    
    /**
     * Sets the property value for whether or not to show a message on the 
     * Customer Scene
     *
     * @param hasMessage the value to set the hasMessage property to
     */
    public static void setHasMessageForCustomerScene(Boolean hasMessage) {
        hasMessageForCustomerScene = hasMessage;
    }
    
    /**
     * Sets the message to show on the Customer Scene
     *
     * @param message the value of the message to set 
     */
    public static void setMessageForCustomerScene(String message) {
        messageForCustomerScene = message;
    }
    
    /**
     * Sets the property value of whether or not to show a message on the
     * Appointment Scene
     *
     * @param hasMessage whether or not there is a message
     */
    public static void setHasMessageForAppointmentScene(Boolean hasMessage) {
        hasMessageForAppointmentScene = hasMessage;
    }
    
    /**
     * Sets the message to show on the Appointment Scene
     *
     * @param message the value of the message to set
     */
    public static void setMessageForAppointmentScene(String message) {
        messageForAppointmentScene = message;
    }
    
    /**
     * Sets the application's mainStage property to the Scene value of the
     * provided Scene object
     *
     * @param scene the Scene object to set the application main Stage to
     */
    public static void setMainStageScene(Scene scene) {
        mainStage.setScene(scene);
    }
    
    /**
     * Shows the application mainStage
     */
    public static void showMainStage() {
        mainStage.show();
    }

    /**
     *  Closes the application Stage
     */
    public static void closeMainStage() {
        mainStage.close();
    }

    /**
     * The application main method
     *
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
    
    /**
     * Gets the application UCertdbDAOFactory object
     *
     * @return the application UCertdbDAOFactory object
     */
    public static UCertdbDAOFactory getUCertdbDAOFactory() {
        return daoFactory;
    }
    

}