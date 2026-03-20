package com.example.myapplication.Voting;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface VotingFoodDao {

    @Query("SELECT * FROM VotingFood WHERE session_id = :sessionId")
    List<VotingFood> getFoodVotesForSession(int sessionId);

    // Return highest 4 voted games
//    @Query("SELECT cusinie, COUNT(cusinie)  AS Votes FROM VotingFood WHERE session_id = :sessionId ORDER BY Votes desc LIMIT 4")
//    List<VotingFood> getHighestFoodVoteForSession(int sessionId);

    @Insert
    void addFoodVote(VotingFood food);
}
