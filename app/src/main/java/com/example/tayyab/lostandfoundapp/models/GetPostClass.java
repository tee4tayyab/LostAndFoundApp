package com.example.tayyab.lostandfoundapp.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPostClass {

    @SerializedName("reg")
    @Expose
    private List<Post> reg = null;

    public List<Post> getReg() {
        return reg;
    }

    public void setReg(List<Post> reg) {
        this.reg = reg;
    }

    @Override
    public String toString() {
        return "GetPostClass{" +
                "reg=" + reg +
                '}';
    }
}