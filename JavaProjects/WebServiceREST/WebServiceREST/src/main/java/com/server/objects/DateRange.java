/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.objects;

/**
 *
 * @author elvis
 */
public class DateRange {
    private String first_Date;
    private String last_Date;

    public DateRange(String first_Date, String last_Date) {
        this.first_Date = first_Date;
        this.last_Date = last_Date;
    }

    
    
    public String getFirst_Date() {
        return first_Date;
    }

    public void setFirst_Date(String first_Date) {
        this.first_Date = first_Date;
    }

    public String getLast_Date() {
        return last_Date;
    }

    public void setLast_Date(String last_Date) {
        this.last_Date = last_Date;
    }
    
}
