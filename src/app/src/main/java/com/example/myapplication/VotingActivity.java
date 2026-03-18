package com.example.myapplication;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VotingActivity extends AppCompatActivity {

    /*
     * Navigations-Element
     */
    private ImageButton btnBack;

    /*
     * Spiele-Voting: Checkboxes, Balken und Prozenttexte
     */
    private MaterialCheckBox cbGame1;
    private MaterialCheckBox cbGame2;
    private MaterialCheckBox cbGame3;
    private MaterialCheckBox cbGame4;

    private LinearProgressIndicator progressGame1;
    private LinearProgressIndicator progressGame2;
    private LinearProgressIndicator progressGame3;
    private LinearProgressIndicator progressGame4;

    private TextView tvProgressGame1;
    private TextView tvProgressGame2;
    private TextView tvProgressGame3;
    private TextView tvProgressGame4;

    /*
     * Essen-Voting: Checkboxes, Balken und Prozenttexte
     */
    private MaterialCheckBox cbFoodPizza;
    private MaterialCheckBox cbFoodBurger;
    private MaterialCheckBox cbFoodAsian;
    private MaterialCheckBox cbFoodItalian;

    private LinearProgressIndicator progressFoodPizza;
    private LinearProgressIndicator progressFoodBurger;
    private LinearProgressIndicator progressFoodAsian;
    private LinearProgressIndicator progressFoodItalian;

    private TextView tvProgressFoodPizza;
    private TextView tvProgressFoodBurger;
    private TextView tvProgressFoodAsian;
    private TextView tvProgressFoodItalian;

    /*
     * Hilfslisten für spätere Logik
     */
    private final List<MaterialCheckBox> gameCheckBoxes = new ArrayList<>();
    private final List<LinearProgressIndicator> gameProgressBars = new ArrayList<>();
    private final List<TextView> gameProgressLabels = new ArrayList<>();

    private final List<MaterialCheckBox> foodCheckBoxes = new ArrayList<>();
    private final List<LinearProgressIndicator> foodProgressBars = new ArrayList<>();
    private final List<TextView> foodProgressLabels = new ArrayList<>();

    // Termin-Datum, das aus der MainActivity kommt
    private String dateTimeFromIntent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting);

