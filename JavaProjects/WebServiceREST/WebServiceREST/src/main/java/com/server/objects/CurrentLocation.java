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
public class CurrentLocation {
    private String username;
    private String full_name;
    private double lat;
    private double lon;
    private Date lastSeen;
    private String status;

    public CurrentLocation(
        String username, String full_name, String lat, String lon, 
        String status, String lastSeen ) throws ParseException {
        this.username = username;
        this.full_name = full_name;
        this.lat = Double.parseDouble(lat);
        this.lon = Double.parseDouble(lon);
        this.lastSeen = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
            .parse(lastSeen);
        this.status = status;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public Date getLastSeen() {
        return lastSeen;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
