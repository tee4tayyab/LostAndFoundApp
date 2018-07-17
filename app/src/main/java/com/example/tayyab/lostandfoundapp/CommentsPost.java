package com.example.tayyab.lostandfoundapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.tayyab.lostandfoundapp.models.Comments;

import java.util.ArrayList;

public class CommentsPost extends AppCompatActivity {
    ArrayList<CustomCommentitem> sampleList;
    RecyclerView.LayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_post);


        sampleList = new ArrayList<>();

        sampleList.add(new CustomCommentitem(1,"Bill","Hey Micheal, I have seen this Bag in courtyard !"));
        sampleList.add(new CustomCommentitem(2,"Micheal Goerge","Thank you for Helping me !"));
        RecyclerView recyclerLostFound = findViewById(R.id.my_recycler_view);
        manager = new LinearLayoutManager(this);
        recyclerLostFound.setAdapter(new CustomCommentAdapter(sampleList));
    }
}
