package com.example.boardgameapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

/*
 * NewGameActivity
 *
 * Diese Activity zeigt die Liste der verfügbaren Spiele.
 *
 * Aktuell wird noch keine Datenbank verwendet.
 * Neue Spiele werden deshalb noch nicht dauerhaft gespeichert.
 *
 * Vorhandene Funktionen:
 * - Navigation zurück
 * - Dialog zum Hinzufügen eines Spiels öffnen
 * - Eingaben lesen
 * - Eingaben validieren
 * - Toast-Meldung anzeigen
 */
public class NewGameActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private MaterialButton btnAddGame;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Layout der Spieleübersicht laden
        setContentView(R.layout.activity_new_game);

        // Buttons aus dem Layout holen
        btnBack = findViewById(R.id.btnBack);
        btnAddGame = findViewById(R.id.btnAddGame);

        /*
         * Navigation zurück zur MainActivity
         *
         * Beim Klick auf den Pfeil oben links wird diese Activity geschlossen.
         */
        btnBack.setOnClickListener(v -> finish());

        /*
         * Öffnet den Dialog zum Hinzufügen eines neuen Spiels
         */
        btnAddGame.setOnClickListener(v -> showAddGameDialog());
    }

    /*
     * showAddGameDialog
     *
     * Zeigt einen Dialog mit Eingabefeldern für:
     * - Spielname
     * - minimale Spielerzahl
     * - maximale Spielerzahl
     * - Spieldauer
     *
     * Die Eingaben werden validiert.
     * Da noch keine Datenbank verwendet wird, erscheint nach erfolgreicher Eingabe
     * nur eine Toast-Meldung.
     */
    private void showAddGameDialog() {

        // Dialog-Layout laden
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_game, null);

        // Eingabefelder aus dem Dialog holen
        EditText etNewGameName = view.findViewById(R.id.etNewGameName);
        EditText etMinPlayers = view.findViewById(R.id.etMinPlayers);
        EditText etMaxPlayers = view.findViewById(R.id.etMaxPlayers);
        EditText etDurationMin = view.findViewById(R.id.etDurationMin);

        // Dialog erstellen
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.add_game_button)
                .setView(view)
                .setNegativeButton(R.string.cancel_button, (dialogInterface, which) -> dialogInterface.dismiss())
                .setPositiveButton(R.string.add_button, null)
                .create();

        // Dialog anzeigen
        dialog.show();

        /*
         * Eigene Klicklogik für den positiven Button.
         *
         * Dadurch bleibt der Dialog offen, wenn Eingaben fehlen oder ungültig sind.
         */
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {

            // Texte aus den Eingabefeldern lesen
            String gameName = etNewGameName.getText().toString().trim();
            String minPlayersText = etMinPlayers.getText().toString().trim();
            String maxPlayersText = etMaxPlayers.getText().toString().trim();
            String durationText = etDurationMin.getText().toString().trim();

            // =========================
            // Validierung: leere Felder
            // =========================
            if (TextUtils.isEmpty(gameName)) {
                etNewGameName.setError("Bitte Spielname eingeben");
                etNewGameName.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(minPlayersText)) {
                etMinPlayers.setError("Bitte minimale Spielerzahl eingeben");
                etMinPlayers.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(maxPlayersText)) {
                etMaxPlayers.setError("Bitte maximale Spielerzahl eingeben");
                etMaxPlayers.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(durationText)) {
                etDurationMin.setError("Bitte Spieldauer eingeben");
                etDurationMin.requestFocus();
                return;
            }

            // =========================
            // Zahlen umwandeln
            // =========================
            int minPlayers;
            int maxPlayers;
            int duration;

            try {
                minPlayers = Integer.parseInt(minPlayersText);
                maxPlayers = Integer.parseInt(maxPlayersText);
                duration = Integer.parseInt(durationText);
            } catch (NumberFormatException e) {
                Toast.makeText(
                        this,
                        "Bitte nur gültige Zahlen eingeben",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            // =========================
            // Fachliche Validierung
            // =========================
            if (minPlayers <= 0) {
                etMinPlayers.setError("Minimale Spielerzahl muss größer als 0 sein");
                etMinPlayers.requestFocus();
                return;
            }

            if (maxPlayers < minPlayers) {
                etMaxPlayers.setError("Maximale Spielerzahl darf nicht kleiner als die minimale sein");
                etMaxPlayers.requestFocus();
                return;
            }

            if (duration <= 0) {
                etDurationMin.setError("Spieldauer muss größer als 0 sein");
                etDurationMin.requestFocus();
                return;
            }

            // =========================
            // Erfolgsrückmeldung
            // =========================
            Toast.makeText(
                    this,
                    "Spiel hinzugefügt: " + gameName,
                    Toast.LENGTH_SHORT
            ).show();

            // Dialog schließen
            dialog.dismiss();
        });
    }
}