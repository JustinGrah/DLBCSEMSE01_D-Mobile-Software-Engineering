package com.example.boardgameapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class NewDateActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private Button btnSaveTermin;
    private Spinner spHost;
    private EditText etDateTime;
    private EditText etLocationLabel;

    // Kalender für Datum und Zeit
    private final Calendar selectedCal = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_date);

        // =========================
        // Views mit dem Layout verbinden
        // =========================
        btnBack = findViewById(R.id.btn_back);
        btnSaveTermin = findViewById(R.id.btn_save_termin);
        spHost = findViewById(R.id.sp_host);
        etDateTime = findViewById(R.id.et_date_time);
        etLocationLabel = findViewById(R.id.et_location_label);

        // =========================
        // Spinner mit Mock-Daten füllen
        // =========================
        setupHostSpinner();

        // =========================
        // Zurück-Button
        // =========================
        btnBack.setOnClickListener(v -> finish());

        // =========================
        // Datumsfeld klickbar machen
        // =========================
        etDateTime.setFocusable(false);
        etDateTime.setClickable(true);
        etDateTime.setOnClickListener(v -> pickDateThenTime());

        // =========================
        // Speichern-Button
        // =========================
        btnSaveTermin.setOnClickListener(v -> saveDate());
    }

    /*
     * setupHostSpinner
     * Füllt den Gastgeber-Spinner mit Mock-Daten aus strings.xml.
     */
    private void setupHostSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.hosts,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spHost.setAdapter(adapter);
    }

    /*
     * pickDateThenTime
     *
     * Öffnet zuerst den DatePicker und danach den TimePicker.
     * Das Ergebnis wird anschließend im Datumsfeld angezeigt.
     */
    private void pickDateThenTime() {
        Calendar now = Calendar.getInstance();

        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            selectedCal.set(Calendar.YEAR, year);
            selectedCal.set(Calendar.MONTH, month);
            selectedCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            new TimePickerDialog(this, (tp, hour, minute) -> {
                selectedCal.set(Calendar.HOUR_OF_DAY, hour);
                selectedCal.set(Calendar.MINUTE, minute);
                selectedCal.set(Calendar.SECOND, 0);
                selectedCal.set(Calendar.MILLISECOND, 0);

                etDateTime.setText(
                        DateFormat.format("dd.MM.yyyy HH:mm", selectedCal)
                );

            }, now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    true).show();

        }, now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)).show();
    }

    /*
     * saveDate
     *
     * Prüft die Eingaben, zeigt bei Fehlern Toast-Meldungen
     * und gibt die Daten per Intent an die MainActivity zurück.
     */
    private void saveDate() {
        String selectedHost = spHost.getSelectedItem() != null
                ? spHost.getSelectedItem().toString().trim()
                : "";

        String dateTime = etDateTime.getText().toString().trim();
        String location = etLocationLabel.getText().toString().trim();

        // =========================
        // Eingaben validieren
        // =========================
        if (selectedHost.isEmpty()) {
            Toast.makeText(this, "Bitte Gastgeber auswählen", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dateTime.isEmpty()) {
            Toast.makeText(this, "Bitte Datum und Uhrzeit auswählen", Toast.LENGTH_SHORT).show();
            return;
        }

        if (location.isEmpty()) {
            etLocationLabel.setError("Bitte Ort eingeben");
            etLocationLabel.requestFocus();
            return;
        }

        // =========================
        // Daten an MainActivity zurückgeben
        // =========================
        Intent resultIntent = new Intent();
        resultIntent.putExtra("host", selectedHost);
        resultIntent.putExtra("dateTime", dateTime);
        resultIntent.putExtra("location", location);

        setResult(RESULT_OK, resultIntent);

        // Kurze Rückmeldung
        Toast.makeText(this, "Termin gespeichert", Toast.LENGTH_SHORT).show();

        // Activity schließen und zurück zur MainActivity
        finish();
    }
}