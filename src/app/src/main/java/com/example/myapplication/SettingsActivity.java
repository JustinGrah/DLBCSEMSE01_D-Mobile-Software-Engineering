package com.example.boardgameapp;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/*
 * SettingsActivity
 *
 * Diese Activity zeigt die Einstellungen der Spielgruppe.
 *
 * Funktionen (Logik wird später ergänzt):
 * - Gruppencode anzeigen / neuen Code generieren
 * - Mitglieder der Gruppe anzeigen
 * - Mitglieder entfernen
 * - Gruppe verlassen
 *
 * Aktuell enthält die Activity nur das Layout.
 */
public class SettingsActivity extends AppCompatActivity {

    private ImageButton btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Layout der Einstellungsseite laden
        setContentView(R.layout.activity_settings);

        // Zurück-Pfeil aus dem Layout holen
        btnBack = findViewById(R.id.btnBack);

        /*
         * Navigation zurück zur MainActivity
         */
        btnBack.setOnClickListener(v -> finish());
    }
}