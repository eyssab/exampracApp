package com.example.exampracapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.Locale;

public class GenerateExamActivity extends AppCompatActivity {

    String title;
    String finOption;
    int minutes, questions;

    EditText titleInput, finOptionInput, minutesInput, questionsInput;

    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_exam);

        titleInput = findViewById(R.id.titleInput);
        finOptionInput = findViewById(R.id.optionSizeInput);
        minutesInput = findViewById(R.id.timeInput);
        questionsInput = findViewById(R.id.questionNumberInput);
    }

    public void onGenerateBtnClick(View view) {
        file = new File("/data/user/0/com.example.exampracapp/files/" + titleInput.getText().toString().replaceAll(" ", "_") + ".txt");

        if(titleInput.getText().toString().equals("") || finOptionInput.getText().toString().equals("") || Integer.valueOf(minutesInput.getText().toString()).equals("") || Integer.valueOf(questionsInput.getText().toString()).equals("")){
            showToast("Fill all forms first");
        }else if(file.exists()){
            showToast(titleInput.getText().toString() + " already exists");
        }else if(!Character.isLetter(finOptionInput.getText().toString().charAt(0))){
            showToast("Final input must be a letter");
        }else {
            title = titleInput.getText().toString();
            finOption = finOptionInput.getText().toString().toUpperCase(Locale.ROOT);
            minutes = Integer.parseInt(minutesInput.getText().toString());
            questions = Integer.parseInt(questionsInput.getText().toString());
            openActivity3();
        }
    }

    public void openActivity3() {
        Intent intent = new Intent(this, TakeExamActivity.class);
        intent.putExtra("numQuestions", questions);
        intent.putExtra("timerMins", minutes);
        intent.putExtra("finOption", finOption);
        intent.putExtra("title", title.replaceAll(" ", "_"));
        startActivity(intent);
    }

    private void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}