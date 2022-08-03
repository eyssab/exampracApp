package com.example.exampracapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import java.io.File;

public class MainActivity extends AppCompatActivity {

    ScrollView scrollView;
    LinearLayout linearLayout;

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
        Button but = new Button(this);
        but.setText(title.substring(0,title.length()-4));
        linearLayout.addView(but);
        but.setOnClickListener(v -> {
            System.out.println(title);
            openActivity3(title);
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