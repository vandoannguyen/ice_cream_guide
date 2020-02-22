package com.example.iscream2_android.answer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iscream2_android.R;
import com.example.iscream2_android.model.Answer;

import java.util.ArrayList;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {
    ArrayList<Answer> list;
    public AnswerAdapter(ArrayList<Answer> list) {
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_anwser,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtNameUser.setText(list.get(position).getUser());
        holder.txtValue.setText(list.get(position).getValue());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameUser, txtValue;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameUser = itemView.findViewById(R.id.txtNameUser);
            txtValue = itemView.findViewById(R.id.txtValue);
        }
    }
}
