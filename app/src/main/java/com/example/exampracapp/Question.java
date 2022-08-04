package com.example.exampracapp;

public class Question {
    public int number;
    public answerButton[] buttons;

    public Question(int number, int correctLetterElement, answerButton[] buttons){
        this.number = number;
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
}
