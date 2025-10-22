package com.example.quiz.CustomRecyclerView;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quiz.Modles.SessionModel;
import com.example.quiz.R;
import com.example.quiz.SessionDetailsActivity;

import java.util.List;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.ViewHolder> {

    private List<SessionModel> sessionList;
    private Context context;

    public SessionAdapter(Context context, List<SessionModel> sessionList) {
        this.sessionList = sessionList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_session, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SessionModel session = sessionList.get(position);
        holder.tvDate.setText("Date: " + session.getTimestamp());
        holder.tvTotal.setText("Total Questions: " + session.getTotalQuestions());
        holder.tvCorrect.setText("Correct: " + session.getCorrectAnswers());
        holder.tvWrong.setText("Wrong: " + session.getWrongAnswers());
        holder.tvScore.setText("Score: " + session.getScore() + "%");
        holder.tvAttempt.setText("Total Attempted: " + session.getAttemptedQuestions());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SessionDetailsActivity.class);
                intent.putExtra("session_id", session.getId());
                Log.d("harshit--", String.valueOf(session.getId()));
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return sessionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvTotal, tvCorrect, tvWrong, tvScore, tvAttempt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            tvCorrect = itemView.findViewById(R.id.tvCorrect);
            tvWrong = itemView.findViewById(R.id.tvWrong);
            tvScore = itemView.findViewById(R.id.tvScore);
            tvAttempt = itemView.findViewById(R.id.tvAttempted);
        }
    }
}
