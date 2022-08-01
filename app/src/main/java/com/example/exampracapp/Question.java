package com.example.exampracapp;

import java.util.ArrayList;

public class Question {
    public int number;
    public int correctLetterElement;
    public answerButton[] buttons;

    public Question(int number, int correctLetterElement, answerButton[] buttons){
        this.number = number;
        this.correctLetterElement = correctLetterElement;
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

    public int getCorrectLetter() {
        return correctLetterElement;
    }

    public void setCorrectLetter(int correctLetterElement) {
        this.correctLetterElement = correctLetterElement;
    }
}
