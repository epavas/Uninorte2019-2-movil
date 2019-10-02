package com.example.myfirstapplication.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myfirstapplication.dao.CurrentLocationDao;
import com.example.myfirstapplication.dao.LocationDao;
import com.example.myfirstapplication.dao.MessagesDao;
import com.example.myfirstapplication.dao.UserDao;
import com.example.myfirstapplication.model.CurrentLocation;
import com.example.myfirstapplication.model.Location;
import com.example.myfirstapplication.model.Messages;
import com.example.myfirstapplication.model.User;

@Database(entities = {User.class, Messages.class, Location.class, CurrentLocation.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao UserDao();
    public abstract MessagesDao MessagesDao();
    public abstract LocationDao LocationDao();
    public abstract CurrentLocationDao CurrentLocationDao();
}