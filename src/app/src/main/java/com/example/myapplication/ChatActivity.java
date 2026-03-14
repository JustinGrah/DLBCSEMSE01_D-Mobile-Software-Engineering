package com.example.boardgameapp;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/*
    Activity für den Chat-Bildschirm der App.

    Diese Klasse steuert die Benutzeroberfläche des Chats.
    Nutzer können Nachrichten eingeben und senden.
    Gesendete Nachrichten werden dynamisch im Chatbereich angezeigt.
*/
public class ChatActivity extends AppCompatActivity {


    private ImageButton btnBack;
    private ImageButton btnSendMessage;
    private EditText etChatMessage;
    private LinearLayout chatMessagesLayout;
    private ScrollView chatMessagesContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_chat);


        btnBack = findViewById(R.id.btnBack);
        btnSendMessage = findViewById(R.id.btnSendMessage);
        etChatMessage = findViewById(R.id.etChatMessage);
        chatMessagesLayout = findViewById(R.id.chatMessagesLayout);
        chatMessagesContainer = findViewById(R.id.chatMessagesContainer);

        /*
            Klicklistener für den Zurück-Button.
            Beendet die aktuelle Activity und kehrt zur vorherigen Seite zurück.
        */
        btnBack.setOnClickListener(v -> finish());

        /*
            Klicklistener für den Senden-Button.
            Beim Klick wird die Methode sendMessage() aufgerufen.
        */
        btnSendMessage.setOnClickListener(v -> sendMessage());
    }

    /*
        Methode zum Versenden einer Chatnachricht.

        Die eingegebene Nachricht wird aus dem Textfeld gelesen,
        als neue TextView erstellt und anschließend im Chatbereich angezeigt.
    */
    private void sendMessage() {

        // Nachricht aus dem Eingabefeld auslesen und Leerzeichen entfernen
        String message = etChatMessage.getText().toString().trim();

        /*
            Prüfen, ob die Nachricht leer ist.
            Falls keine Nachricht eingegeben wurde, erscheint eine kurze Meldung.
        */
        if (message.isEmpty()) {
            Toast.makeText(this, "Bitte Nachricht eingeben", Toast.LENGTH_SHORT).show();
            return;
        }

        /*
            Neue TextView wird erstellt, die die Chatnachricht darstellt.
            Diese fungiert als Chat-Bubble im Chatfenster.
        */
        TextView messageView = new TextView(this);
        messageView.setText(message);
        messageView.setTextSize(16f);
        messageView.setTextColor(getResources().getColor(android.R.color.black));
        messageView.setBackgroundResource(R.drawable.bg_chat_bubble_right);
        messageView.setPadding(24, 16, 24, 16);

        /*
            Layoutparameter für die Positionierung der Nachricht im Chat.

            WRAP_CONTENT sorgt dafür, dass die Nachricht nur so breit wie nötig ist.
            Gravity.END positioniert die Nachricht rechts (eigene Nachricht).
        */
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.gravity = Gravity.END;
        params.topMargin = 12;

        messageView.setLayoutParams(params);

        /*
            Die neue Nachricht wird dem Chat-Layout hinzugefügt.
        */
        chatMessagesLayout.addView(messageView);

        // Eingabefeld wird nach dem Senden geleert
        etChatMessage.setText("");

        /*
            Scrollt automatisch nach unten,
            damit die neueste Nachricht sofort sichtbar ist.
        */
        chatMessagesContainer.post(() -> chatMessagesContainer.fullScroll(ScrollView.FOCUS_DOWN));
    }
}