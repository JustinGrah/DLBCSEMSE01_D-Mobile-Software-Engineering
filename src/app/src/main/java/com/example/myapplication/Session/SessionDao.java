package com.example.myapplication.Session;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

// Interface zum speichern und lesen von daten
@Dao
public interface SessionDao {
    @Query("SELECT * FROM session WHERE group_id = :groupId ORDER BY datetime_start DESC LIMIT 5")
    List<Session> getSessionsForGroup(int groupId);

    @Query("SELECT * FROM Session ORDER BY datetime_start DESC LIMIT 1")
    Session getNextSession();

    @Insert
    void addSession(Session session);
}
