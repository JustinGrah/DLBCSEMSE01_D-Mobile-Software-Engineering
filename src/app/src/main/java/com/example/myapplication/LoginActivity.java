package com.example.boardgameapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private Button btnRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Verbindet diese Activity mit dem Login-Layout
        setContentView(R.layout.activity_login);

        // Buttons aus dem Layout holen
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        /*
         * Wechsel zur Hauptseite
         *
         * Wenn der Benutzer auf "Login" klickt, wird die MainActivity geöffnet.
         * Eine echte Prüfung der Login-Daten gibt es im Moment noch nicht,
         * da wir aktuell nur die Navigation zwischen den Seiten umsetzen.
         */
        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        });

        /*
         * Wechsel zur Registrierungsseite
         *
         * Über den Button "Registrieren" gelangt der Benutzer
         * von der Login-Seite zur RegisterActivity.
         */
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}