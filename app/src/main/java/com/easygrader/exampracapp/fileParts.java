package com.easygrader.exampracapp;

public class fileParts {
    String fileTitle;
    Double curScore;
    int curTimee;

    public fileParts(String fileTitle, Double curScore, int curTimee) {
        this.fileTitle = fileTitle;
        this.curScore = curScore;
        this.curTimee = curTimee;
    }

    public Double getCurScore() {
        return curScore;
    }

    public void setCurScore(Double curScore) {
        this.curScore = curScore;
    }

    public int getCurTimee() {
        return curTimee;
    }

    public void setCurTimee(int curTimee) {
        this.curTimee = curTimee;
    }

    public String getFileTitle() {
        return fileTitle;
    }

    public void setFileTitle(String fileTitle) {
        this.fileTitle = fileTitle;
    }
}
