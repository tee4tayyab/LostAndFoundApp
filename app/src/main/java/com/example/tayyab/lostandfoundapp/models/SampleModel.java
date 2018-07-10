package com.example.tayyab.lostandfoundapp.models;

public class SampleModel {

    private Integer id;
    private String Username;
    private String Description;
    private Boolean StatusLostFound;
    private String UserImageUrl;
    private String PostImageUrl;

    public SampleModel(Integer id, String username, String description, Boolean statusLostFound, String userImageUrl, String postImageUrl) {
        this.id = id;
        Username = username;
        Description = description;
        StatusLostFound = statusLostFound;
        UserImageUrl = userImageUrl;
        PostImageUrl = postImageUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Boolean getStatusLostFound() {
        return StatusLostFound;
    }

    public void setStatusLostFound(Boolean statusLostFound) {
        StatusLostFound = statusLostFound;
    }

    public String getUserImageUrl() {
        return UserImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        UserImageUrl = userImageUrl;
    }

    public String getPostImageUrl() {
        return PostImageUrl;
    }

    public void setPostImageUrl(String postImageUrl) {
        PostImageUrl = postImageUrl;
    }
}
