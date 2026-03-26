package com.example.myapplication.Voting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Game.Game;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Adapter für den Recycler View zum anzeigen aller games.
public class VotingGamesAdapter extends RecyclerView.Adapter<VotingGamesAdapter.ViewHolder> {

    private Map<Integer, Boolean> selected = new HashMap<>();
    private List<Game> games = new ArrayList<>();

    public void setGames(List<Game> games) {
        this.games = games;
        notifyDataSetChanged();
    }

    public List<Game> getSelectedGames() {
        List<Game> result = new ArrayList<>();
        for (Game g : games) {
            if (selected.getOrDefault(g.id, false)) {
                result.add(g);
            }
        }
        return result;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game_voting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Game game = games.get(position);
        holder.tvGameName.setText(game.name);

        boolean isChecked = selected.getOrDefault(game.id, false);

        holder.cbSelect.setChecked(isChecked);

        // Checkbox click
        holder.cbSelect.setOnCheckedChangeListener((buttonView, checked) -> {
            selected.put(game.id, checked);
        });

        // Clicking the row toggles the checkbox
        holder.itemView.setOnClickListener(v -> {
            boolean newState = !holder.cbSelect.isChecked();
            holder.cbSelect.setChecked(newState);
            selected.put(game.id, newState);
        });
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvGameName;
        CheckBox cbSelect;

        public ViewHolder(View itemView) {
            super(itemView);
            tvGameName = itemView.findViewById(R.id.tvGameName);
            cbSelect = itemView.findViewById(R.id.cbSelect);
        }
    }
}
