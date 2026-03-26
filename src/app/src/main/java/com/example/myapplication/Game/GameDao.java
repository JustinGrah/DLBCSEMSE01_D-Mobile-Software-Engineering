package com.example.myapplication.Game;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

// Interface zum speichern und lesen von daten
@Dao
public interface GameDao {

    @Query("SELECT * FROM Game")
    public List<Game> listAllGames();

    @Insert
    void addGame(Game game);

    @Delete
    void deleteGame(Game game);
}
