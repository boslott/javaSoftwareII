
package com.boslott.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * This class holds all the values to display to the user in order to 
 * create timestamps
 *
 * @author Steven Slott
 */
public class TimesArrayList {
    
    private ArrayList<LocalDateTime> times = new ArrayList<>();
    
    /**
     * Constructs a TimesArrayList object and populates the ArrayList with
     * LocalDateTime objects with the values 0 - 23:45 in "15 minute" increments
     */
    public TimesArrayList() {
        this.times.add(LocalDateTime.of(2021, 1, 1, 0, 00, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 0, 15, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 0, 30, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 0, 45, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 1, 00, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 1, 15, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 1, 30, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 1, 45, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 2, 00, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 2, 15, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 2, 30, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 2, 45, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 3, 00, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 3, 15, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 3, 30, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 3, 45, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 4, 00, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 4, 15, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 4, 30, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 4, 45, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 5, 00, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 5, 15, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 5, 30, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 5, 45, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 6, 00, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 6, 15, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 6, 30, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 6, 45, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 7, 00, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 7, 15, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 7, 30, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 7, 45, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 8, 00, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 8, 15, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 8, 30, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 8, 45, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 9, 00, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 9, 15, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 9, 30, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 9, 45, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 10, 00, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 10, 15, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 10, 30, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 10, 45, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 11, 00, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 11, 15, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 11, 30, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 11, 45, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 12, 00, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 12, 15, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 12, 30, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 12, 45, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 13, 00, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 13, 15, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 13, 30, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 13, 45, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 14, 00, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 14, 15, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 14, 30, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 14, 45, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 15, 00, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 15, 15, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 15, 30, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 15, 45, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 16, 00, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 16, 15, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 16, 30, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 16, 45, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 17, 00, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 17, 15, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 17, 30, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 17, 45, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 18, 00, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 18, 15, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 18, 30, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 18, 45, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 19, 00, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 19, 15, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 19, 30, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 19, 45, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 20, 00, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 20, 15, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 20, 30, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 20, 45, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 21, 00, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 21, 15, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 21, 30, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 21, 45, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 22, 00, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 22, 15, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 22, 30, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 22, 45, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 23, 00, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 23, 15, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 23, 30, 00));
        this.times.add(LocalDateTime.of(2021, 1, 1, 23, 45, 00));
    }
    
    /**
     * Gets the ArrayList of LocalDateTime objects
     *
     * @return
     */
    public ArrayList<LocalDateTime> getTimesList() {
        return this.times;
    }
    
}
