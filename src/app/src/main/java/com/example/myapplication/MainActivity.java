package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.Database.DataStore;
import com.example.myapplication.Game.Game;
import com.example.myapplication.Game.GameDao;
import com.example.myapplication.Session.Session;
import com.example.myapplication.Session.SessionAdapter;
import com.example.myapplication.Session.SessionDao;
import com.example.myapplication.User.User;
import com.example.myapplication.User.UserDao;
import com.example.myapplication.User.UserSession;
import com.example.myapplication.Voting.VotingGamesDao;
import com.google.android.material.button.MaterialButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/*
 * MainActivity
 *
 * Diese Activity bildet die Startseite der App.
 * Hier sieht der Benutzer die wichtigsten Informationen und Funktionen auf einen Blick.
 *
 * Aktuell wird noch keine Datenbank verwendet.
 * Deshalb werden Daten nur innerhalb des aktuellen Ablaufs verarbeitet.
 */
public class MainActivity extends AppCompatActivity {

    private MaterialButton btnChat;
    private Button btnRateEvening;
    private MaterialButton btnSettings;
    private MaterialButton btnNewGame;
    private TextView btnAddDate;

    private SessionAdapter sessionAdapter;
    private Map<Integer, String> userNameMap = new HashMap<>();
    private List<Game> games;

    // Merkt sich, ob ein echter aktiver Termin erstellt wurde
    private boolean hasActiveTerm = false;

    // Speichert das aktuelle Termin-Datum als String
    private String currentDateTime = "";
    private User user;
    private boolean hasVoted = true;

    /*
     * Launcher für NewDateActivity
     *
     * Empfängt die Daten des neu erstellten Termins zurück.
     */
    private final ActivityResultLauncher<Intent> newDateLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {

                            String host = result.getData().getStringExtra("host");
                            String dateTime = result.getData().getStringExtra("dateTime");
                            String location = result.getData().getStringExtra("location");

                            if (host != null && dateTime != null && location != null) {

                                DataStore.databaseWriteExecutor.execute(() -> {
                                    DataStore db = DataStore.getDatabase(this);
                                    UserDao userDao = db.userDao();
                                    SessionDao sessionDao = db.sessionDao();
                                    User hostUser = userDao.getUserByName(host);

                                    Date date = new Date();
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
                                    try {
                                        date = sdf.parse(dateTime);
                                    } catch (ParseException e) {
                                        throw new RuntimeException(e);
                                    }

                                    Session session = new Session();
                                    session.group_id = user.groupId;
                                    session.host_user_id = hostUser.id; // falls host eine ID ist
                                    session.datetime_start = date.getTime();
                                    session.location = location;
                                    session.status = "planned";

                                    sessionDao.addSession(session);

                                    runOnUiThread(() -> {
                                        loadSessions();
                                    });
                                });
                            }
                        }
                    }
            );

    /*
     * Launcher für VotingActivity
     *
     * Aktuell wird hier noch nichts zurückverarbeitet,
     * weil die echten Voting-Ergebnisse ohne Datenbank noch nicht sauber berechnet werden können.
     */
    private final ActivityResultLauncher<Intent> votingLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {

                            boolean updated = result.getData().getBooleanExtra("updated", false);

                            if (updated) {
                                loadSessions(); // RecyclerView neu laden
                            }
                        }
                    }
            );

    private final ActivityResultLauncher<Intent> votingResultLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {}
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.user = UserSession.getUser();
        super.onCreate(savedInstanceState);

        loadSessions();

        // Verbindet diese Activity mit dem Layout der Hauptseite
        setContentView(R.layout.activity_main);

        //laden des recycler views
        RecyclerView rvSessions = findViewById(R.id.rvSessions);

        // Adapter erstellen
        loadUsersForGroup();
        loadGames();
        sessionAdapter = new SessionAdapter(this.userNameMap, this.games);
        rvSessions.setAdapter(sessionAdapter);

        // LayoutManager setzen
        rvSessions.setLayoutManager(new LinearLayoutManager(this));

        // Sessions laden
        DataStore.databaseWriteExecutor.execute(() -> {

            DataStore db = DataStore.getDatabase(this);
            SessionDao sessionDao = db.sessionDao();

            // Sessions der Gruppe laden
            List<Session> sessions = sessionDao.getSessionsForGroup(user.groupId);

            runOnUiThread(() -> {
                sessionAdapter.setSessions(sessions);
            });
        });


        // =========================
        // Buttons und Views verbinden
        // =========================
        btnChat = findViewById(R.id.btnChat);
        btnSettings = findViewById(R.id.btnSettings);
        btnNewGame = findViewById(R.id.btnNewGame);
        btnAddDate = findViewById(R.id.btnAddDate);

        // =========================
        // Chat öffnen
        // =========================
        btnChat.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ChatActivity.class);
            startActivity(intent);
        });

        // =========================
        // Bewertungsdialog öffnen
        // =========================
