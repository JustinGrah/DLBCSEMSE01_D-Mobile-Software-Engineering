package com.example.myapplication.Voting;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class VotingGames {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "session_id")
    public int session_id;

    @ColumnInfo(name = "game_id")
    public int game_id;

    @ColumnInfo(name = "user_id")
    public int user_id;
}
