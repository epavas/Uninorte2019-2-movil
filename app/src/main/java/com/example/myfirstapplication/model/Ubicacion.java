package com.example.myfirstapplication.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ubicacion")
public class Ubicacion {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idUbicacion")
    public int idUbicacion;

    @ColumnInfo(name = "lat")
    public String lat;

    @ColumnInfo(name = "lon")
    public String lon;

    @ColumnInfo(name = "lastSeen")
    public String lastSeen;

    @ColumnInfo(name = "username")
    public String username;
}
