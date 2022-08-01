package com.example.exampracapp;

import java.util.ArrayList;

public class Question {
    public int number;
    public String correctLetter;
    public answerButton[] buttons;

    public Question(int number, String correctLetter, answerButton[] buttons){
        this.number = number;
        this.correctLetter = correctLetter;
        this.buttons = buttons;
    }

    public answerButton[] getButtons() {
        return buttons;
    }

    public void setButtons(answerButton[] buttons) {
        this.buttons = buttons;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCorrectLetter() {
        return correctLetter;
    }

    public void setCorrectLetter(String correctLetter) {
        this.correctLetter = correctLetter;
    }
}
