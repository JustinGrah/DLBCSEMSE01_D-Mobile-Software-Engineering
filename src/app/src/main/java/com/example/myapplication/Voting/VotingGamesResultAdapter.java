package com.example.myapplication.Voting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class VotingGamesResultAdapter extends RecyclerView.Adapter<VotingGamesResultAdapter.ViewHolder> {
    private List<VotingGamesWithGame> results = new ArrayList<>();
    private List<String> gameNames = new ArrayList<>();

    public void setResults(List<VotingGamesWithGame> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_voting_game_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        VotingGamesWithGame gameVoting =  results.get(position);
        holder.tvName.setText(gameVoting.name);
        holder.tvVotes.setText(gameVoting.votes + " Stimmen");
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvVotes;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvVotes = itemView.findViewById(R.id.tvVotes);
        }
    }

}