//        btnRateEvening.setOnClickListener(v -> {
//            BewertungDialogFragment dialog = new BewertungDialogFragment();
//            dialog.show(getSupportFragmentManager(), "BewertungDialog");
//        });

        // =========================
        // Settings öffnen
        // =========================
        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        // =========================
        // NewGameActivity öffnen
        // =========================
        btnNewGame.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NewGameActivity.class);
            startActivity(intent);
        });

        // =========================
        // NewDateActivity öffnen
        // =========================
        btnAddDate.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NewDateActivity.class);
            newDateLauncher.launch(intent);
        });

        // =========================
        // Klick auf aktiven Termin öffnet Voting
        // =========================
        sessionAdapter.setOnSessionClickListener(session -> {
            DataStore db = DataStore.getDatabase(this);
            VotingGamesDao votingGamesDao = db.votingGamesDao();

            DataStore.databaseWriteExecutor.execute(() -> {
                int votes = votingGamesDao.countGameVotesForUser(user.id,session.id);

                if(votes <= 0 ) {
                    runOnUiThread(() -> {
                        Intent intent = new Intent(MainActivity.this, VotingActivity.class);

                        // Session-Daten übergeben
                        intent.putExtra("session_id", session.id);
                        intent.putExtra("group_id", session.group_id);
                        intent.putExtra("host_user_id", session.host_user_id);

                        votingLauncher.launch(intent);
                    });
                } else {
                    runOnUiThread(() -> {
                        Intent intent = new Intent(MainActivity.this, VotingShowActivity.class);

                        // Session-Daten übergeben
                        intent.putExtra("session_id", session.id);
                        intent.putExtra("group_id", session.group_id);
                        intent.putExtra("host_user_id", session.host_user_id);
                        votingResultLauncher.launch(intent);
                    });
                };
            });
        });
    }

    private void loadSessions() {
        loadUsersForGroup();
        loadGames();
        DataStore.databaseWriteExecutor.execute(() -> {

            DataStore db = DataStore.getDatabase(this);
            SessionDao sessionDao = db.sessionDao();

            // Sessions der Gruppe laden
            List<Session> sessions = sessionDao.getSessionsForGroup(user.groupId);

            runOnUiThread(() -> {
                sessionAdapter.setSessions(sessions);
            });
        });
    }

    private void loadUsersForGroup() {

        DataStore.databaseWriteExecutor.execute(() -> {

            DataStore db = DataStore.getDatabase(this);
            UserDao userDao = db.userDao();

            List<User> users = userDao.getUsersByGroup(user.groupId);

            // Map: userId → username
            for (User u : users) {
                userNameMap.put(u.id, u.name);
            }
        });
    }

    private void loadGames() {
        DataStore.databaseWriteExecutor.execute(() -> {

            DataStore db = DataStore.getDatabase(this);
            GameDao gameDao = db.gameDao();

            this.games = gameDao.listAllGames();
        });
    }
}
