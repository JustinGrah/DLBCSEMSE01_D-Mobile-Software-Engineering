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

public class BewertungShowDialogFragment extends DialogFragment {
    private RatingBar ratingBarFood;
    private RatingBar ratingBarOverall;
    private RatingBar ratingBarHost;
    private MaterialButton btnBack;

    private float avgFood;
    private float avgHost;
    private float avgOverall;

    BewertungShowDialogFragment(float avgHost, float avgFood, float avgOverall) {
        this.avgHost = avgHost;
        this.avgFood = avgFood;
        this.avgOverall = avgOverall;
    }

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
     *
     * Lädt das Layout des Bewertungsdialogs.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.dialog_bewertung_show, container, false);
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

        ratingBarHost.setEnabled(false);
        ratingBarFood.setEnabled(false);
        ratingBarOverall.setEnabled(false);

        ratingBarHost.setRating(this.avgFood);
        ratingBarFood.setRating(this.avgHost);
        ratingBarOverall.setRating(this.avgOverall);

        // Bewertungsbutton aus dem Layout holen
        btnBack = view.findViewById(R.id.btnDialogBack);

        /*
         * Klick auf "Zurück"
         *
         * - Dialog schließen
         */
        btnBack.setOnClickListener(v -> {
            dismiss();
        });
    }

}
