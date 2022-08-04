package com.example.exampracapp;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

public class answerButton {
    int letterNumber;
    ToggleButton button;
    boolean correct;
    boolean isGrading;

    public answerButton(int letterNumber, ToggleButton button, boolean correct, boolean isGrading) {
        this.letterNumber = letterNumber;
        this.button = button;
        this.correct = correct;
        this.isGrading = isGrading;
    }

    public ToggleButton getButton() {
        return button;
    }

    public void onClick(View v) {
        if(!button.isChecked()) {
            button.setChecked(true);
            if(isGrading){
                correct = true;
            }
        }else{
            button.setChecked(false);
            if(isGrading){
                correct = false;
            }
        }
    }

    public boolean isGrading() {
        return isGrading;
    }

    public void setGrading(boolean grading) {
        isGrading = grading;
    }

    public void setButton(ToggleButton button) {
        this.button = button;
    }

    public int getLetterNumber() {
        return letterNumber;
    }

    public void setLetterNumber(int letterNumber) {
        this.letterNumber = letterNumber;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    @Override
    public String toString() {
        //letterNumber can be converted to letter when reading file
        return button.isChecked() + ",";
    }

    public String correctToString(){
        return isCorrect() + ",";
    }
}
