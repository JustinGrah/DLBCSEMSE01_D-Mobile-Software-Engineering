package com.example.myapplication.Rating;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface RatingDao {
    @Insert
    public void addRating(Rating rating);
}
