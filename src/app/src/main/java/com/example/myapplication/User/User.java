package com.example.myapplication.User;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "password")
    public String password;
    @ColumnInfo(name = "group_id")
    public int groupId;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}