
package com.boslott.controller;

import com.boslott.DataAccess.UCertdbUserDAO;
import com.boslott.model.User;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class for the Login Scene
 *
 * @author Steven Slott
 */
public class LoginSceneController implements Initializable {
    
    @FXML Label mainTitleLabel;
    @FXML Label userNameLabel;
    @FXML Label passwordLabel;
    @FXML Label welcomeLabel;
    @FXML Label timeZoneLabel;
    @FXML Label localeLabel;
    @FXML Label useridErrorLabel;
    @FXML Label passwordErrorLabel;
    @FXML TextField useridField;
    @FXML TextField passwordField;
    @FXML Button loginButton;
    @FXML Button exitAppButton;
    
    private UCertdbUserDAO userDAO;
    
    /**
     * The User object used to hold the currentUser property values for the 
     * User currently logged into the application
     */
    private User currentUser;
    
    /**
     * Gets the user's system locale property value
     */
    private final Locale userLocale = Locale.getDefault();
    
    /**
     * Hold the value for whether or not to show French labels on the Login Scene
     */
    private final Boolean frenchLabels = userLocale.toString().contains("fr");

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.userDAO = new UCertdbUserDAO();
        } catch (SQLException ex) {
            Logger.getLogger(LoginSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        useridErrorLabel.setText("");
        passwordErrorLabel.setText("");
        
        this.setLabels();
        
        this.localeLabel.setText(Calendar.getInstance().getTimeZone().getDisplayName());
    }    
    
    /**
     * Sets all the Labels on the Login Scene and either displays English or French
     * depending on the user's system locale
     */
    private void setLabels() {
        if(this.frenchLabels) {
            this.mainTitleLabel.setText("connectez-vous à l'application".toUpperCase());
            this.userNameLabel.setText("Nom d'utilisateur".toUpperCase());
            this.passwordLabel.setText("le mot de passe".toUpperCase());
            this.loginButton.setText("connexion".toUpperCase());
            this.welcomeLabel.setText("Bonjour et Bienvenue!");
            this.timeZoneLabel.setText("Votre Fuseau Horaire Actuel est");
            this.exitAppButton.setText("quitter l'app".toUpperCase());
        } else {
            this.mainTitleLabel.setText("LOGIN TO APPLICATION");
            this.userNameLabel.setText("USERNAME");
            this.passwordLabel.setText("PASSWORD");
            this.loginButton.setText("LOGIN");
            this.welcomeLabel.setText("Hello and Welcome!");
            this.timeZoneLabel.setText("Your Current Time Zone Is:");
            this.exitAppButton.setText("EXIT APP");
        }
    }
    
    /**
     * Directs the process of user logging in. This method validates if the currentUser
     * property values exist in the UCertify database, validates if the currentUser
     * password property value matches that of the User password stored in the
     * UCertify database, and if a match, changes the application main Stage
     * Scene to the Dashboard Main Scene. If the user inputs an incorrect userName
     * or incorrect password, the appropriate error messages are displayed.
     *
     * @throws IOException if there is an FXML loading error
     */
    public void login() throws IOException {
        String inputtedName = this.useridField.getText();
        String inputtedPass = this.passwordField.getText();
        
        
        
        this.currentUser = userDAO.findUser(inputtedName);
        Boolean isUser = this.currentUser != null;
        Boolean isPassCorrect = this.userDAO.isCorrectPassword(inputtedName, inputtedPass);
        
        
        this.useridErrorLabel.setText("correct");
        if(isUser) {
            if(this.userDAO.isCorrectPassword(inputtedName, inputtedPass)) {
                this.passwordErrorLabel.setText("correct");
                this.logLoginAttemptData(inputtedName, LocalDateTime.now().toLocalDate().toString(), LocalDateTime.now().toLocalTime().toString(), "Successful Login Attempt");
                App.setCurrentUser(this.currentUser);
                SceneMediator.changeScene("DashboardMain");    
            } else {
                // Password is incorrect
                this.logLoginAttemptData(inputtedName, LocalDateTime.now().toLocalDate().toString(), LocalDateTime.now().toLocalTime().toString(), "Unsuccessful Login Attempt - Incorrect Password");
                this.passwordErrorLabel.setText(this.frenchLabels ? "Le mot de passe est incorrect" : "Password is incorrect");
            }
        } else {
            // Username is incorrect
            this.logLoginAttemptData(inputtedName, LocalDateTime.now().toLocalDate().toString(), LocalDateTime.now().toLocalTime().toString(), "Unsuccessful Login Attempt - Incorrect Username");
            this.useridErrorLabel.setText(this.frenchLabels ? "Le nom d'utilisateur est incorrect" : "Username is incorrect");
            this.passwordErrorLabel.setText("");
        }
        
    }
    
    /**
     * Closes and exits the application
     */
    public void exitApp() {
        App.closeMainStage();
        Platform.exit();
    }
    
    /**
     * Writes to a file every login attempt with the login data
     * 
     * @param userName the text inputted as the userName
     * @param date the current date
     * @param time the current time
     * @param attemptStatus the status of the login attempt
     * @throws FileNotFoundException  if the file cannot be found
     */
    private void logLoginAttemptData(String userName, String date, String time, String attemptStatus) throws FileNotFoundException {
        
        try {
            
            File userLogFile = new File("login_activity.txt");
            
            // Create the file if it does not already exist
            if (userLogFile.createNewFile()) {
              System.out.println("File created: " + userLogFile.getName());
            } else {
              System.out.println("File already exists.");
            }
            
            
            // Create the log string to write to the file
            String logString = "Login Attempt: ";
            logString += date + ", at ";
            logString += time + ", by ";
            logString += userName + ", status ";
            logString += attemptStatus + "\n";
            
            // Write to the file
                BufferedWriter bufferedLogWriter = new BufferedWriter(new FileWriter("login_activity.txt", true));
                bufferedLogWriter.append(' ');
                bufferedLogWriter.append(logString);
                bufferedLogWriter.close();
            }
        catch (IOException e) {
            System.out.println("An error occurred.");
          }
        }
    
}
