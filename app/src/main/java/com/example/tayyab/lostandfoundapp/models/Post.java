package com.example.tayyab.lostandfoundapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("PostID")
    @Expose
    private Integer postID;
    @SerializedName("PostTypeID")
    @Expose
    private Integer postTypeID;
    @SerializedName("PosPicture")
    @Expose
    private String posPicture;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("CategoryID")
    @Expose
    private Integer categoryID;
    @SerializedName("RegisterId")
    @Expose
    private Integer registerId;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Username")
    @Expose
    private String username;
    @SerializedName("Picture")
    @Expose
    private String picture;
    @SerializedName("UserID")
    @Expose
    private Integer userID;

    public Post(Integer postID, Integer postTypeID, String posPicture, String description, Integer categoryID, Integer registerId, String email, String username, String picture, Integer userID) {
        this.postID = postID;
        this.postTypeID = postTypeID;
        this.posPicture = posPicture;
        this.description = description;
        this.categoryID = categoryID;
        this.registerId = registerId;
        this.email = email;
        this.username = username;
        this.picture = picture;
        this.userID = userID;
    }

    public Post(Integer postTypeID, String posPicture, String description, Integer categoryID, Integer registerId) {
        this.postTypeID = postTypeID;
        this.posPicture = posPicture;
        this.description = description;
        this.categoryID = categoryID;
        this.registerId = registerId;
    }

    public Integer getPostID() {
        return postID;
    }

    public void setPostID(Integer postID) {
        this.postID = postID;
    }

    public Integer getPostTypeID() {
        return postTypeID;
    }

    public void setPostTypeID(Integer postTypeID) {
        this.postTypeID = postTypeID;
    }

    public String getPosPicture() {
        return posPicture;
    }

    public void setPosPicture(String posPicture) {
        this.posPicture = posPicture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public Integer getRegisterId() {
        return registerId;
    }

    public void setRegisterId(Integer registerId) {
        this.registerId = registerId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postID=" + postID +
                ", postTypeID=" + postTypeID +
                ", posPicture='" + posPicture + '\'' +
                ", description='" + description + '\'' +
                ", categoryID=" + categoryID +
                ", registerId=" + registerId +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", picture='" + picture + '\'' +
                ", userID=" + userID +
                '}';
    }
}

