package com.example.boardgameapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/*
 * RegisterActivity
 *
 * Diese Activity zeigt die Registrierungsseite der App.
 * Hier können Benutzer später einen neuen Account erstellen.
 *
 * Aktuell enthält die Activity nur das Layout.
 * Die eigentliche Registrierungslogik wird später ergänzt.
 */
public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Verbindet diese Activity mit dem Layout der Registrierungsseite
        setContentView(R.layout.activity_register);
    }
}