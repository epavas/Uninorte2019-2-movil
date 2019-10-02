package com.example.myfirstapplication.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myfirstapplication.model.Location;

import java.util.List;

@Dao
public interface LocationDao {

    @Query( "Select * from location" )
    List<Location> getLocationList();

    @Insert
    void insertarLocation(Location location);

    @Insert
    void insertAll(Location... locations);
}
