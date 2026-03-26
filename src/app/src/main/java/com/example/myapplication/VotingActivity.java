package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Database.DataStore;
import com.example.myapplication.Game.Game;
import com.example.myapplication.Game.GameDao;
import com.example.myapplication.User.User;
import com.example.myapplication.User.UserSession;
import com.example.myapplication.Voting.VotingGames;
import com.example.myapplication.Voting.VotingGamesAdapter;
import com.example.myapplication.Voting.VotingGamesDao;

import java.util.List;

public class VotingActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private VotingGamesAdapter gameVotingAdapter;
    private RecyclerView rvGameVoting;
    private Button btnSubmitVotes;
    private int sessionId;
    private int groupId;
    private int hostUserId;
    private User user;
    private List<Game> games;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting);

        this.user = UserSession.getUser();

        sessionId = getIntent().getIntExtra("session_id", -1);
        groupId = getIntent().getIntExtra("group_id", -1);
        hostUserId = getIntent().getIntExtra("host_user_id", -1);

        this.btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        rvGameVoting = findViewById(R.id.rvGameVoting);
        btnSubmitVotes = findViewById(R.id.btnSubmitVotes);

        gameVotingAdapter = new VotingGamesAdapter();

        rvGameVoting.setLayoutManager(new LinearLayoutManager(this));

        rvGameVoting.setAdapter(gameVotingAdapter);

        loadVotingData();
        setupSubmitButton();
    }

    private void loadVotingData() {
        DataStore db = DataStore.getDatabase(this);
        GameDao gameDao = db.gameDao();

        DataStore.databaseWriteExecutor.execute(() -> {
            List<Game> games = gameDao.listAllGames();

            runOnUiThread(() -> {
                gameVotingAdapter.setGames(games);
            });
        });
    }

    private void setupSubmitButton() {
        btnSubmitVotes.setOnClickListener(v -> {

            List<Game> selectedGames = gameVotingAdapter.getSelectedGames();

            DataStore.databaseWriteExecutor.execute(() -> {

                DataStore db = DataStore.getDatabase(this);
                VotingGamesDao gameDao = db.votingGamesDao();
                Log.d("UI", "setupSubmitButton: " + selectedGames.toString());

                for(Game game : selectedGames) {
                    Log.d("UI", "Adding game: " + game.name);
                    VotingGames gameVote = new VotingGames();
                    gameVote.game_id = game.id;
                    gameVote.user_id = user.id;
                    gameVote.session_id = sessionId;
                    gameDao.addGameVote(gameVote);
                }

                runOnUiThread(() -> {
                    Intent result = new Intent();
                    result.putExtra("updated", true);
                    setResult(RESULT_OK, result);
                    finish();
                });
            });
        });
    }
}