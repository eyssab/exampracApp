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
        holder.score.setText(String.valueOf(score.get(position)));
    }

    @Override
    public int getItemCount() {
        return this.fileName.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Button fileBtn;
        private TextView score;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.fileBtn = view.findViewById(R.id.fileBtn);
            this.score = view.findViewById(R.id.score);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "position : " + getLayoutPosition() + " text : " + this.score.getText(), Toast.LENGTH_SHORT).show();
            Toast.makeText(view.getContext(), "position : " + getLayoutPosition() + " text : " + this.score.getText(), Toast.LENGTH_SHORT).show();
            //Intent intent = new Intent(this, TakeExamActivity.class);
            //intent.putExtra("fileName", title);
            //startActivity(intent);
        }
    }
}