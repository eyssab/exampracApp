package com.example.exampracapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    ScrollView scrollView;
    LinearLayout linearLayout;
    LinearLayout innerLinearLayout;

    String fileTitle;
    int width = Resources.getSystem().getDisplayMetrics().widthPixels;

    Double fileScore;
    TextView scoreView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //past exam scrollview
        scrollView = findViewById(R.id.pastExamScroll);
        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(linearLayout);

        //Show past exams by looking at files directory
        File file = new File("/data/user/0/com.example.exampracapp/files");
        String[] arr =file.list();
        if(arr != null) {
            for (String s : arr) {
                if (s.endsWith(".txt")) {
                    System.out.println(s);
                    addPastExam(s);
                }
            }
        }
    }

    public void onNewExamBtnClick(View view) {
        openActivity2();
    }

    public void addPastExam(String title){
        innerLinearLayout = new LinearLayout(this);
        innerLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        load(title);
        Button but = new Button(this);
        if(fileScore == -1){
            but.setLayoutParams(new LinearLayout.LayoutParams((int) ((int)width/1.7), LinearLayout.LayoutParams.WRAP_CONTENT));
            but.setText(title.substring(0,title.length()-4));
            innerLinearLayout.addView(but);
        }else {
            scoreView = new TextView(this);
            scoreView.setText(String.valueOf(Math.round(fileScore)) + "%");
            but.setLayoutParams(new LinearLayout.LayoutParams((int) ((int)width/1.9), LinearLayout.LayoutParams.WRAP_CONTENT));
            but.setText(title.substring(0, title.length() - 4));
            innerLinearLayout.addView(but);
            innerLinearLayout.addView(scoreView);
        }
        linearLayout.addView(innerLinearLayout);
        but.setOnClickListener(v -> {
            openActivity3(title);
        });
    }

    public void load(String fileName) {
        FileInputStream fis = null;

        try {
            fis = openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            //Get title and score of a file given fileName

            String line;
            String[] lineArray;

            boolean firstLine = true;
            while((line = br.readLine()) != null && firstLine) {
                lineArray = line.split(",");
                fileTitle = lineArray[0];
                fileScore = Double.valueOf(lineArray[4]);
                System.out.println(Double.valueOf(lineArray[4]) + "sus");
                firstLine = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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