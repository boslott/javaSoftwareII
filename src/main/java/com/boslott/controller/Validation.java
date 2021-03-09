
package com.boslott.controller;

import java.sql.Timestamp;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * A utility class to help with validation
 *
 * @author Steven Slott
 */
public class Validation {
    
    /**
     * Returns whether or not the provided TextField has a value
     *
     * @param field the TextField to validate
     * @return true if the Field  "has nothing", false if there is a value
     */
    public static Boolean hasNothing(TextField field) {
        Boolean isEmpty = false;
        Boolean isBlank = false;
        
        if(field.getText() == null) {
            return true;
        }
        else if(field.getText().isBlank()) isBlank = true;
        if(field.getText().isEmpty()) isEmpty = true;
        
        return (isEmpty || isBlank);
    }
    
    /**
     * Returns whether or not the provided TextArea has a value
     *
     * @param area the TextArea to validate
     * @return true if the Area "has nothing", false if there is a value
     */
    public static Boolean hasNothing(TextArea area) {
        Boolean isEmpty = false;
        Boolean isBlank = false;
        
        if(area.getText() == null) {
            return true;
        }
        else if(area.getText().isBlank()) isBlank = true;
        if(area.getText().isEmpty()) isEmpty = true;
        
        return (isEmpty || isBlank);
    }
    
    /**
     * Returns whether or not the provided ComboBox has a value
     *
     * @param box the ComboBox to validate
     * @return true if the ComboBox "has nothing", false if there is a value
     */
    public static Boolean hasNothing(ComboBox box) {
        Boolean isEmpty = false;
        
        if(box.getValue() == null) {
            return true;
        }
        else if(box.getValue().toString().equals("")) isEmpty = true;
        
        return isEmpty;
    }
    
    /**
     * Returns whether or not the provided DatePicker has a value
     *
     * @param picker the DatePicker to validate
     * @return true if the Picker "has nothing", false if there is a value
     */
    public static Boolean hasNothing(DatePicker picker) {
        Boolean isNull = false;
        Boolean isEmpty = false;
        
        if(picker.getValue() == null) isNull = true;
        if(picker.getValue().toString().equals("")) isEmpty = true;
        
        return (isNull || isEmpty);
    }
    
    /**
     * Validates if two provided Timestamps are equal year, month, day, hour, and minute
     *
     * @param timestamp1 the first Timestamp to compare
     * @param timestamp2 the second Timestamp to compare
     * @return true if the the year, month, day, hour, and minutes are equal between
     * the two Timestamps, false if the Timestamps are not the same
     */
    public static Boolean timestampIsEqualToTimestamp(Timestamp timestamp1, Timestamp timestamp2) {
        // Equal Timestamps for the purpose of this app means Timestamps have the same
        // year - month - date - hour - minute
        return false;
    }
    
    /**
     * This method compares three Timestamps and determines whether or not
     * the timestampInQuestion is chronologically in the middle of the other
     * two provided Timestamps. If so, this indicates a chosen Appointment time
     * coincides with an already existing Appointment
     *
     * @param timestampInQuestion the Timestamp to validate if in the middle or not
     * @param afterThisTime the Timestamp to use as the chronologically first time
     * @param beforeThisTime the Timestamp to use as the chronologically second time
     * @return true if the timestampInQuestion comes after the first Timestamp and 
     * before the second Timestamp, this means the timestampInQuestion is in the 
     * middle of the other two provided timestamps.
     */
    public static Boolean timestampIsAfterAndBefore(Timestamp timestampInQuestion, Timestamp afterThisTime, Timestamp beforeThisTime) {
        
        return true;
    }
    
}
