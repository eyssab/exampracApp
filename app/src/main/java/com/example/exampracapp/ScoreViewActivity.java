package com.example.exampracapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ScoreViewActivity extends AppCompatActivity {

    Double score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_view);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            score = bundle.getDouble("score");
        }

        TextView scoreText = findViewById(R.id.scoreText);
        scoreText.setText(String.valueOf(score) + "%");
    }
}