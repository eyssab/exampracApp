package com.example.exampracapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ScoreViewActivity extends AppCompatActivity {

    Double score;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_view);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            score = bundle.getDouble("score");
            title = bundle.getString("title");
        }

        TextView scoreText = findViewById(R.id.scoreText);
        scoreText.setText(String.valueOf(score.intValue()) + "%");
    }

    public void onReturnBtnDown(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("score", score);
        startActivity(intent);
    }
}