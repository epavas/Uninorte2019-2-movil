package com.example.myfirstapplication.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "currentLocation")
public class CurrentLocation {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idCurrentLocation")
    public int idCurrentLocation;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "full_name")
    public String full_name;

    @ColumnInfo(name = "lat")
    public String lat;

    @ColumnInfo(name = "lon")
    public String lon;

    @ColumnInfo(name = "lastSeen")
    public String lastSeen;

    @ColumnInfo(name = "status")
    public String status;

}
