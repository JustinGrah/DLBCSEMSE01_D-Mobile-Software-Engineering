package com.example.myapplication.Voting;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

// Interface zum speichern und lesen von daten
@Dao
public interface VotingGamesDao {

    @Query("SELECT * FROM VotingGames WHERE session_id = :sessionId")
    List<VotingGames> getGameVotesForSession(int sessionId);

    // Return highest 4 voted games

    @Query("SELECT COUNT(*) FROM VotingGames WHERE user_id = :userId AND session_id = :sessionId")
    int countGameVotesForUser(int userId, int sessionId);

    @Query("SELECT Game.name AS name, COUNT(VotingGames.game_id) AS votes FROM VotingGames INNER JOIN Game ON VotingGames.game_id = Game.id WHERE VotingGames.session_id = :sessionId GROUP BY Game.id ORDER BY votes DESC")
    List<VotingGamesWithGame> countGameVotesForSession(int sessionId);


    @Insert
    void addGameVote(VotingGames gameVote);
}
