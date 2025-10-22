package com.example.quiz;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.example.quiz.Modles.QuestionsModel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionsRepository {

    private static final String TAG = "QuestionsRepository";
    private static final String CSV_FILE_NAME = "india_questions.csv";

    /**
     * Loads all questions from assets/india_questions.csv
     */
    public static List<QuestionsModel> loadAllQuestions(Context context) {
        List<QuestionsModel> questionsList = new ArrayList<>();

        try {
            AssetManager assetManager = context.getAssets();
            InputStream is = assetManager.open(CSV_FILE_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                // Skip header line
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                // Split by comma safely
                String[] parts = line.split(",", -1);

                if (parts.length < 6) {
                    Log.w(TAG, "Skipping malformed line: " + line);
                    continue;
                }

                String question = parts[0].trim();
                String option1 = parts[1].trim();
                String option2 = parts[2].trim();
                String option3 = parts[3].trim();
                String option4 = parts[4].trim();
                String answer = parts[5].trim();

                if (question.isEmpty() || answer.isEmpty()) continue;

                questionsList.add(new QuestionsModel(
                        question, option1, option2, option3, option4, answer
                ));
            }

            reader.close();
            is.close();

            Log.i(TAG, "Loaded " + questionsList.size() + " questions from CSV.");

        } catch (Exception e) {
            Log.e(TAG, "Error reading CSV file: " + e.getMessage());
            e.printStackTrace();
        }

        return questionsList;
    }

    /**
     * Returns 30 random questions from CSV
     */
    public static List<QuestionsModel> getRandom30Questions(Context context) {
        List<QuestionsModel> allQuestions = loadAllQuestions(context);
        if (allQuestions.isEmpty()) return new ArrayList<>();

        Collections.shuffle(allQuestions);
        int limit = Math.min(30, allQuestions.size());
        return new ArrayList<>(allQuestions.subList(0, limit));
    }

    /**
     * Returns predefined 50-question set (for Set 1–5)
     */
    public static List<QuestionsModel> getPredefinedSet(Context context, int setNumber) {
        List<QuestionsModel> allQuestions = loadAllQuestions(context);
        if (allQuestions.isEmpty()) return new ArrayList<>();

        int setSize = 50;
        int startIndex = (setNumber - 1) * setSize;
        int endIndex = Math.min(startIndex + setSize, allQuestions.size());

        if (startIndex >= allQuestions.size()) return new ArrayList<>();

        return new ArrayList<>(allQuestions.subList(startIndex, endIndex));
    }
}
