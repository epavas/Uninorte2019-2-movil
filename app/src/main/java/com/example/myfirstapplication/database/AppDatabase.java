package com.example.myfirstapplication.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myfirstapplication.dao.CurrentLocationDao;
import com.example.myfirstapplication.dao.UbicacionDao;
import com.example.myfirstapplication.dao.MessagesDao;
import com.example.myfirstapplication.dao.UserDao;
import com.example.myfirstapplication.model.CurrentLocation;
import com.example.myfirstapplication.model.Ubicacion;
import com.example.myfirstapplication.model.Messages;
import com.example.myfirstapplication.model.User;

@Database(entities = {User.class, Messages.class, Ubicacion.class, CurrentLocation.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao UserDao();
    public abstract MessagesDao MessagesDao();
    public abstract UbicacionDao LocationDao();
    public abstract CurrentLocationDao CurrentLocationDao();
}