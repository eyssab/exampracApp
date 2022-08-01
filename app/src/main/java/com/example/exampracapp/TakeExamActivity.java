package com.example.exampracapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

public class TakeExamActivity extends AppCompatActivity implements Serializable {

    ScrollView scrollView;
    LinearLayout linearLayout;

    //Array of Questions to each hold(questionNumber, selectedLetter, and correctLetter)
    Question qArray[];
    int numQuestions;
    int timerMins;
    String title;
    String finOption;
    int answers;
    int width = Resources.getSystem().getDisplayMetrics().widthPixels;

    //STORAGE
    private final static String FILE_NAME = "example.txt";
    String finLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //auto generated code
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_exam);

        //Question overall scrollview
        scrollView = (ScrollView) findViewById(R.id.pastExamScroll);
        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(linearLayout);

        //Grab all data from past activity
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            numQuestions = bundle.getInt("numQuestions");
            timerMins = bundle.getInt("timerMins");
            title = bundle.getString("title");
            finOption = bundle.getString("finOption");
        }

        //Setting Title
        TextView titleView = findViewById(R.id.titleText);
        titleView.setText(title);

        //find amount of answer choices needed and assigned to int answers
        char finOptionchar = finOption.charAt(0);
        while(finOptionchar >= 'A'){
            finOptionchar--;
            answers++;
        }

        //Initialize qArray based on user inputted question amount
        qArray = new Question[numQuestions];
        for(int i = 0; i<qArray.length; i++){
            qArray[i] = new Question(i+1, 0, null);
            addQuestion(qArray[i]);
        }
    }

    public void addQuestion(Question que){
        //Creates each question HORIZONTAL scrollview within the main VERTICAL scrollview to render each question properly
        LinearLayout linearInnerLayout = new LinearLayout(this);
        linearInnerLayout.setOrientation(LinearLayout.HORIZONTAL);

        //rendering number text
        TextView numb = new TextView(this);
        numb.setLayoutParams(new LinearLayout.LayoutParams(60, LinearLayout.LayoutParams.WRAP_CONTENT));
        numb.setText(String.valueOf(que.number));
        linearInnerLayout.addView(numb);

        //button array with answer amount of elements
        answerButton buttonS[] = new answerButton[answers];


        //Generate answer button based on finOption
        for(int i = 0; i < answers;i++){
            buttonS[i] = new answerButton( ((int) 'A' + i), new ToggleButton(this));
            char number2Letter = (char)('A' + i);
            buttonS[i].getButton().setText(Character.toString(number2Letter));
            buttonS[i].getButton().setTextOn(Character.toString(number2Letter));
            buttonS[i].getButton().setTextOff(Character.toString(number2Letter));
            buttonS[i].getButton().setLayoutParams(new LinearLayout.LayoutParams((width-100)/answers, LinearLayout.LayoutParams.WRAP_CONTENT));

            linearInnerLayout.addView(buttonS[i].button);
        }

        //Setting button array to que to be saved for later
        que.setButtons(buttonS);

        //adding the whole linear layout into the main scrollview linear layout
        linearLayout.addView(linearInnerLayout);
    }

    public void save(View view){
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(finLine.getBytes());

            System.out.println("Saved to " + getFilesDir() + "/" + FILE_NAME);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void onSaveBtnClick(View view) {
        finLine = title + "," + timerMins + "," + numQuestions + "," + answers + "\n";
        for(int i = 0; i < numQuestions; i++){
            addWholeString(i);
        }
        save(view);
        openActivity4();
    }

    public void addWholeString(int i){
        //Run through all of qArray[i] and get all data of each button together into allButtons
        String allButtons = "";

        for(int x = 0; x<answers; x++){
            allButtons += qArray[i].getButtons()[x].toString();
        }

        //title, mins, #questions(newline)
        //question#, 65, buttonData(newline)
        //call to add each questions data
        finLine += qArray[i].number
                + "," + qArray[i].correctLetterElement + allButtons + "\n";
    }

    public void onDoneBtnClick(View view) {
        //Send all Data to each activity until it reaches home page
        openActivity4();
    }

    //Sending numQuestions, timerMins, title, and the whole questionArray for grading in next Activity
    public void openActivity4() {
        Intent intent = new Intent(this, GradingPicker.class);
        startActivity(intent);
    }

    //Sending numQuestions, timerMins, title, and the whole questionArray for grading in next Activity
    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}