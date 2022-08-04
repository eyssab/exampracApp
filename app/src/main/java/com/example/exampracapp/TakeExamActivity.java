package com.example.exampracapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TakeExamActivity extends AppCompatActivity{

    ScrollView scrollView;
    LinearLayout linearLayout;

    //Array of Questions to each hold(questionNumber, selectedLetter, and correctLetter)
    Question[] qArray;
    int numQuestions;
    int timerMins;
    String title;
    String finOption;
    int answers;
    int width = Resources.getSystem().getDisplayMetrics().widthPixels;

    //STORAGE
    private static String fileName;
    String finLine;
    ArrayList<Boolean> questionChecksArray;
    ArrayList<Boolean> questionCorrectAnswersArray;
    boolean openingSave = false;
    boolean isGrading = false;
    double score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //auto generated code
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_exam);

        TextView titleView = findViewById(R.id.titleText);
        Button saveBtn = findViewById(R.id.saveButton);
        Button doneBtn = findViewById(R.id.doneButton);

        //Question overall scrollview
        scrollView = findViewById(R.id.pastExamScroll);
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
            fileName = bundle.getString("fileName");
            isGrading = bundle.getBoolean("isGrading");
        }

        //Stuff to initialize when grading
        if(isGrading) {
            doneBtn.setText("Grade");
            saveBtn.setText("Back");
        }

        //Opening saved file and generating exam
        if(fileName != null){
            openingSave = true;
            questionChecksArray = new ArrayList<>();
            questionCorrectAnswersArray = new ArrayList<>();
            load(fileName);

            titleView.setText(title);
            for (int i = 0; i < qArray.length; i++) {
                qArray[i] = new Question(i + 1, 0, null);
                addQuestion(qArray[i], i);
            }

        }else {
            //Generating new exam from scratch
            fileName = title + ".txt";

            //Setting Title
            titleView.setText(title);

            //find amount of answer choices needed and assigned to int answers
            char finOptionchar = finOption.charAt(0);
            while (finOptionchar >= 'A') {
                finOptionchar--;
                answers++;
            }

            //Initialize qArray based on user inputted question amount
            qArray = new Question[numQuestions];
            for (int i = 0; i < qArray.length; i++) {
                qArray[i] = new Question(i + 1, 0, null);
                addQuestion(qArray[i], i);
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    public void addQuestion(Question que, int qNumber){
        //Creates each question HORIZONTAL scrollview within the main VERTICAL scrollview to render each question properly
        LinearLayout linearInnerLayout = new LinearLayout(this);
        linearInnerLayout.setOrientation(LinearLayout.HORIZONTAL);

        //rendering number text
        TextView numb = new TextView(this);
        numb.setLayoutParams(new LinearLayout.LayoutParams(60, LinearLayout.LayoutParams.WRAP_CONTENT));
        numb.setText(String.valueOf(que.number));
        linearInnerLayout.addView(numb);

        //button array with answer amount of elements
        answerButton[] buttonS = new answerButton[answers];

        //Generate answer button based on finOption
        for(int i = 0; i < answers;i++){
            buttonS[i] = new answerButton( ((int) 'A' + i), new ToggleButton(this), false, isGrading);
            char number2Letter = (char)('A' + i);
            buttonS[i].getButton().setText(Character.toString(number2Letter));
            buttonS[i].getButton().setTextOn(Character.toString(number2Letter));
            buttonS[i].getButton().setTextOff(Character.toString(number2Letter));
            if(openingSave && !isGrading) {
                buttonS[i].getButton().setChecked(questionChecksArray.get(i + answers * qNumber));
            }
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
            fos = openFileOutput(fileName, MODE_PRIVATE);
            fos.write(finLine.getBytes());

            System.out.println("Saved to " + getFilesDir() + "/" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void load(String fileName) {
        FileInputStream fis = null;

        try {
            fis = openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String[] lineArray;
            String line;
            boolean isFirstLine = true;

            while((line = br.readLine()) != null){
                lineArray = line.split(",");
                if (!isFirstLine) {
                    //Write from txt file to questionChecksArray
                    for(int i = 0; i < answers; i++) {
                        questionChecksArray.add(Boolean.valueOf(lineArray[i]));
                    }
                } else {
                    //Parse first line for data
                    lineArray = line.split(",");
                    title = lineArray[0];
                    timerMins = Integer.parseInt(lineArray[1]);
                    numQuestions = Integer.parseInt(lineArray[2]);
                    answers = Integer.parseInt(lineArray[3]);
                    qArray = new Question[numQuestions];
                    isFirstLine = false;
                }
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

    public void onSaveBtnClick(View view) {
        if(!isGrading) {
            finLine = title + "," + timerMins + "," + numQuestions + "," + answers + "," + score + "\n";
            for (int i = 0; i < numQuestions; i++) {
                addWholeString(i);
            }
            save(view);
            openMainActivity();
        }else{
            openMainActivity();
        }
    }

    public void gradeExam(){
        //Build array of isChecked then compare the 2 arrays
        for (int i = 0; i < numQuestions; i++) {
            correctArrayBuilder(i);
        }

        //Accounts for multiple selections then grades
        for(int i = 0;i < numQuestions; i++){
            boolean qCorrect = false;
            for(int z = 0; z < answers; z++) {
                if ((questionCorrectAnswersArray.get(z+i*answers) == true && questionChecksArray.get(z+i*answers) == true)) {
                    qCorrect = true;
                }else if((questionCorrectAnswersArray.get(z+i*answers) == false && questionChecksArray.get(z+i*answers) == false)){
                }else{
                    qCorrect = false;
                    z =answers;
                }
            }
            if(qCorrect){
                score++;
            }
        }
    }

    public void addWholeString(int i){
        //Run through all of qArray[i] and get all data of each button together into allButtons
        String allButtons = "";

        for(int x = 0; x<answers; x++){
            if(x != answers-1){
                allButtons += qArray[i].getButtons()[x].getButton().isChecked() + ",";
            }else{
                allButtons += qArray[i].getButtons()[x].getButton().isChecked();
            }
        }

        //allows to see checked buttons from save file
        finLine += allButtons + "\n";
    }

    public void correctArrayBuilder(int i){
        //Run through all of qArray and put all isCorrect values into one array for grading
        for(int x = 0; x<answers; x++){
                questionCorrectAnswersArray.add(qArray[i].getButtons()[x].getButton().isChecked());
        }
    }

    public void onDoneBtnClick(View view) {
        //Send all Data to each activity until it reaches home page
        if(!isGrading) {
            finLine = title + "," + timerMins + "," + numQuestions + "," + answers + "," + score + "\n";
            for (int i = 0; i < numQuestions; i++) {
                addWholeString(i);
            }
            save(view);
            openGradingPicker();
        }else{
            gradeExam();
            score = score/(double)numQuestions * 100;
            finLine = title + "," + timerMins + "," + numQuestions + "," + answers + "," + score + "\n";
            for (int i = 0; i < numQuestions; i++) {
                addWholeString(i);
            }
            save(view);
            System.out.println(score);
            openScoreViewActivity();
        }
    }

    //Sending numQuestions, timerMins, title, and the whole questionArray for grading in next Activity
    public void openGradingPicker() {
        Intent intent = new Intent(this, GradingPicker.class);
        intent.putExtra("title", title);
        intent.putExtra("fileName", fileName);
        startActivity(intent);
    }

    //Sending numQuestions, timerMins, title, and the whole questionArray for grading in next Activity
    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("title", title);
        startActivity(intent);
    }

    public void openScoreViewActivity() {
        Intent intent = new Intent(this, ScoreViewActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("score", score);
        startActivity(intent);
    }

}