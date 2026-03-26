package com.example.myapplication;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

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
    private RatingBar ratingBarFood;
    private RatingBar ratingBarOverall;
    private RatingBar ratingBarHost;

    private MaterialButton btnBewerten;
    private BewertungListener listener;

    public interface BewertungListener {
        void onBewertungAbgegeben(int ratingHost, int ratingFood, int ratingOverall);
    }

    public void setBewertungListener(BewertungListener listener) {
        this.listener = listener;
    }

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
        ratingBarHost = view.findViewById(R.id.ratingBarHost);
        ratingBarFood = view.findViewById(R.id.ratingBarFood);
        ratingBarOverall = view.findViewById(R.id.ratingBarOverall);

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

            float ratingHost = ratingBarHost.getRating();
            float ratingFood = ratingBarFood.getRating();
            float ratingOverall = ratingBarOverall.getRating();

            if (listener != null) {
                listener.onBewertungAbgegeben((int) ratingHost, (int) ratingFood, (int) ratingOverall);
            }
            dismiss();
        });
    }
}