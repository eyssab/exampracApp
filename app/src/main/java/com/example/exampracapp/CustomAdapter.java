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
    private ArrayList<fileParts> fileParts;
    public CustomAdapter (ArrayList<fileParts> fileParts){
        this.fileParts = fileParts;
    }

    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_view, parent, false);
        return new ViewHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(CustomAdapter.ViewHolder holder, int position) {
        holder.fileBtn.setText(fileParts.get(position).getFileTitle().replaceAll("_", " "));
        int intScore = fileParts.get(position).getCurScore().intValue();
        holder.score.setText(intScore + "%");
    }

    @Override
    public int getItemCount() {
        return this.fileParts.size();
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