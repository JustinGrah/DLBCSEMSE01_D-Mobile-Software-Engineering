package com.example.myapplication.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.Game.Game;
import com.example.myapplication.Game.GameDao;
import com.example.myapplication.Group.Group;
import com.example.myapplication.Group.GroupDao;
import com.example.myapplication.Message.Message;
import com.example.myapplication.Message.MessageDao;
import com.example.myapplication.Rating.Rating;
import com.example.myapplication.Rating.RatingDao;
import com.example.myapplication.Session.Session;
import com.example.myapplication.Session.SessionDao;
import com.example.myapplication.User.User;
import com.example.myapplication.User.UserDao;
import com.example.myapplication.Voting.VotingGames;
import com.example.myapplication.Voting.VotingGamesDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Definition von Entities, welche in unserem ER-Modell identifiziert wurden (Dargestellt als JAVA-Klasse)
@Database(entities = {
        User.class,
        Session.class,
        Rating.class,
        Message.class,
        Group.class,
        Game.class,
        VotingGames.class
}, version = 2, exportSchema = false)

public abstract class DataStore extends RoomDatabase {
    // "Importieren" der Interfaces zum speichern und lesen von Attributen bzw. Objekten
    public abstract UserDao userDao();
    public abstract SessionDao sessionDao();
    public abstract RatingDao ratingDao();
    public abstract MessageDao messageDao();
    public abstract GroupDao groupDao();
    public abstract GameDao gameDao();
    public abstract VotingGamesDao votingGamesDao();

    //Definition der Datenbank und initiale Erstellung
    private static volatile DataStore INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static DataStore getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DataStore.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DataStore.class, "BoardGamer_App_datastore").build();
                }
            }
        }
        return INSTANCE;
    }

}
