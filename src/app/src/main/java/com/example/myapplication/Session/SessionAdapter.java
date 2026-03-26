package com.example.myapplication.Session;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Game.Game;
import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

// Adapter zum erstellen eines Recycler Views
public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.SessionViewHolder> {

    // Platzhalter für alle daten welche angezeigt werden müssen
    private List<Session> sessions = new ArrayList<>();
    private Map<Integer, String> userNameMap;
    private List<Game> games;
    private OnSessionClickListener listener;
    private OnRateButtonClickListener rateListener;

    public interface OnSessionClickListener {
        void onSessionClick(Session session);
    }

    public interface OnRateButtonClickListener {
        void onRateButtonClick(Session session);
    }


    // Setter und das verlinken von "Item-Actions"
    public void setOnSessionClickListener(OnSessionClickListener listener) {
        this.listener = listener;
    }

    public void setOnRateButtonClickListener(OnRateButtonClickListener rateListener) {
        this.rateListener = rateListener;
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

    // laden des Items
    @Override
    public SessionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_session, parent, false);
        return new SessionViewHolder(view);
    }

    // befüllen des Items
    @Override
    public void onBindViewHolder(SessionViewHolder holder, int position) {
        Session session = sessions.get(position);
        String hostName = userNameMap.get(session.host_user_id);
        String games = gameNamesToString(this.games);
        if (hostName == null) hostName = "Unbekannt";

        Date date = new Date(session.datetime_start);
        Date nowDate = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onSessionClick(session);
            }
        });

        holder.btnRate.setOnClickListener(v -> {
            if(rateListener != null) {
                rateListener.onRateButtonClick(session);
            }
        });

        String dateStr = dateFormat.format(date);
        String time = timeFormat.format(date);

        // Daten in die Views setzen
        holder.tvHost.setText("Host: " + hostName);
        holder.tvDate.setText("Datum: " + dateStr);
        holder.tvLocation.setText("Ort: " + session.location);
        holder.tvTime.setText("Uhrzeit: " + time);
        holder.tvGames.setText("Spiele: " + games);

        if(date.before(nowDate)) {
            holder.btnRate.setVisibility(View.VISIBLE);
            holder.statusView.setBackgroundResource(R.drawable.bg_status_red_circle);
        } else {
            holder.btnRate.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }

    static class SessionViewHolder extends RecyclerView.ViewHolder {

        TextView tvHost, tvDate, tvLocation, tvTime, tvGames;
        Button btnRate;
        View statusView;

        public SessionViewHolder(View itemView) {
            super(itemView);

            tvHost = itemView.findViewById(R.id.tvHostActive);
            tvDate = itemView.findViewById(R.id.tvDateActive);
            tvLocation = itemView.findViewById(R.id.tvLocationActive);
            tvTime = itemView.findViewById(R.id.tvTimeActive);
            tvGames = itemView.findViewById(R.id.tvGamesActive);
            btnRate = itemView.findViewById(R.id.btnRateEvening);
            statusView = itemView.findViewById(R.id.viewStatus);
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
