package com.example.myapplication.Rating;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Rating {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "session_id")
    public int session_id;

    @ColumnInfo(name = "rater_user_id")
    public int rater_user_id;

    @ColumnInfo(name = "host_rating")
    public int host_rating;

    @ColumnInfo(name = "food_rating")
    public int food_rating;

    @ColumnInfo(name = "overall_rating")
    public int overall_rating;
}
