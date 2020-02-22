package com.example.iscream2_android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Answer implements Serializable {

    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("value")
    @Expose
    private String value;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}