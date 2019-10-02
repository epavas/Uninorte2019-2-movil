package com.example.myfirstapplication.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myfirstapplication.model.Ubicacion;

import java.util.List;

@Dao
public interface UbicacionDao {
    @Query( "Select * from ubicacion" )
    List<Ubicacion> getUbicacionList();

    @Insert
    void insertarUbicacion(Ubicacion ubicacion);

    @Insert
    void insertAll(Ubicacion... ubicacions);
}
