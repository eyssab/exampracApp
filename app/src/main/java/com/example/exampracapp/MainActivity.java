package com.example.exampracapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ScrollView scrollView;
    LinearLayout linearLayout;

    int numQuestions;
    int timerMins;
    String title;
    Question qArray[];
    int answers;

    private static final String FILE_NAME = "example.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //past exam scrollview
        scrollView = (ScrollView) findViewById(R.id.pastExamScroll);
        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(linearLayout);

        //Show past exams by looking at files directory
        File file = new File("/data/user/0/com.example.exampracapp/files");
        String arr[]=file.list();
        for(int i = 0; i < arr.length; i++) {
            if (arr[i].endsWith(".txt")) {
                System.out.println(arr[i]);
                addPastExam(arr[i]);
            }
        }
    }

    public void onNewExamBtnClick(View view) {
        openActivity2();
    }

    public void addPastExam(String title){
        Button but = new Button(this);
        but.setText(title.substring(0,title.length()-4));
        linearLayout.addView(but);
        but.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println(title);
                openActivity3(title);
            }
        });
    }

    public void openActivity2() {
        Intent intent = new Intent(this, GenerateExamActivity.class);
        startActivity(intent);
    }

    public void openActivity3(String title) {
        Intent intent = new Intent(this, TakeExamActivity.class);
        intent.putExtra("fileName", title);
        startActivity(intent);
    }
}