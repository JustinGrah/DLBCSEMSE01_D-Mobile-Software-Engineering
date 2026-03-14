package com.example.myapplication;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;

/*
 * BewertungDialogFragment
 *
 * Dieser Dialog wird als kleines Fenster in der Mitte angezeigt.
 * Die aktuelle Activity bleibt im Hintergrund sichtbar.
 *
 * Aktuell enthält der Dialog nur das Frontend.
 * Die eigentliche Speicherung der Bewertung erfolgt später über eine Datenbank.
 */
public class BewertungDialogFragment extends DialogFragment {

    private RatingBar ratingBarAbend;
    private MaterialButton btnBewerten;

    /*
     * onCreateDialog
     *
     * Entfernt den Standardhintergrund des Dialogs,
     * damit das Layout mit abgerundeten Ecken sichtbar bleibt.
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        // Dialog kann geschlossen werden, wenn außerhalb geklickt wird
        dialog.setCanceledOnTouchOutside(true);

        return dialog;
    }

    /*
     * onCreateView
     *
     * Lädt das Layout des Bewertungsdialogs.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.dialog_bewertung, container, false);
    }

    /*
     * onStart
     *
     * Passt die Breite des Dialogfensters an,
     * damit es nicht die gesamte Bildschirmbreite einnimmt.
     */
    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();

        if (dialog != null && dialog.getWindow() != null) {

            int width = (int) (requireContext()
                    .getResources()
                    .getDisplayMetrics()
                    .widthPixels * 0.85);

            dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    /*
     * onViewCreated
     *
     * Verbindet die UI-Elemente mit dem Code
     * und definiert das Verhalten des Bewertungsbuttons.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        // RatingBar aus dem Layout holen
        ratingBarAbend = view.findViewById(R.id.ratingBarAbend);

        // Bewertungsbutton aus dem Layout holen
        btnBewerten = view.findViewById(R.id.btnBewerten);

        /*
         * Klick auf "Bewerten"
         *
         * - Bewertungswert aus der RatingBar lesen
         * - Nachricht anzeigen
         * - Dialog schließen
         */
        btnBewerten.setOnClickListener(v -> {

            float rating = ratingBarAbend.getRating();

            // Feedback für den Benutzer anzeigen
            new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    .setTitle("Info")
                    .setMessage("Bewertung gespeichert: " + rating + " Sterne")
                    .setPositiveButton("OK", null)
                    .show();
            // Dialog schließen
            dismiss();
        });
    }
}