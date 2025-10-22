package com.example.quiz;

import android.content.Context;

import com.example.quiz.Modles.QuestionsModel;

import java.util.List;

public class DemoQuestionsData {

    public static List<QuestionsModel> getQuestions(Context context) {
        return QuestionsRepository.getRandom30Questions(context);
    }

    public static List<QuestionsModel> getPredefinedSet(Context context, int setNumber) {
        return QuestionsRepository.getPredefinedSet(context, setNumber);
    }
}
