package com.example.myapplication.Game;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Game {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name="name")
    public String name;

    @ColumnInfo(name="min_players")
    public int min_players;

    @ColumnInfo(name="max_players")
    public int max_players;

    @ColumnInfo(name="description")
    public String description;
}
