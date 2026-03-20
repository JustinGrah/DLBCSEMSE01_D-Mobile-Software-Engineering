package com.example.myapplication.Voting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VotingFoodAdapter extends RecyclerView.Adapter<VotingFoodAdapter.ViewHolder> {
    private List<VotingFood> foodVotes = new ArrayList<>();
    private Map<Integer, Boolean> selected = new HashMap<>();

    public void setFoodVotes(List<VotingFood> foodVotes) {
        this.foodVotes = foodVotes;
        notifyDataSetChanged();
    }

    public List<VotingFood> getSelectedFood() {
        List<VotingFood> result = new ArrayList<>();
        for (VotingFood f : foodVotes) {
            if (selected.getOrDefault(f.id, false)) {
                result.add(f);
            }
        }
        return result;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_voting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        VotingFood food = foodVotes.get(position);

        holder.tvFoodName.setText(food.cusinie);

        boolean isSelected = selected.getOrDefault(food.id, false);
        holder.itemView.setAlpha(isSelected ? 1f : 0.5f);

        holder.itemView.setOnClickListener(v -> {
            selected.put(food.id, !isSelected);
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return foodVotes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFoodName;

        public ViewHolder(View itemView) {
            super(itemView);
            tvFoodName = itemView.findViewById(R.id.tvFoodName);
        }
    }

}
