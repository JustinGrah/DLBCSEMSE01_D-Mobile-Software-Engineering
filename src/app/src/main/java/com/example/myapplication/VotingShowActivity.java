package com.example.myapplication;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Database.DataStore;
import com.example.myapplication.User.User;
import com.example.myapplication.User.UserSession;
import com.example.myapplication.Voting.VotingGamesDao;
import com.example.myapplication.Voting.VotingGamesResultAdapter;
import com.example.myapplication.Voting.VotingGamesWithGame;

import java.util.List;

public class VotingShowActivity extends AppCompatActivity {
    private User user;
    private ImageButton btnBack;
    private RecyclerView rvVoteResults;
    private VotingGamesResultAdapter resultsAdapter;
    private  int sessionId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting_show);

        this.btnBack = findViewById(R.id.btnBack);
        this.user = UserSession.getUser();
        sessionId = getIntent().getIntExtra("session_id", -1);

        btnBack.setOnClickListener(v -> finish());

        rvVoteResults = findViewById(R.id.rvVoteResults);
        rvVoteResults.setLayoutManager(new LinearLayoutManager(this));

        resultsAdapter = new VotingGamesResultAdapter();
        rvVoteResults.setAdapter(resultsAdapter);

        loadGameVoteResults(sessionId);
    }

    private void loadGameVoteResults(int sessionId) {

        DataStore.databaseWriteExecutor.execute(() -> {

            DataStore db = DataStore.getDatabase(this);
            VotingGamesDao votingDao = db.votingGamesDao();
            List<VotingGamesWithGame> results = votingDao.countGameVotesForSession(sessionId);

            runOnUiThread(() -> {
                resultsAdapter.setResults(results);
            });
        });
    }
}
