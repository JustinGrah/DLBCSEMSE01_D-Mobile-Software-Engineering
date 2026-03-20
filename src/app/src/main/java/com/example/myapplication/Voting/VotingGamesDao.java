package com.example.myapplication.Voting;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myapplication.Game.Game;

import java.util.List;

@Dao
public interface VotingGamesDao {

    @Query("SELECT * FROM VotingGames WHERE session_id = :sessionId")
    List<VotingGames> getGameVotesForSession(int sessionId);

    // Return highest 4 voted games

    @Query("SELECT COUNT(*) FROM VotingGames WHERE user_id = :userId AND session_id = :sessionId")
    int countGameVotesForUser(int userId, int sessionId);


    @Insert
    void addGameVote(VotingGames gameVote);
}
