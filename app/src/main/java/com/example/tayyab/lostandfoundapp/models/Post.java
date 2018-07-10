package com.example.tayyab.lostandfoundapp.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("PostID")
    @Expose
    private Integer postID;
    @SerializedName("Description")
    @Expose
    private Object description;
    @SerializedName("PosPicture")
    @Expose
    private Object posPicture;
    @SerializedName("CategoryID")
    @Expose
    private Object categoryID;
    @SerializedName("PostTypeID")
    @Expose
    private Object postTypeID;
    @SerializedName("RegisterId")
    @Expose
    private Integer registerId;

    public Post(Object description, Object posPicture, Object postTypeID) {
        this.description = description;
        this.posPicture = posPicture;
        this.postTypeID = postTypeID;
    }

    public Integer getPostID() {
        return postID;
    }

    public void setPostID(Integer postID) {
        this.postID = postID;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Object getPosPicture() {
        return posPicture;
    }

    public void setPosPicture(Object posPicture) {
        this.posPicture = posPicture;
    }

    public Object getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Object categoryID) {
        this.categoryID = categoryID;
    }

    public Object getPostTypeID() {
        return postTypeID;
    }

    public void setPostTypeID(Object postTypeID) {
        this.postTypeID = postTypeID;
    }

    public Integer getRegisterId() {
        return registerId;
    }

    public void setRegisterId(Integer registerId) {
        this.registerId = registerId;
    }

}