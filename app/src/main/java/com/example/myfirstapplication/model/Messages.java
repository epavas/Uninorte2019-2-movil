package com.example.myfirstapplication.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "messages")
public class Messages {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idMessage")
    public int idMessage;

    @ColumnInfo(name = "body")
    public String body;

    @ColumnInfo(name = "message_timestamp")
    public String message_timestamp;

    @ColumnInfo(name = "sender")
    public String sender;


}
