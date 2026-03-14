package com.example.myapplication.Group;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

import com.example.myapplication.Session.Session;

import java.util.List;

@Entity
public class GroupWithSessions {
    @Embedded
    Group group;

    @Relation(
            parentColumn = "id",
            entityColumn = "group_id"
    )
    public List<Session> sessions;
}
