package com.example.exampracapp;

import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

public class answerButton {
    int letterNumber;
    ToggleButton button;

    public answerButton(int letterNumber, ToggleButton button) {
        this.letterNumber = letterNumber;
        this.button = button;
    }

    public ToggleButton getButton() {
        return button;
    }

    public void onClick(View v) {
        if(!button.isChecked()) {
            button.setChecked(true);
            System.out.println(button.toString());
        }else{
            button.setChecked(false);
            System.out.println(button.toString());
        }
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

    @Override
    public String toString() {
        //letterNumber can be converted to letter when reading file
        return  "," + letterNumber +
                "," + button.isChecked();
    }
}
