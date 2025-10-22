package com.example.quiz.CustomRecyclerView;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quiz.Modles.QuestionsModel;
import com.example.quiz.R;

import java.util.List;

public class SessionAnswersAdapter extends RecyclerView.Adapter<SessionAnswersAdapter.ViewHolder> {

    private final Context context;
    private final List<QuestionsModel> answersList;

    public SessionAnswersAdapter(Context context, List<QuestionsModel> answersList) {
        this.context = context;
        this.answersList = answersList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_answer_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuestionsModel q = answersList.get(position);

        holder.tvQuestion.setText((position + 1) + ". " + q.getQuestion());
        holder.tvSelected.setText("Your Answer: " + (q.getSelectedOption() == null ? "Not answered" : q.getSelectedOption()));
        holder.tvCorrect.setText("Correct Answer: " + q.getCorrectOption());

        if (q.getSelectedOption() != null && q.getSelectedOption().equals(q.getCorrectOption())) {
            holder.tvResult.setText("✅ Correct");
            holder.tvResult.setTextColor(context.getColor(R.color.green));
        } else {
            holder.tvResult.setText("❌ Incorrect");
            holder.tvResult.setTextColor(context.getColor(R.color.red));
        }
    }

    @Override
    public int getItemCount() {
        return answersList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestion, tvSelected, tvCorrect, tvResult;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestion = itemView.findViewById(R.id.tvQuestionHistory);
            tvSelected = itemView.findViewById(R.id.tvSelectedAnswer);
            tvCorrect = itemView.findViewById(R.id.tvCorrectAnswer);
            tvResult = itemView.findViewById(R.id.tvAnswerResult);
        }
    }
}

