package com.example.tayyab.lostandfoundapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("RegisterID")
    @Expose
    private Integer registerID;

    public User(String password, String username, String picture, String email, Integer userID) {
        this.password = password;
        this.username = username;
        this.picture = picture;
        this.email = email;
        this.userID = userID;
    }

    public User(Integer registerID, String password, String username, String picture, String email, Integer userID) {
        this.registerID = registerID;
        this.password = password;
        this.username = username;
        this.picture = picture;
        this.email = email;
        this.userID = userID;
    }

    @SerializedName("Password")
    @Expose

    private String password;
    @SerializedName("Username")
    @Expose
    private String username;
    @SerializedName("Picture")
    @Expose
    private String picture;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("UserID")
    @Expose
    private Integer userID;

    public Integer getRegisterID() {
        return registerID;
    }

    public void setRegisterID(Integer registerID) {
        this.registerID = registerID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

}