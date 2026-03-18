package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.Database.DataStore;
import com.example.myapplication.Session.Session;
import com.example.myapplication.Session.SessionAdapter;
import com.example.myapplication.Session.SessionDao;
import com.example.myapplication.User.User;
import com.example.myapplication.User.UserDao;
import com.example.myapplication.User.UserSession;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

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

    // Merkt sich, ob ein echter aktiver Termin erstellt wurde
    private boolean hasActiveTerm = false;

    // Speichert das aktuelle Termin-Datum als String
    private String currentDateTime = "";
    private User user;

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

                                    // 2. Neue Session erstellen
                                    Session session = new Session();
                                    session.group_id = user.groupId;
                                    session.host_user_id = hostUser.id; // falls host eine ID ist
                                    session.datetime_start = dateTime;
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
                        // Platz für spätere Ergebnisverarbeitung
                    }
            );

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        this.user = UserSession.getUser();
        super.onCreate(savedInstanceState);

        // Verbindet diese Activity mit dem Layout der Hauptseite
        setContentView(R.layout.activity_main);

        //laden des recycler views
        RecyclerView rvSessions = findViewById(R.id.rvSessions);

        // Adapter erstellen
        loadUsersForGroup();
        sessionAdapter = new SessionAdapter(userNameMap);
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
        // Nur wenn ein echter aktiver Termin existiert
        // und das Voting noch erlaubt ist
        // =========================
//        cardActiveGame.setOnClickListener(v -> openVotingIfAllowed());
    }

    /*
     * openVotingIfAllowed
     *
     * Öffnet die VotingActivity nur dann,
     * wenn ein aktiver Termin existiert und das Voting zeitlich noch erlaubt ist.
     */
    private void openVotingIfAllowed() {

        if (!hasActiveTerm) {
            Toast.makeText(this, "Bitte zuerst einen Termin erstellen", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isVotingStillAllowed(currentDateTime)) {
            Toast.makeText(this,
                    "Abstimmen ist nur bis 1 Tag vor dem Termin möglich",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(MainActivity.this, VotingActivity.class);
        intent.putExtra("dateTime", currentDateTime);
        votingLauncher.launch(intent);
    }

    /*
     * isVotingStillAllowed
     *
     * Prüft, ob der Termin mehr als 24 Stunden in der Zukunft liegt.
     * Nur dann darf noch abgestimmt werden.
     */
    private boolean isVotingStillAllowed(String dateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());

        try {
            Date date = sdf.parse(dateTime);
            if (date == null) {
                return false;
            }

            long nowMillis = System.currentTimeMillis();
            long termMillis = date.getTime();

            long diffMillis = termMillis - nowMillis;
            long oneDayMillis = 24L * 60L * 60L * 1000L;

            return diffMillis > oneDayMillis;

        } catch (ParseException e) {
            return false;
        }
    }

    private void loadSessions() {
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
}
