package com.example.myfirstapplication.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myfirstapplication.model.Location;
import com.example.myfirstapplication.model.Messages;

import java.util.List;

@Dao
public interface MessagesDao {

    @Query( "Select * from messages" )
    List<Messages> getAll();

    @Insert
    void insertMessages(Messages mesages);
}
