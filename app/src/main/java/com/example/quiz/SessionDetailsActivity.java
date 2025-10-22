package com.example.quiz;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quiz.CustomRecyclerView.SessionAnswersAdapter;
import com.example.quiz.Modles.QuestionsModel;

import java.util.List;

public class SessionDetailsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SessionAnswersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_details);

        recyclerView = findViewById(R.id.recyclerSessionAnswers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        int sessionId = getIntent().getIntExtra("session_id", -1);
        Toast.makeText(SessionDetailsActivity.this, ""+sessionId, Toast.LENGTH_SHORT).show();
        if (sessionId != -1) {
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            List<QuestionsModel> answersList = dbHelper.getAnswersForSession(sessionId);
            adapter = new SessionAnswersAdapter(this, answersList);
            recyclerView.setAdapter(adapter);
        }
    }
}
