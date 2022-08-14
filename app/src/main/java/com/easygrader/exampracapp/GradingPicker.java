package com.easygrader.exampracapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

public class GradingPicker extends AppCompatActivity {

    Class class2Open;

    ToggleButton manualBtn;
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grading_picker);
        //autoBtn = findViewById(R.id.autoGradeSelection);
        manualBtn  = findViewById(R.id.manualGradeSelection);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            fileName = bundle.getString("fileName");
        }
    }


    public void onManualBtnDown(View view) {
        //SET TO ManualGradeActivity
        class2Open = TakeExamActivity.class;
    }

    public void onNextBtnDown(View view) {
        Intent intent = new Intent(this, class2Open);
        intent.putExtra("fileName", fileName);
        intent.putExtra("isGrading", true);
        startActivity(intent);
    }
}