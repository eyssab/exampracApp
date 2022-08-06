package com.example.exampracapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private ArrayList<String> fileName;
    private ArrayList<Double> score;
    public CustomAdapter (ArrayList<String> fileName, ArrayList<Double> score){
        this.fileName = fileName;
        this.score = score;
    }

    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_view, parent, false);
        return new ViewHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(CustomAdapter.ViewHolder holder, int position) {
        holder.fileBtn.setText(fileName.get(position));
        int intScore = score.get(position).intValue();
        holder.score.setText(intScore + "%");
    }

    @Override
    public int getItemCount() {
        return this.fileName.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private Button fileBtn;
        private TextView score;

        public ViewHolder(View view) {
            super(view);
            this.fileBtn = view.findViewById(R.id.fileBtn);
            this.score = view.findViewById(R.id.score);
        }
    }
}