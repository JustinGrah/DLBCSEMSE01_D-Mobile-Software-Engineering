package com.example.myapplication.Message;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Adapter um alle Nachrichten in einem Recycler View darzustellen
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    // Liste aller Nachrichten
    private List<Message> messages = new ArrayList<>();
    // Hashmap zum identifizieren von Nutzer <--> Nachricht
    private Map<Integer, String> userNameMap;

    public MessageAdapter(Map<Integer, String> userNameMap) {
        this.userNameMap = userNameMap;
    }

    // Setter für Nachrichten
    public void setMessages(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    // Laden des Layouts für die Chat Nachrichten
    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat_message, parent, false);
        return new MessageViewHolder(view);
    }

    // Befüllen des Views mit den daten aus der Nachricht
    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        Message msg = messages.get(position);
        String senderName = userNameMap.get(msg.sender_id);

        holder.tvSender.setText(senderName);
        holder.tvMessage.setText(msg.content);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
    static class MessageViewHolder extends RecyclerView.ViewHolder {

        TextView tvSender, tvMessage;

        public MessageViewHolder(View itemView) {
            super(itemView);
            tvSender = itemView.findViewById(R.id.tvSender);
            tvMessage = itemView.findViewById(R.id.tvMessage);
        }
    }
}
