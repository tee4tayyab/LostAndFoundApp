package com.example.tayyab.lostandfoundapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommentAdd {

    @SerializedName("Comment1")
    @Expose
    private String comment1;
    @SerializedName("PostId")
    @Expose
    private Integer postId;
    @SerializedName("Username")
    @Expose
    private String username;


    public CommentAdd(String comment1, Integer postId, String username) {
        this.comment1 = comment1;
        this.postId = postId;
        this.username = username;
    }

    public String getComment1() {
        return comment1;
    }

    public void setComment1(String comment1) {
        this.comment1 = comment1;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}