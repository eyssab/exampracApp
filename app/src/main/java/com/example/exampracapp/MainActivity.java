package com.example.exampracapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ScrollView scrollView;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //past exam scrollview
        scrollView = (ScrollView) findViewById(R.id.pastExamScroll);
        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(linearLayout);
    }

    public void onNewExamBtnClick(View view) {
        openActivity2();
    }

    public void addPastExam(){
        Button but = new Button(this);
        but.setText("Lemon");
        linearLayout.addView(but);
    }

    public void openActivity2() {
        Intent intent = new Intent(this, GenerateExamActivity.class);
        startActivity(intent);
    }
}