
package com.boslott.controller;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * This class controls the Scene the UI is displaying
 *
 * @author Steven Slott
 */
public class SceneMediator {
    
    private static Scene scene;
    
    /**
     * Constructs a new SceneMediator object, constructs a new Scene object,
     * and sets the application main Stage Scene to the Login Scene
     * 
     * @throws IOException if there is an FXML loading error
     */
    SceneMediator() throws IOException {
        scene = new Scene(loadFXML("LoginScene"), 900, 600);
        App.setMainStageScene(scene);
        App.showMainStage();
    }
    
    /**
     * Changes the application main Stage Scene to the FXML Scene with 
     * the provided name
     *
     * @param fxml the name of the FXML Scene to load
     * @throws IOException if there is an FXML loading error
     */
    public static void changeScene(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
        App.setMainStageScene(scene);
    }

    /**
     * Loads an FXML Scene with the provided name
     * 
     * @param fxml the FXML Scene to load
     * @return a Parent object with the loaded FXML Scene
     * @throws IOException if there is an FXML loading error
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/view/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    
}
