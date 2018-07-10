package com.example.tayyab.lostandfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tayyab.lostandfoundapp.InterfaceService.UserClient;
import com.example.tayyab.lostandfoundapp.models.Dummy;
import com.example.tayyab.lostandfoundapp.models.Post;
import com.example.tayyab.lostandfoundapp.models.SampleModel;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private Button logout;
    private FirebaseAuth mAuth;

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Customfeeditem> SampleListData;

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



        /*recyclerView = findViewById(R.id.recyclerLostFound);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);*/

        SampleListData = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            SampleListData.add(new Customfeeditem(i, "username " + i, "description http://badboyroy.com/wp-content/uploads/2014/09/image-placeholder-940x470-940x470.jpg" + i, true, "http://badboyroy.com/wp-content/uploads/2014/09/image-placeholder-940x470-940x470.jpg", "http://badboyroy.com/wp-content/uploads/2014/09/image-placeholder-940x470-940x470.jpg"));
        }

        RecyclerView recyclerLostFound = findViewById(R.id.recyclerLostFound);

        recyclerLostFound.setAdapter(new CustomfeedAdapter(SampleListData));



        UserClient CustomerService = ServiceGenerator.createService(UserClient.class);
//        Customer c = new Customer(7, "tttttttttttttttttttt", "tttt");

        Dummy dummy = new Dummy(1, 1, "wwwwwwwwwwwwwwww", "assssssssssssssssss");

        Call<Dummy> dummycall = CustomerService.updateDummy(1, dummy);

        dummycall.enqueue(new Callback<Dummy>() {
            @Override
            public void onResponse(Call<Dummy> call, Response<Dummy> response) {
                Log.d("MTAG", "onResponse: ");
                String s = ";";
            }

            @Override
            public void onFailure(Call<Dummy> call, Throwable t) {
                Log.d("MTAG", "onFailure: ");
            }
        });






        // recyclerLostFound.setLayoutManager(RecyclerView.LayoutManager);








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



