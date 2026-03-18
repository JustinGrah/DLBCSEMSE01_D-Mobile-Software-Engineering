package com.example.myapplication.Session;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Game.Game;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.SessionViewHolder> {


    private List<Session> sessions = new ArrayList<>();
    private Map<Integer, String> userNameMap;
    private List<Game> games;
    private OnSessionClickListener listener;

    public interface OnSessionClickListener {
        void onSessionClick(Session session);
    }

    public void setOnSessionClickListener(OnSessionClickListener listener) {
        this.listener = listener;
    }

    public SessionAdapter(Map<Integer, String> userNameMap, List<Game> games) {
        this.userNameMap = userNameMap;
        this.games = games;
    }

    // Liste aktualisieren
    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
        notifyDataSetChanged();
    }

    @Override
    public SessionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_session, parent, false);
        return new SessionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SessionViewHolder holder, int position) {
        Session session = sessions.get(position);
        String hostName = userNameMap.get(session.host_user_id);
        String games = gameNamesToString(this.games);
        if (hostName == null) hostName = "Unbekannt";

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onSessionClick(session);
            }
        });

        // Daten in die Views setzen
        holder.tvHost.setText("Host: " + hostName);
        holder.tvDate.setText("Datum: " + session.datetime_start);
        holder.tvLocation.setText("Ort: " + session.location);
        holder.tvTime.setText("Uhrzeit: " + session.datetime_start);
        holder.tvGames.setText("Spiele: " + games);
    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }

    static class SessionViewHolder extends RecyclerView.ViewHolder {

        TextView tvHost, tvDate, tvLocation, tvTime, tvGames;

        public SessionViewHolder(View itemView) {
            super(itemView);

            tvHost = itemView.findViewById(R.id.tvHostActive);
            tvDate = itemView.findViewById(R.id.tvDateActive);
            tvLocation = itemView.findViewById(R.id.tvLocationActive);
            tvTime = itemView.findViewById(R.id.tvTimeActive);
            tvGames = itemView.findViewById(R.id.tvGamesActive);
        }
    }

    public String gameNamesToString(List<Game> games) {

        if (games == null || games.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for (Game game : games) {
            String name = game.name;
            if (name != null) {
                sb.append(name.toString()).append(" ");
            }
        }

        return sb.toString().trim();
    }
}
