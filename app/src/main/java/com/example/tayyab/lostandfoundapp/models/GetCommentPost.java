package com.example.tayyab.lostandfoundapp.models;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCommentPost {
    @SerializedName("reg")
    @Expose
    private List<Comments> reg = null;

    public List<Comments> getReg() {
        return reg;
    }

    public void setReg(List<Comments> reg) {
        this.reg = reg;
    }

}
