package com.example.myapplication.Rating;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

// Interface zum speichern und lesen von daten
@Dao
public interface RatingDao {
    @Insert
    public void addRating(Rating rating);

    @Query("SELECT COUNT(*) FROM Rating WHERE rater_user_id = :userId AND session_id = :sessionId")
    public int getVotesForUser(int userId, int sessionId);

    @Query("SELECT AVG(food_rating) AS avgFood, AVG(host_rating) AS avgHost, AVG(overall_rating) AS avgOverall FROM Rating WHERE session_id = :sessionId")
    SessionRatings getAveragesForSession(int sessionId);

}
