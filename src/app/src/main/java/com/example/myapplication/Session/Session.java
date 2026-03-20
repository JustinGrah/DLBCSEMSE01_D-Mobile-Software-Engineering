package com.example.myapplication.Session;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Session {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "group_id")
    public int group_id;

    @ColumnInfo(name = "host_user_id")
    public int host_user_id;

    @ColumnInfo(name = "datetime_start")
    public long datetime_start;

    @ColumnInfo(name = "location")
    public String location;

    @ColumnInfo(name = "status")
    public String status;
}
