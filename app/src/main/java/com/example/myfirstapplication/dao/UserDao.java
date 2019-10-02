package com.example.myfirstapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myfirstapplication.model.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("select * from User")
    List<User> getAll();

    @Query("select * from User  WHERE username IN (:usernames)")
    List<User> loadAllByIds(int[] usernames);

    @Query("select * from User  WHERE email = :userEmail")
    List<User> getUserbyEmail(String userEmail);


    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);
}