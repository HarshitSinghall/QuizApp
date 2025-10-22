package com.example.quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.quiz.Modles.QuestionsModel;
import com.example.quiz.Modles.SessionModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "quiz_sessions.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_SESSIONS = "quiz_sessions";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TOTAL = "total_questions";
    private static final String COLUMN_CORRECT = "correct_answers";
    private static final String COLUMN_WRONG = "wrong_answers";
    private static final String COLUMN_SCORE = "score";
    private static final String COLUMN_TIMESTAMP = "timestamp";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public List<QuestionsModel> getAnswersForSession(long sessionId) {
        List<QuestionsModel> answersList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT question, selected_option, correct_option, is_correct " +
                "FROM session_answers WHERE session_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(sessionId)});

        if (cursor.moveToFirst()) {
            do {
                QuestionsModel q = new QuestionsModel(
                        cursor.getString(cursor.getColumnIndexOrThrow("question")),
                        "", "", "", "",
                        cursor.getString(cursor.getColumnIndexOrThrow("correct_option"))
                );
                q.setSelectedOption(cursor.getString(cursor.getColumnIndexOrThrow("selected_option")));
                answersList.add(q);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return answersList;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String createSessionsTable = "CREATE TABLE quiz_sessions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "quiz_type TEXT, " +
                "total_questions INTEGER, " +
                "correct_answers INTEGER, " +
                "wrong_answers INTEGER, " +
                "score INTEGER, " +
                "timestamp TEXT)";
        db.execSQL(createSessionsTable);

        String createAnswersTable = "CREATE TABLE session_answers (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "session_id INTEGER, " +
                "question TEXT, " +
                "selected_option TEXT, " +
                "correct_option TEXT, " +
                "is_correct INTEGER)";
        db.execSQL(createAnswersTable);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SESSIONS);
        db.execSQL("DROP TABLE IF EXISTS session_answers");
        onCreate(db);
    }

    public List<SessionModel> getAllSessions() {
        List<SessionModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM quiz_sessions ORDER BY id DESC", null);
        if (cursor.moveToFirst()) {
            do {
                list.add(new SessionModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("quiz_type")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("total_questions")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("correct_answers")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("wrong_answers")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("score")),
                        cursor.getString(cursor.getColumnIndexOrThrow("timestamp"))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }



    public void insertAnswer(SQLiteDatabase db, long sessionId, QuestionsModel q) {
        ContentValues values = new ContentValues();
        values.put("session_id", sessionId);
        values.put("question", q.getQuestion());
        values.put("selected_option", q.getSelectedOption());
        values.put("correct_option", q.getCorrectOption());
        values.put("is_correct", q.getSelectedOption() != null &&
                q.getSelectedOption().equals(q.getCorrectOption()) ? 1 : 0);

        db.insert("session_answers", null, values);
    }


    public long insertSession(String quizType, int totalQuestions, int correctCount, int incorrectCount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        int score = (int) ((correctCount / (float) totalQuestions) * 100);
        String timestamp = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()).format(new Date());

        values.put("quiz_type", quizType);
        values.put("total_questions", totalQuestions);
        values.put("correct_answers", correctCount);
        values.put("wrong_answers", incorrectCount);
        values.put("score", score);
        values.put("timestamp", timestamp);

        long sessionId = db.insert("quiz_sessions", null, values);
        db.close();
        return sessionId;
    }




    public void clearSessions() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SESSIONS, null, null);
        db.close();
    }
}
