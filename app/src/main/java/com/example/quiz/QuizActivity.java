package com.example.quiz;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.quiz.CustomRecyclerView.QuestionsAdapter;
import com.example.quiz.Modles.QuestionsModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button btnPrev, btnNext, btnSubmit, btnRestart;
    TextView tvProgress, tvCorrectCount, tvIncorrectCount;
    View reportContainer;
    List<QuestionsModel> questionsList;
    QuestionsAdapter adapter;
    LinearLayoutManager layoutManager;
    int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz);


        recyclerView = findViewById(R.id.recyclerQuestions);
        btnPrev = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnRestart = findViewById(R.id.btnRestart);
        tvProgress = findViewById(R.id.tvProgress);
        tvCorrectCount = findViewById(R.id.tvCorrectCount);
        tvIncorrectCount = findViewById(R.id.tvIncorrectCount);
        reportContainer = findViewById(R.id.reportContainer);

        Intent intent = getIntent();
        int quizType = intent.getIntExtra("type",0);


        if(quizType==1){
            questionsList = DemoQuestionsData.getQuestions(this);
        }else{
            Random rand = new Random();
            int randomIndex = rand.nextInt(5);
            questionsList = DemoQuestionsData.getPredefinedSet(this, randomIndex);
        }


        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new QuestionsAdapter(questionsList, this);
        recyclerView.setAdapter(adapter);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        updateButtonVisibility();
        updateProgressText();


        btnNext.setOnClickListener(v -> {
            if (currentPosition < questionsList.size() - 1) {
                currentPosition++;
                recyclerView.smoothScrollToPosition(currentPosition);
                updateProgressText();
            }
            updateButtonVisibility();
        });


        btnPrev.setOnClickListener(v -> {
            if (currentPosition > 0) {
                currentPosition--;
                recyclerView.smoothScrollToPosition(currentPosition);
                updateProgressText();
            }
            updateButtonVisibility();
        });


        btnSubmit.setOnClickListener(v -> showReport());


        btnRestart.setOnClickListener(v -> exit());
    }

    private void updateButtonVisibility() {
        btnPrev.setVisibility(currentPosition == 0 ? View.INVISIBLE : View.VISIBLE);

        // Show Submit only on last question
        if (currentPosition == questionsList.size() - 1) {
            btnNext.setVisibility(View.GONE);
            btnSubmit.setVisibility(View.VISIBLE);
        } else {
            btnNext.setVisibility(View.VISIBLE);
            btnSubmit.setVisibility(View.GONE);
        }
    }

    private void updateProgressText() {
        String progressText = "Question " + (currentPosition + 1) + " / " + questionsList.size();
        tvProgress.setText(progressText);
    }


    private void showReport() {
        int correctCount = 0;
        int incorrectCount = 0;

        for (QuestionsModel q : questionsList) {
            String selected = q.getSelectedOption();
            if (selected != null) {
                if (selected.equals(q.getCorrectOption())) {
                    correctCount++;
                } else {
                    incorrectCount++;
                }
            }
        }

        int totalQuestions = questionsList.size();

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        String quizType = "Random"; // or "Set 1" if loading predefined


        long sessionId = dbHelper.insertSession(quizType, totalQuestions, correctCount, incorrectCount);


        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (QuestionsModel q : questionsList) {
            dbHelper.insertAnswer(db, sessionId, q);
        }
        db.close();


        recyclerView.setVisibility(View.GONE);
        btnPrev.setVisibility(View.GONE);
        btnNext.setVisibility(View.GONE);
        btnSubmit.setVisibility(View.GONE);
        tvProgress.setVisibility(View.GONE);
        reportContainer.setVisibility(View.VISIBLE);

        tvCorrectCount.setText("✅ Correct: " + correctCount);
        tvIncorrectCount.setText("❌ Incorrect: " + incorrectCount);
    }






    private void exit(){
        startActivity(new Intent(this, MainActivity.class));
    }


}
