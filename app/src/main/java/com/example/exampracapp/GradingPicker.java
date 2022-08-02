package com.example.exampracapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class GradingPicker extends AppCompatActivity {

    int numQuestions;
    int timerMins;
    String title;
    Question qArray[];
    int answers;

    private static final String FILE_NAME = "example.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grading_picker);

        load();
    }

    public void load() {
        FileInputStream fis = null;

        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String[] firstLineArray;

            int i = 0;
            String line;
            while ((line = br.readLine()) != null) {
                if (i != 0) {
                    firstLineArray = line.split(",");
                    //System.out.println(line);

                    answerButton answerButtons[] = new answerButton[answers];

                    Question newQuestion = new Question(Integer.parseInt(firstLineArray[0]),
                            Integer.parseInt(firstLineArray[1]),
                            answerButtons);

                    int currLineElement = 2;
                    while(currLineElement < firstLineArray.length){
                        ToggleButton newTogBtn = new ToggleButton(this);
                        newTogBtn.setText(firstLineArray[currLineElement+1]);
                        answerButtons[i] = new answerButton( Integer.parseInt(firstLineArray[currLineElement]), newTogBtn);
                        currLineElement+=2;
                    }

                    newQuestion.setButtons(answerButtons);
                    qArray[i-1] = newQuestion;
                    i++;
                } else {
                    firstLineArray = line.split(",");
                    title = firstLineArray[0];
                    timerMins = Integer.parseInt(firstLineArray[1]);
                    numQuestions = Integer.parseInt(firstLineArray[2]);
                    System.out.println(numQuestions);
                    answers = Integer.parseInt(firstLineArray[3]);
                    qArray = new Question[numQuestions];
                    i++;
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void onBtnClick(View view) {

    }
}