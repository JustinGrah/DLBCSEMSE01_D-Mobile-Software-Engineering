package com.example.myapplication.Message;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MessageDao {
    @Insert
    void addMessage(Message message);

    @Query("SELECT * FROM Message WHERE group_id = :groupId")
    List<Message> getMessagesFromGroup(int groupId);
}
