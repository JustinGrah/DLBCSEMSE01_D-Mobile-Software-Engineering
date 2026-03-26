package com.example.myapplication.Message;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Nachrichten Entität für den Chat
@Entity
public class Message {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int group_id;
    public int sender_id;
    @ColumnInfo(name="type")
    public String type;
    @ColumnInfo(name="content")
    public String content;

    @ColumnInfo(name="timestamp")
    public String timestamp;
}
