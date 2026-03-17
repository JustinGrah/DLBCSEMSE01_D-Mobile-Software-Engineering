package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Database.DataStore;
import com.example.myapplication.User.User;
import com.example.myapplication.User.UserDao;

import java.util.List;


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

        // Text als variablen




        /*
         * Wechsel zur Hauptseite
         *
         * Wenn der Benutzer auf "Login" klickt, wird die MainActivity geöffnet.
         * Eine echte Prüfung der Login-Daten gibt es im Moment noch nicht,
         * da wir aktuell nur die Navigation zwischen den Seiten umsetzen.
         */
        btnLogin.setOnClickListener(v -> {
            EditText usernameTxt = findViewById(R.id.etUsername);
            EditText passwordTxt = findViewById(R.id.etPassword);

            String username = usernameTxt.getText().toString().trim();
            String password = passwordTxt.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                makeToast("Bitte alle Felder ausfüllen");
                return;
            }

            loginUser(username, password);
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

    private void loginUser(String username, String password) {
        DataStore db = DataStore.getDatabase(this);
        UserDao userDao = db.userDao();

        DataStore.databaseWriteExecutor.execute(() -> {
            User user = userDao.getUserByName(username);

            Log.d("Login", user.name);
            Log.d("Login", user.password.trim());
            Log.d("Login", password.trim());


            if(user != null) {
                if(user.password.equals(password)) {
                    Log.d("Login", "password correct");
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    // Passwort falsch
                    makeToast("Das Passwort ist nicht richtig!");
                }

            } else {
                // Benutzer nicht gefunden!
                makeToast("Der Benutzer wurde nicht gefunden!");
            }
        });
    }

    private void makeToast(String text) {
        runOnUiThread(() ->
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        );
    }
}