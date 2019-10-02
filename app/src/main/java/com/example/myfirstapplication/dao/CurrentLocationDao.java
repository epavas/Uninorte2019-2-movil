package com.example.myfirstapplication.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myfirstapplication.model.CurrentLocation;

import java.util.List;

@Dao
public interface CurrentLocationDao {



    @Query( "Select * from CurrentLocation" )
    List<CurrentLocation> getCurrentLocationList();

    @Insert
    void insertarCurrentLocation(CurrentLocation currentLocation);

    @Update
    void updateCurrentLocation(CurrentLocation currentLocation);



}
