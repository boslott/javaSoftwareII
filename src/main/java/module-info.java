module com.boslott {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.base;
    requires java.sql;
    
   
    exports com.boslott.controller;
    exports com.boslott.DataAccess;
    exports com.boslott.model;

    opens com.boslott.controller to javafx.fxml;
    opens com.boslott.model to javafx.base;
}
