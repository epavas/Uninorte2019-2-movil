package com.example.myfirstapplication.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "idUser")
    public int idUser;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "password")
    public String password;

    @ColumnInfo(name = "first_name")
    public String first_name;

    @ColumnInfo(name = "last_name")
    public String last_name;

    @ColumnInfo(name = "full_name")
    public String full_name;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "lastLat")
    public String lastLat;

    @ColumnInfo(name = "lastLon")
    public String lastLon;

    @ColumnInfo(name = "status")
    public String status;

    @ColumnInfo(name = "lastSeen")
    public String lastSeen;


}