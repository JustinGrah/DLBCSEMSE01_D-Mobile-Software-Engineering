package com.example.myapplication.Game;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

// Adapter klasse um Objekte in den Recycler view zu überführen.
public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {
    // Liste mit allen Spielen
    private List<Game> games = new ArrayList<>();

    // Setter methode.
    public void setGames(List<Game> games) {
        this.games = games;
        notifyDataSetChanged();
    }

    // Festlegen des "Item-Layouts".
    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_game, parent, false);
        return new GameViewHolder(view);
    }

    // wir binden hier die einzelnen elemente an das Item-Layout
    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        Game game = games.get(position);

        holder.tvName.setText(game.name);
        holder.tvPlayers.setText(game.min_players + " - " + game.max_players + " Spieler");
        holder.tvDuration.setText(game.description + " Min");
    }

    // Benötigt für die Darstellung aller Items
    @Override
    public int getItemCount() {
        return games.size();
    }

    // Definition der Items
    static class GameViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPlayers, tvDuration;

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvGameName);
            tvPlayers = itemView.findViewById(R.id.tvGamePlayers);
            tvDuration = itemView.findViewById(R.id.tvGameDuration);
        }
    }
}
