package com.example.myfirstapplication.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "currentLocation")
public class CurrentLocation {

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
