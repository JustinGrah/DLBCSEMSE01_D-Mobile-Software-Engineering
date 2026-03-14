package com.example.myapplication.Message;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface MessageDao {
    @Insert
    void addMessage(Message message);
}
