package com.example.tayyab.lostandfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tayyab.lostandfoundapp.models.Post;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private Button logout;
    private FirebaseAuth mAuth;

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    List<Post> postList;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();

        logout = findViewById(R.id.btnLogout);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        Toast.makeText(getApplicationContext(),"Home",Toast.LENGTH_SHORT).show();
                        hometoHome();
                        break;
                    case R.id.navigation_addPost:
                        Toast.makeText(getApplicationContext(),"Add Post",Toast.LENGTH_SHORT).show();
                        hometoAddPost();
                        break;
                    case R.id.navigation_profile:
                        Toast.makeText(getApplicationContext(),"My Profile",Toast.LENGTH_SHORT).show();
                        hometoMyProfile();
                        break;
                }
                return true;
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent hometoLogin = new Intent(HomeActivity.this, LoginActivity.class);
                hometoLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(hometoLogin);
            }
        });



        recyclerView = findViewById(R.id.recycleViewContainer);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        mAdapter = new CustomfeedAdapter(this, postList);
        recyclerView.setAdapter(mAdapter);








    }

    private void hometoHome(){
        startActivity(new Intent(HomeActivity.this, HomeActivity.class));
    }
    private void hometoAddPost(){
        startActivity(new Intent(HomeActivity.this, AddPost.class));
    }
    private void hometoMyProfile(){
        startActivity(new Intent(HomeActivity.this, MyProfile.class));
    }

}



