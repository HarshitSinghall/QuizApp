package com.example.quiz.CustomRecyclerView;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quiz.Modles.QuestionsModel;
import com.example.quiz.R;

import java.util.ArrayList;
import java.util.List;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {

    private List<QuestionsModel> questionList;
    private Context context;

    public QuestionsAdapter(List<QuestionsModel> questionList, Context context) {
        this.questionList = questionList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuestionsModel question = questionList.get(position);

        holder.tvQuestionNumber.setText("Q" + (position + 1));
        holder.tvQuestion.setText(question.getQuestion());
        holder.option1.setText(question.getOption1());
        holder.option2.setText(question.getOption2());
        holder.option3.setText(question.getOption3());
        holder.option4.setText(question.getOption4());

        holder.tvFeedback.setVisibility(View.GONE);
        resetOptionUI(holder);

        holder.layoutOption1.setEnabled(true);
        holder.layoutOption2.setEnabled(true);
        holder.layoutOption3.setEnabled(true);
        holder.layoutOption4.setEnabled(true);

        if (question.getSelectedOption() != null) {
            updateSelectedUI(holder, question.getSelectedOption(), question);

            holder.layoutOption1.setEnabled(false);
            holder.layoutOption2.setEnabled(false);
            holder.layoutOption3.setEnabled(false);
            holder.layoutOption4.setEnabled(false);

            holder.tvFeedback.setVisibility(View.VISIBLE);
            if (question.getSelectedOption().equals(question.getCorrectOption())) {
                holder.tvFeedback.setText("✅ Correct!");
                holder.tvFeedback.setTextColor(context.getColor(R.color.green));
            } else {
                holder.tvFeedback.setText("❌ Incorrect");
                holder.tvFeedback.setTextColor(context.getColor(R.color.red));
            }
        }

        holder.layoutOption1.setOnClickListener(v ->
                handleOptionClick(holder, question, question.getOption1(), holder.iconOption1, holder.layoutOption1));
        holder.layoutOption2.setOnClickListener(v ->
                handleOptionClick(holder, question, question.getOption2(), holder.iconOption2, holder.layoutOption2));
        holder.layoutOption3.setOnClickListener(v ->
                handleOptionClick(holder, question, question.getOption3(), holder.iconOption3, holder.layoutOption3));
        holder.layoutOption4.setOnClickListener(v ->
                handleOptionClick(holder, question, question.getOption4(), holder.iconOption4, holder.layoutOption4));
    }


    @Override
    public int getItemCount() {
        return questionList.size();
    }

    private void handleOptionClick(ViewHolder holder, QuestionsModel question, String selectedText,
                                   ImageView selectedIcon, LinearLayout selectedLayout) {

        if (question.getSelectedOption() != null) {
            android.widget.Toast.makeText(context, "You can't change your answer!", android.widget.Toast.LENGTH_SHORT).show();
            return;
        }

        question.setSelectedOption(selectedText);

        resetOptionUI(holder);

        selectedIcon.setImageResource(R.drawable.ic_radio_checked);
        selectedLayout.setBackgroundResource(R.drawable.bg_option_selected);

        if (selectedText.equals(question.getCorrectOption())) {
            holder.tvFeedback.setText("✅ Correct!");
            holder.tvFeedback.setTextColor(context.getColor(R.color.green));
        } else {
            holder.tvFeedback.setText("❌ Incorrect");
            holder.tvFeedback.setTextColor(context.getColor(R.color.red));
        }

        holder.tvFeedback.setVisibility(View.VISIBLE);

        holder.layoutOption1.setEnabled(false);
        holder.layoutOption2.setEnabled(false);
        holder.layoutOption3.setEnabled(false);
        holder.layoutOption4.setEnabled(false);
    }


    private void resetOptionUI(ViewHolder holder) {
        holder.iconOption1.setImageResource(R.drawable.ic_radio_unchecked);
        holder.iconOption2.setImageResource(R.drawable.ic_radio_unchecked);
        holder.iconOption3.setImageResource(R.drawable.ic_radio_unchecked);
        holder.iconOption4.setImageResource(R.drawable.ic_radio_unchecked);

        holder.layoutOption1.setBackgroundResource(R.drawable.bg_option);
        holder.layoutOption2.setBackgroundResource(R.drawable.bg_option);
        holder.layoutOption3.setBackgroundResource(R.drawable.bg_option);
        holder.layoutOption4.setBackgroundResource(R.drawable.bg_option);
    }

    private void updateSelectedUI(ViewHolder holder, String selected, QuestionsModel question) {
        resetOptionUI(holder);

        if (selected.equals(question.getOption1())) {
            holder.iconOption1.setImageResource(R.drawable.ic_radio_checked);
            holder.layoutOption1.setBackgroundResource(R.drawable.bg_option_selected);
        } else if (selected.equals(question.getOption2())) {
            holder.iconOption2.setImageResource(R.drawable.ic_radio_checked);
            holder.layoutOption2.setBackgroundResource(R.drawable.bg_option_selected);
        } else if (selected.equals(question.getOption3())) {
            holder.iconOption3.setImageResource(R.drawable.ic_radio_checked);
            holder.layoutOption3.setBackgroundResource(R.drawable.bg_option_selected);
        } else if (selected.equals(question.getOption4())) {
            holder.iconOption4.setImageResource(R.drawable.ic_radio_checked);
            holder.layoutOption4.setBackgroundResource(R.drawable.bg_option_selected);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvQuestionNumber, tvQuestion, tvFeedback;
        TextView option1, option2, option3, option4;
        LinearLayout layoutOption1, layoutOption2, layoutOption3, layoutOption4;
        ImageView iconOption1, iconOption2, iconOption3, iconOption4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestionNumber = itemView.findViewById(R.id.tvQuestionNumber);
            tvQuestion = itemView.findViewById(R.id.tvQuestion);
            tvFeedback = itemView.findViewById(R.id.tvFeedback);

            layoutOption1 = itemView.findViewById(R.id.layoutOption1);
            layoutOption2 = itemView.findViewById(R.id.layoutOption2);
            layoutOption3 = itemView.findViewById(R.id.layoutOption3);
            layoutOption4 = itemView.findViewById(R.id.layoutOption4);

            option1 = itemView.findViewById(R.id.option1);
            option2 = itemView.findViewById(R.id.option2);
            option3 = itemView.findViewById(R.id.option3);
            option4 = itemView.findViewById(R.id.option4);

            iconOption1 = itemView.findViewById(R.id.iconOption1);
            iconOption2 = itemView.findViewById(R.id.iconOption2);
            iconOption3 = itemView.findViewById(R.id.iconOption3);
            iconOption4 = itemView.findViewById(R.id.iconOption4);
        }
    }
}
