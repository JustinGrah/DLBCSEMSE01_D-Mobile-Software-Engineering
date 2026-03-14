package com.example.myapplication.Session;


import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

import com.example.myapplication.Message.Message;

import java.util.List;

@Entity
public class SessionWithMessages {
    @Embedded public Session sesion;

    @Relation(
            parentColumn = "id",
            entityColumn = "session_id"
    )
    public List<Message> messages;
}
