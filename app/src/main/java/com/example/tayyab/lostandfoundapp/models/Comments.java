package com.example.tayyab.lostandfoundapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comments {

    @SerializedName("CommentID")
    @Expose
    private Integer commentID;
    @SerializedName("Comment")
    @Expose
    private String comment;
    @SerializedName("PostId")
    @Expose
    private Integer postId;
    @SerializedName("PosPicture")
    @Expose
    private String posPicture;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("CategoryID")
    @Expose
    private Integer categoryID;
    @SerializedName("PostTypeID")
    @Expose
    private Integer postTypeID;
    @SerializedName("RegisterId")
    @Expose
    private Integer registerId;
    @SerializedName("Username")
    @Expose
    private String username;
    @SerializedName("UserID")
    @Expose
    private Integer userID;

    public Comments(Integer commentID, String comment, Integer postId) {
        this.commentID = commentID;
        this.comment = comment;
        this.postId = postId;
    }

    public Integer getCommentID() {
        return commentID;
    }

    public void setCommentID(Integer commentID) {
        this.commentID = commentID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
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

    public Integer getPostTypeID() {
        return postTypeID;
    }

    public void setPostTypeID(Integer postTypeID) {
        this.postTypeID = postTypeID;
    }

    public Integer getRegisterId() {
        return registerId;
    }

    public void setRegisterId(Integer registerId) {
        this.registerId = registerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

}