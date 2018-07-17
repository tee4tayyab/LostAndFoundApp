package com.example.tayyab.lostandfoundapp;

public class CustomCommentitem {
    private Integer id;
    private String Username;
    private String Comments;

    public CustomCommentitem(Integer id, String username, String comments) {
        this.id = id;
        Username = username;
        Comments = comments;
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

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    @Override
    public String toString() {
        return "CustomCommentitem{" +
                "id=" + id +
                ", Username='" + Username + '\'' +
                ", Comments='" + Comments + '\'' +
                '}';
    }
}
