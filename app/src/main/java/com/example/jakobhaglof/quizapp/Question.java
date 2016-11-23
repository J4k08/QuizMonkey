package com.example.jakobhaglof.quizapp;

/**
 * Created by Curject on 17/11/16.
 */

public class Question {

    private String category;
    private String question;
    private int correctAnswerId;
    private String choice1;
    private String choice2;
    private String choice3;
    private String choice4;

    public Question() {
        category = "";
        question = "";
        correctAnswerId = 0;
        choice1 = "";
        choice2 = "";
        choice3 = "";
        choice4 = "";
    }

    public Question(String question, String category, int correctAnswerId, String choice1, String choice2, String choice3, String choice4) {

        this.question = question;
        this.category = category;
        this.correctAnswerId = 0;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.choice4 = choice4;

    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }

    public int getCorrectAnswerId() {
        return correctAnswerId;
    }
    public void setCorrectAnswerId(int correctAnswerId) {
        this.correctAnswerId = correctAnswerId;
    }

    public String getChoice1() {
        return choice1;
    }
    public void setChoice1(String choice1) {
        this.choice1 = choice1;
    }

    public String getChoice2() {
        return choice2;
    }
    public void setChoice2(String choice2) {
        this.choice2= choice2;
    }

    public String getChoice3() {
        return choice3;
    }
    public void setChoice3(String choice3) {
        this.choice3= choice3;
    }

    public String getChoice4() {
        return choice4;
    }
    public void setChoice4(String choice4) {
        this.choice4 = choice4;
    }

    public Question addQuestion(Question question) {

        return question;
        //Överflödig metod?
    }

    public void removeQuestion(){
        //ctrl+alt+del
    }

    public void shuffleQuestion(){
        //Blanda ordningen som svars alternativen kommer fram i spelet
    }
}