package com.example.myapplication.Session;

import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.myapplication.Message.Message;

import java.util.List;
import java.util.Map;

@Dao
public interface SessionDao {
    @Query("SELECT * FROM session WHERE group_id = :groupId ORDER BY datetime_start DESC")
    List<Session> getSessionsForGroup(int groupId);

    @Transaction
    @Query("SELECT * FROM Session WHERE id = :session_id")
    public List<SessionWithRatings> getSessionRatings(int session_id);

    @Insert
    void addSession(Session session);
}
