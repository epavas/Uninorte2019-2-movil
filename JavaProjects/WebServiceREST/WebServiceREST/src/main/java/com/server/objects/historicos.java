/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.objects;

/**
 *
 * @author je_z_
 */
public class historicos {
    private String id;
    private String lat;
    private String lon;
    private String fecha;
    private String iduser;
    private boolean linea;

    public historicos(String lat, String lon, String fecha, String id, boolean linea) {
        this.lat = lat;
        this.lon = lon;
        this.fecha = fecha;
        this.id = id;
        this.linea=linea;
    }
    public historicos() {
        
    }

    public boolean isLinea() {
        return linea;
    }

    public void setLinea(boolean linea) {
        this.linea = linea;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
    
    
    
}


