package com.example.exampracapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class TakeExamActivity extends AppCompatActivity{

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
    private static String fileName;
    String finLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //auto generated code
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_exam);

        TextView titleView = findViewById(R.id.titleText);

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
            fileName = bundle.getString("fileName");
        }

        //Opening saved file and generating exam
        if(fileName != null){
            load(fileName);

            titleView.setText(title);
            for (int i = 0; i < qArray.length; i++) {
                qArray[i] = new Question(i + 1, 0, null);
                addQuestion(qArray[i]);
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
                addQuestion(qArray[i]);
            }
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
            fos = openFileOutput(fileName, MODE_PRIVATE);
            fos.write(finLine.getBytes());

            System.out.println("Saved to " + getFilesDir() + "/" + fileName);
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

    public void load(String FILE_NAME) {
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
                    while(currLineElement < firstLineArray.length-1){
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

    public void onSaveBtnClick(View view) {
        finLine = title + "," + timerMins + "," + numQuestions + "," + answers + "\n";
        for(int i = 0; i < numQuestions; i++){
            addWholeString(i);
        }
        save(view);
        openMainActivity();
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
        intent.putExtra("title", title);
        startActivity(intent);
    }

}