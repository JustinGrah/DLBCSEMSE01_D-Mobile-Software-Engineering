package com.example.myapplication.Session;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.SessionViewHolder> {


    private List<Session> sessions = new ArrayList<>();
    private Map<Integer, String> userNameMap;

    public SessionAdapter(Map<Integer, String> userNameMap) {
        this.userNameMap = userNameMap;
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
        if (hostName == null) hostName = "Unbekannt";


        // Daten in die Views setzen
        holder.tvHost.setText("Host: " + hostName);
        holder.tvDate.setText("Datum: " + session.datetime_start);
        holder.tvLocation.setText("Ort: " + session.location);
        holder.tvTime.setText("Uhrzeit: " + session.datetime_start);
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


}
