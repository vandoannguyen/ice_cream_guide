package com.example.iscream2_android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {

    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("answer")
    @Expose
    private List<Answer> answer = null;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Answer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Answer> answer) {
        this.answer = answer;
    }

}