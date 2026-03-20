package com.example.myapplication.Voting;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class VotingFood {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "session_id")
    public int session_id;
    @ColumnInfo(name = "user_id")
    public int user_id;
    @ColumnInfo(name = "cusinie")
    public String cusinie;
}
