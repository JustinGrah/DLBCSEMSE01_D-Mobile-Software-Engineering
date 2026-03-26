package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Database.DataStore;
import com.example.myapplication.User.User;
import com.example.myapplication.User.UserDao;

import java.util.List;

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

        EditText usernameTxt = findViewById(R.id.etRegisterUsername);
        EditText passwordTxt = findViewById(R.id.etRegisterPassword);
        Button btnRegister = findViewById(R.id.btnRegisterUser);

        btnRegister.setOnClickListener(v -> {
            String username = usernameTxt.getText().toString().trim();
            String password = passwordTxt.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Bitte alle Felder ausfüllen", Toast.LENGTH_SHORT).show();
                return;
            }

            this.RegisterUser(username, password);
        });
    }

    private void RegisterUser(String username, String password) {
        DataStore db = DataStore.getDatabase(this);
        UserDao userDao = db.userDao();

        User user = new User(username, password);

        DataStore.databaseWriteExecutor.execute(() -> {
            List<User> userList = userDao.getAllUserByName(user.name);

            if(userList.isEmpty()) {
                userDao.createUser(user);
                runOnUiThread(() -> {
                    Toast.makeText(this, "Registrierung erfolgreich!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                });
            } else {
                runOnUiThread(() ->
                        Toast.makeText(this, "Benutzername bereits vergeben!", Toast.LENGTH_SHORT).show()
                );
            }
        });
    }
}