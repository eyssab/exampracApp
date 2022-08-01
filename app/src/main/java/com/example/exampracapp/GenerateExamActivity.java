package com.example.exampracapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class GenerateExamActivity extends AppCompatActivity {

    String title;
    String finOption;
    int minutes, questions;

    EditText titleInput, finOptionInput, minutesInput, questionsInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_exam);

        titleInput = (EditText) findViewById(R.id.titleInput);
        finOptionInput = (EditText) findViewById(R.id.optionSizeInput);
        minutesInput = (EditText) findViewById(R.id.timeInput);
        questionsInput = (EditText) findViewById(R.id.questionNumberInput);
    }

    public void onGenerateBtnClick(View view) {
        if(titleInput.getText().toString().equals("") || finOptionInput.getText().toString().equals("") || Integer.valueOf(minutesInput.getText().toString()).equals("") || Integer.valueOf(questionsInput.getText().toString()).equals("")){
            showToast("Fill all forms firsdt");
        }else {
            title = titleInput.getText().toString();
            finOption = finOptionInput.getText().toString();
            minutes = Integer.valueOf(minutesInput.getText().toString());
            questions = Integer.valueOf(questionsInput.getText().toString());
            openActivity3();
        }
    }

    public void openActivity3() {
        Intent intent = new Intent(this, TakeExamActivity.class);
        intent.putExtra("numQuestions", questions);
        intent.putExtra("timerMins", minutes);
        intent.putExtra("finOption", finOption);
        intent.putExtra("title", title);
        startActivity(intent);
    }

    private void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}