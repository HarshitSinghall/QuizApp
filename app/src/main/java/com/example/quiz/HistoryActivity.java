package com.example.quiz;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quiz.CustomRecyclerView.SessionAdapter;
import com.example.quiz.Modles.SessionModel;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView tvEmpty;
    Button btnClear;
    DatabaseHelper dbHelper;
    SessionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.recyclerHistory);
        tvEmpty = findViewById(R.id.tvEmpty);
        btnClear = findViewById(R.id.btnClear);

        dbHelper = new DatabaseHelper(this);
        List<SessionModel> sessions = dbHelper.getAllSessions();

        if (sessions.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new SessionAdapter(this, sessions);
            recyclerView.setAdapter(adapter);
        }

        btnClear.setOnClickListener(v -> {
            dbHelper.clearSessions();
            recreate();
        });


    }
}

