package com.example.quiz.Modles;

public class SessionModel {
    private int id;
    private int totalQuestions;
    private int attemptedQuestions;
    private int correctAnswers;
    private int wrongAnswers;
    private int score;
    private String timestamp;

    public SessionModel(int id, String quizType, int totalQuestions, int correctAnswers, int wrongAnswers, int score, String timestamp) {
        this.id = id;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.wrongAnswers = wrongAnswers;
        this.score = score;
        this.timestamp = timestamp;
        this.attemptedQuestions=correctAnswers+wrongAnswers;
    }

    public int getId() { return id; }
    public int getTotalQuestions() { return totalQuestions; }
    public int getCorrectAnswers() { return correctAnswers; }
    public int getWrongAnswers() { return wrongAnswers; }
    public int getScore() { return score; }
    public String getTimestamp() { return timestamp; }

    public int getAttemptedQuestions() {
        return attemptedQuestions;
    }

    public void setAttemptedQuestions(int attemptedQuestions) {
        this.attemptedQuestions = attemptedQuestions;
    }
}
