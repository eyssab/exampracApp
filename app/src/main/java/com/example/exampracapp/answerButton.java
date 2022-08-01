package com.example.exampracapp;

import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

public class answerButton {
    boolean selected;
    String letter;
    ToggleButton button;

    public answerButton(boolean selected, String letter, ToggleButton button) {
        this.selected = selected;
        this.letter = letter;
        this.button = button;
    }

    public ToggleButton getButton() {
        return button;
    }

    public void onClick(View v) {
        System.out.println(button.getText() + " " + button.getId() + "activated?: " + button.isChecked());
        selected = true;
    }

    public void setButton(ToggleButton button) {
        this.button = button;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }
}
