package com.example.myapplication;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Database.DataStore;
import com.example.myapplication.Message.Message;
import com.example.myapplication.Message.MessageAdapter;
import com.example.myapplication.Message.MessageDao;
import com.example.myapplication.User.User;
import com.example.myapplication.User.UserDao;
import com.example.myapplication.User.UserSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    private User user;
    private Map<Integer, String> userNameMap = new HashMap<>();
    private RecyclerView rvChatMessages;
    private MessageAdapter chatAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        this.user = UserSession.getUser();
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_chat);


        btnBack = findViewById(R.id.btnBack);
        btnSendMessage = findViewById(R.id.btnSendMessage);
        etChatMessage = findViewById(R.id.etChatMessage);
        rvChatMessages = findViewById(R.id.rvChatMessages);

        loadUsersForGroup();

        chatAdapter = new MessageAdapter(userNameMap);
        rvChatMessages.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true); // Chat scrollt automatisch nach unten
        rvChatMessages.setLayoutManager(layoutManager);

        // Nachrichten laden
        loadMessages();

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
        String content = etChatMessage.getText().toString().trim();

        /*
            Prüfen, ob die Nachricht leer ist.
            Falls keine Nachricht eingegeben wurde, erscheint eine kurze Meldung.
        */
        if (content.isEmpty()) {
            Toast.makeText(this, "Bitte Nachricht eingeben", Toast.LENGTH_SHORT).show();
            return;
        }


        /*
            Neue TextView wird erstellt, die die Chatnachricht darstellt.
            Diese fungiert als Chat-Bubble im Chatfenster.
        */
//        TextView messageView = new TextView(this);
//        messageView.setText(message);
//        messageView.setTextSize(16f);
//        messageView.setTextColor(getResources().getColor(android.R.color.black));
//        messageView.setBackgroundResource(R.drawable.bg_chat_bubble_right);
//        messageView.setPadding(24, 16, 24, 16);
//
//        messageView.setLayoutParams(params);
//
//        /*
//            Die neue Nachricht wird dem Chat-Layout hinzugefügt.
//        */
//        chatMessagesLayout.addView(messageView);
//
//        // Eingabefeld wird nach dem Senden geleert
//        etChatMessage.setText("");
//
//        /*
//            Scrollt automatisch nach unten,
//            damit die neueste Nachricht sofort sichtbar ist.
//        */
//        chatMessagesContainer.post(() -> chatMessagesContainer.fullScroll(ScrollView.FOCUS_DOWN));

        DataStore.databaseWriteExecutor.execute(() -> {

            DataStore db = DataStore.getDatabase(this);
            MessageDao messageDao = db.messageDao();
            Message message = new Message();
            message.content = content;
            message.group_id = user.groupId;
            message.sender_id = user.id;

            messageDao.addMessage(message);

            runOnUiThread(() -> {
                loadMessages();
            });
        });
    }

    private void loadUsersForGroup() {

        DataStore.databaseWriteExecutor.execute(() -> {

            DataStore db = DataStore.getDatabase(this);
            UserDao userDao = db.userDao();

            List<User> users = userDao.getUsersByGroup(user.groupId);

            // Map: userId → username
            for (User u : users) {
                userNameMap.put(u.id, u.name);
            }
        });
    }

    private void loadMessages() {

        DataStore.databaseWriteExecutor.execute(() -> {

            DataStore db = DataStore.getDatabase(this);
            MessageDao messageDao = db.messageDao();

            List<Message> messages = messageDao.getMessagesFromGroup(user.groupId);

            runOnUiThread(() -> {
                chatAdapter.setMessages(messages);
                rvChatMessages.scrollToPosition(messages.size() - 1);
            });
        });
    }
}