/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.objects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author elvis
 */
public class Location {
    private double lat;
    private double lon;
    private Date lastSeen;
    private String username;

    public Location(
        String lat, String lon, String lastSeen, String username) throws ParseException {
        this.lat = Double.parseDouble(lat);
        this.lon = Double.parseDouble(lon);
        this.lastSeen 
            = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                .parse(lastSeen);
        this.username = username;
    }

    public double getLat() {
        return lat;
    }

    public String getLatString() {
        return Double.toString(lat);
    }
    
    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public String getLonString() {
        return Double.toString(lon);
    }
    
    public void setLon(double lon) {
        this.lon = lon;
    }

    public Date getLastSeen() {
        return lastSeen;
    }

    public String getLastSeenISOFormatted(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(lastSeen);
    }
    
    public void setLastSeen(Date lastSeen) {
        this.lastSeen = lastSeen;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
