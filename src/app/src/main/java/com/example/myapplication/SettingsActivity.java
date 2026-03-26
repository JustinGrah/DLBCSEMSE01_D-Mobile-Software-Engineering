package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Database.DataStore;
import com.example.myapplication.Group.Group;
import com.example.myapplication.Group.GroupDao;
import com.example.myapplication.Group.GroupWithUsers;
import com.example.myapplication.User.User;
import com.example.myapplication.User.UserAdapter;
import com.example.myapplication.User.UserDao;
import com.example.myapplication.User.UserSession;
import com.google.android.material.button.MaterialButton;

import java.util.List;

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
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.user = UserSession.getUser();

        super.onCreate(savedInstanceState);
        View addGroupView = LayoutInflater.from(this).inflate(R.layout.dialog_add_group, null);


        // Layout der Einstellungsseite laden
        setContentView(R.layout.activity_settings);

        // Zurück-Pfeil aus dem Layout holen
        btnBack = findViewById(R.id.btnBack);

        // Laden von UI Komponenten
        RecyclerView rvMembers = findViewById(R.id.rvGroupMembers);
        MaterialButton btnAdd = findViewById(R.id.btnGenerateCode);
        MaterialButton btnLeave = findViewById(R.id.btnLeaveGroup);
        EditText etGroupCode = findViewById(R.id.etGroupCode);

        //Setup vom RecyclerView
        UserAdapter adapter = new UserAdapter();
        rvMembers.setAdapter(adapter);
        rvMembers.setLayoutManager(new LinearLayoutManager(this));

        //Prüfen ob der nutzer bereits in einer Gruppe ist.
        if (user.groupId != 0) {
            // Feld deaktivieren, damit der Nutzer keinen Code eingeben kann
            etGroupCode.setEnabled(false);
            etGroupCode.setText("Bereits in einer Gruppe!");

            DataStore.databaseWriteExecutor.execute(() -> {

                DataStore db = DataStore.getDatabase(this);
                GroupDao groupDao = db.groupDao();

                // Alle Nutzer der Gruppe laden
                List<GroupWithUsers> result = groupDao.getAllGroupUsers(user.groupId);
                List<User> members = result.get(0).user;

                runOnUiThread(() -> {
                    adapter.setUsers(members);
                });
            });
        }

        /*
         * Navigation zurück zur MainActivity
         */
        btnBack.setOnClickListener(v -> finish());
        btnLeave.setOnClickListener(v -> {
            user.groupId = 0;
            DataStore.databaseWriteExecutor.execute(() -> {

                DataStore db = DataStore.getDatabase(this);
                UserDao userDao = db.userDao();
                userDao.updateUser(user);

                runOnUiThread(() -> {
                    Toast.makeText(this, "Du hast die Gruppe erfolgreich Verlassen!", Toast.LENGTH_SHORT).show();
                });
            });
        });
        btnAdd.setOnClickListener(v -> {
            String code = etGroupCode.getText().toString().trim();

            if(!code.isEmpty()) {
                DataStore.databaseWriteExecutor.execute(() -> {

                    DataStore db = DataStore.getDatabase(this);
                    GroupDao groupDao = db.groupDao();
                    UserDao userDao = db.userDao();

                    // Gruppe anhand des Codes suchen
                    Group group = groupDao.getByCode(code);

                    // Falls keine Gruppe existiert → Fehlermeldung
                    if (group == null) {
                        runOnUiThread(() ->
                                Toast.makeText(this, "Keine Gruppe mit diesem Code gefunden", Toast.LENGTH_SHORT).show()
                        );
                        return;
                    }

                    // Gruppe existiert → Nutzer zuweisen
                    user.groupId = group.id;

                    // Nutzer in der Datenbank aktualisieren
                    userDao.updateUser(user);

                    // UI-Update im Hauptthread
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Du bist der Gruppe '" + group.name + "' beigetreten", Toast.LENGTH_SHORT).show();

                        // Code-Feld deaktivieren, da Nutzer jetzt in einer Gruppe ist
                        etGroupCode.setEnabled(false);
                        return;
                    });
                });
                return;
            }

            showAddGroupDialog();
        });
    }

    private void showAddGroupDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_group, null);
        EditText etName = view.findViewById(R.id.etGroupName);
        EditText etDescription = view.findViewById(R.id.etGroupDescription);
        EditText etCode = view.findViewById(R.id.etGroupCode);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Eine neue Gruppe erstellen")
                .setView(view)
                .setNegativeButton(R.string.cancel_button, (dialogInterface, which) -> dialogInterface.dismiss())
                .setPositiveButton(R.string.add_button, null)
                .create();

        EditText etCodeGeneration = view.findViewById(R.id.etGroupCode);
        MaterialButton btnGenCode = view.findViewById(R.id.btnAddGameGenerateCode);

        btnGenCode.setOnClickListener(v -> {
            String code = this.generateRandomCode();
            etCode.setText(code);
        });

        // Dialog anzeigen
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String description = etDescription.getText().toString().trim();
            String code = etCode.getText().toString().trim();


            // Name prüfen
            if (name.isEmpty()) {
                etName.setError("Bitte einen Namen eingeben");
                etName.requestFocus();
                return;
            }

            // Beschreibung prüfen
            if (description.isEmpty()) {
                etDescription.setError("Bitte eine Beschreibung eingeben");
                etDescription.requestFocus();
                return;
            }

            // Code prüfen
            if (code.isEmpty()) {
                etCode.setError("Bitte einen Code eingeben");
                etCode.requestFocus();
                return;
            }

            // Code muss genau 5 Zeichen haben
            if (code.length() != 5) {
                etCode.setError("Code muss genau 5 Zeichen lang sein");
                etCode.requestFocus();
                return;
            }

            // Code darf nur Buchstaben enthalten
            if (!code.matches("[a-zA-Z]{5}")) {
                etCode.setError("Code darf nur Buchstaben enthalten (a-z, A-Z)");
                etCode.requestFocus();
                return;
            }

            DataStore db = DataStore.getDatabase(this);
            DataStore.databaseWriteExecutor.execute(() -> {
                GroupDao groupDao = db.groupDao();
                Group group = new Group();
                group.name = name;
                group.code = code;
                group.description = description;

                groupDao.createGroup(group);

                runOnUiThread(() -> {
                    Toast.makeText(this, "Die Gruppe wurde erfolgreich erstellt!", Toast.LENGTH_SHORT).show();
                });
            });
        });
    }

    private String generateRandomCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        java.util.Random random = new java.util.Random();

        for (int i = 0; i < 5; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }
}