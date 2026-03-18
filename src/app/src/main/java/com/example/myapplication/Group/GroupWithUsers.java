package com.example.myapplication.Group;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.myapplication.User.User;

import java.util.List;

public class GroupWithUsers {
    @Embedded
    Group group;

    @Relation(
            parentColumn = "id",
            entityColumn = "group_id"
    )
    public List<User> user;

}