//        /*
//         * Datum des aktiven Termins auslesen
//         */
//        dateTimeFromIntent = getIntent().getStringExtra("dateTime");
//
//        /*
//         * Alle Views mit den IDs aus dem Layout verbinden
//         */
//        initViews();
//
//        /*
//         * Views in Listen sammeln
//         */
//        initCollections();
//
//        /*
//         * Zurück-Pfeil
//         */
//        setupBackButton();
//
//        /*
//         * Auswahl-Limits
//         */
//        setupPlaceholderBehaviour();
//
//        /*
//         * Voting deaktivieren, wenn der Termin
//         * in weniger als 24 Stunden stattfindet
//         */
//        disableVotingIfNeeded();
    }

    private void initViews() {
//        btnBack = findViewById(R.id.btnBack);
//
//        cbGame1 = findViewById(R.id.cbGame1);
//        cbGame2 = findViewById(R.id.cbGame2);
//        cbGame3 = findViewById(R.id.cbGame3);
//        cbGame4 = findViewById(R.id.cbGame4);
//
//        progressGame1 = findViewById(R.id.progressGame1);
//        progressGame2 = findViewById(R.id.progressGame2);
//        progressGame3 = findViewById(R.id.progressGame3);
//        progressGame4 = findViewById(R.id.progressGame4);
//
//        tvProgressGame1 = findViewById(R.id.tvProgressGame1);
//        tvProgressGame2 = findViewById(R.id.tvProgressGame2);
//        tvProgressGame3 = findViewById(R.id.tvProgressGame3);
//        tvProgressGame4 = findViewById(R.id.tvProgressGame4);
//
//        cbFoodPizza = findViewById(R.id.cbFoodPizza);
//        cbFoodBurger = findViewById(R.id.cbFoodBurger);
//        cbFoodAsian = findViewById(R.id.cbFoodAsian);
//        cbFoodItalian = findViewById(R.id.cbFoodItalian);
//
//        progressFoodPizza = findViewById(R.id.progressFoodPizza);
//        progressFoodBurger = findViewById(R.id.progressFoodBurger);
//        progressFoodAsian = findViewById(R.id.progressFoodAsian);
//        progressFoodItalian = findViewById(R.id.progressFoodItalian);
//
//        tvProgressFoodPizza = findViewById(R.id.tvProgressFoodPizza);
//        tvProgressFoodBurger = findViewById(R.id.tvProgressFoodBurger);
//        tvProgressFoodAsian = findViewById(R.id.tvProgressFoodAsian);
//        tvProgressFoodItalian = findViewById(R.id.tvProgressFoodItalian);
    }

    private void initCollections() {
        gameCheckBoxes.add(cbGame1);
        gameCheckBoxes.add(cbGame2);
        gameCheckBoxes.add(cbGame3);
        gameCheckBoxes.add(cbGame4);

        gameProgressBars.add(progressGame1);
        gameProgressBars.add(progressGame2);
        gameProgressBars.add(progressGame3);
        gameProgressBars.add(progressGame4);

        gameProgressLabels.add(tvProgressGame1);
        gameProgressLabels.add(tvProgressGame2);
        gameProgressLabels.add(tvProgressGame3);
        gameProgressLabels.add(tvProgressGame4);

        foodCheckBoxes.add(cbFoodPizza);
        foodCheckBoxes.add(cbFoodBurger);
        foodCheckBoxes.add(cbFoodAsian);
        foodCheckBoxes.add(cbFoodItalian);

        foodProgressBars.add(progressFoodPizza);
        foodProgressBars.add(progressFoodBurger);
        foodProgressBars.add(progressFoodAsian);
        foodProgressBars.add(progressFoodItalian);

        foodProgressLabels.add(tvProgressFoodPizza);
        foodProgressLabels.add(tvProgressFoodBurger);
        foodProgressLabels.add(tvProgressFoodAsian);
        foodProgressLabels.add(tvProgressFoodItalian);
    }

    /*
     * Zurück-Pfeil schließt die Activity
     */
    private void setupBackButton() {
        btnBack.setOnClickListener(v -> finish());
    }

    /*
     * Auswahl-Limits:
     * - maximal 4 Spiele
     * - maximal 2 Essensrichtungen
     */
    private void setupPlaceholderBehaviour() {

        for (MaterialCheckBox checkBox : gameCheckBoxes) {
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked && countChecked(gameCheckBoxes) > 4) {
                    buttonView.setChecked(false);
                }
            });
        }

        for (MaterialCheckBox checkBox : foodCheckBoxes) {
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked && countChecked(foodCheckBoxes) > 2) {
                    buttonView.setChecked(false);
                }
            });
        }
    }

    /*
     * Deaktiviert das Voting,
     * wenn der Termin in weniger als 24 Stunden stattfindet.
     */
    private void disableVotingIfNeeded() {
        if (!isVotingStillAllowed(dateTimeFromIntent)) {

            for (MaterialCheckBox checkBox : gameCheckBoxes) {
                checkBox.setEnabled(false);
            }

            for (MaterialCheckBox checkBox : foodCheckBoxes) {
                checkBox.setEnabled(false);
            }
        }
    }

    /*
     * Prüft, ob das Voting zeitlich noch erlaubt ist.
     */
    private boolean isVotingStillAllowed(String dateTime) {
        if (dateTime == null || dateTime.isEmpty()) {
            return false;
        }

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

    /*
     * Hilfsmethode für später:
     * Setzt einen Prozentwert auf einen Material-Balken
     * und aktualisiert den Text.
     */
    private void updateProgress(LinearProgressIndicator progressBar, TextView label, int percent) {
        int safePercent = Math.max(0, Math.min(percent, 100));
        progressBar.setProgress(safePercent);
        label.setText(safePercent + " %");
    }

    /*
     * Hilfsmethode:
     * Zählt, wie viele Checkboxen in einer Gruppe ausgewählt sind.
     */
    private int countChecked(List<MaterialCheckBox> checkBoxes) {
        int count = 0;
        for (MaterialCheckBox checkBox : checkBoxes) {
            if (checkBox.isChecked()) {
                count++;
            }
        }
        return count;
    }
}