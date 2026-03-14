package com.example.myapplication.Message;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity
public class Message {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int session_id;
    public int sender_id;
    @ColumnInfo(name="type")
    public String type;
    @ColumnInfo(name="content")
    public String content;

    @ColumnInfo(name="timestamp")
    public String timestamp;
}
