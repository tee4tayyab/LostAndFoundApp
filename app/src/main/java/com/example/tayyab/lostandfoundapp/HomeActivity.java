package com.example.tayyab.lostandfoundapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tayyab.lostandfoundapp.InterfaceService.PostClient;
import com.example.tayyab.lostandfoundapp.InterfaceService.UserClient;
import com.example.tayyab.lostandfoundapp.models.Dummy;
import com.example.tayyab.lostandfoundapp.models.GetPostClass;
import com.example.tayyab.lostandfoundapp.models.Post;
import com.example.tayyab.lostandfoundapp.models.SampleModel;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {

    private Button logout;
    private FirebaseAuth mAuth;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Customfeeditem> SampleListData;
ImageView Send;
    List<Post> postList;
    SlimAdapter slimAdapter;

    RecyclerView recyclerLostFound;

    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        search = findViewById(R.id.search_bar);
        Send = findViewById(R.id.send);
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl("http://192.168.10.11/LostFoundApi/api/")
                        .addConverterFactory(GsonConverterFactory.create());

                Retrofit retrofit = builder.build();
                Log.d("MTAG", "sendNetworkRequest: ");
                PostClient client = retrofit.create(PostClient.class);
                Call<GetPostClass> call = client.getcategory(search.getText().toString());
                call.enqueue(new Callback<GetPostClass>() {
                    @Override
                    public void onResponse(@NonNull Call<GetPostClass> call, @NonNull Response<GetPostClass> response) {
//                        Toast.makeText(HomeActivity.this, response.body().getReg().get(0).getUsername().toString(), Toast.LENGTH_LONG).show();
                        postList = response.body().getReg();
                        slimAdapter.updateData(postList);
//                        Log.d("MTAG", "onResponse: " + response.body().getReg().get(0));
//                        int i = 0;
                    }

                    @Override
                    public void onFailure(Call<GetPostClass> call, Throwable t) {
                        Toast.makeText(HomeActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d("MTAG", "onFailure: ");
                    }
                });
            }
        });
        logout = findViewById(R.id.btnLogout);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                        hometoHome();
                        break;
                    case R.id.navigation_addPost:
                        Toast.makeText(getApplicationContext(), "Add Post", Toast.LENGTH_SHORT).show();
                        hometoAddPost();
                        break;
                    case R.id.navigation_profile:
                        Toast.makeText(getApplicationContext(), "My Profile", Toast.LENGTH_SHORT).show();
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


        sendNetworkRequest();

        recyclerLostFound = findViewById(R.id.recyclerLostFound);
        recyclerLostFound.setHasFixedSize(true);
        recyclerLostFound.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerLostFound.setAdapter(new CustomfeedAdapter(SampleListData));

        slimAdapter = SlimAdapter.create()
                .register(R.layout.sample_feed_item_layout, new SlimInjector<Post>() {
                    @Override
                    public void onInject(final Post data, IViewInjector injector) {
                        injector.text(R.id.txt_ProfileName, data.getUsername() + "")
                                .text(R.id.txtDescription, data.getDescription() + "");
                        byte[] decodedString = new byte[0];
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.FROYO) {
                            decodedString = Base64.decode(data.getPosPicture(), Base64.DEFAULT);
                        }

                        final Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        injector.with(R.id.imgviewPostPicture, new IViewInjector.Action() {
                            @Override
                            public void action(View view) {
                                ImageView i = view.findViewById(R.id.imgviewPostPicture);
                                i.setImageBitmap(decodedByte);
                            }
                        });


/*                        byte[] decodedString1 = new byte[0];
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.FROYO) {
                            decodedString = Base64.decode(data.getPicture(), Base64.DEFAULT);
                        }

                        final Bitmap decodedByte1 = BitmapFactory.decodeByteArray(decodedString1, 0, decodedString1.length);
                        injector.with(R.id.imgSampleProfileImage, new IViewInjector.Action() {
                            @Override
                            public void action(View view) {
                                ImageView i = view.findViewById(R.id.imgviewPostPicture);
                                i.setImageBitmap(decodedByte1);
                            }
                        });*/


                        if (String.valueOf(data.getPostTypeID()).equalsIgnoreCase("1")) {
                            injector.text(R.id.txtLostFoundStatus, "Lost");

                        } else if (String.valueOf(data.getPostTypeID()).equalsIgnoreCase("2")) {
                            injector.text(R.id.txtLostFoundStatus, "Found");

                        }


                        final String filename = "bitmap.png";

                        try {
                            //Write file
                            FileOutputStream stream = HomeActivity.this.openFileOutput(filename, Context.MODE_PRIVATE);
                            decodedByte.compress(Bitmap.CompressFormat.PNG, 100, stream);

                            //Cleanup
                            stream.close();
//                            decodedByte.recycle();

                            //Pop intent

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

/*
                        final String filename1 = "bitmap.png";

                        try {
                            //Write file
                            FileOutputStream stream = HomeActivity.this.openFileOutput(filename1, Context.MODE_PRIVATE);
                            decodedByte.compress(Bitmap.CompressFormat.PNG, 100, stream);

                            //Cleanup
                            stream.close();
//                            decodedByte.recycle();

                            //Pop intent

                        } catch (Exception e) {
                            e.printStackTrace();
                        }*/


                        injector.clicked(R.id.card, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(getApplicationContext(), CommentsPost.class);
                                i.putExtra("name", data.getUsername() + "");
                                i.putExtra("description", data.getDescription() + "");
                                i.putExtra("status", data.getPostTypeID() + "");
                                i.putExtra("imagepost", filename + "");
                                i.putExtra("imageuser", data.getPicture() + "");
                                i.putExtra("postid", data.getPostID() + "");
                                startActivity(i);
                            }
                        });
                    }
                })

                .attachTo(recyclerLostFound);

        slimAdapter.updateData(postList);


    }


    private void sendNetworkRequest() {


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.10.11/LostFoundApi/api/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        Log.d("MTAG", "sendNetworkRequest: ");
        PostClient client = retrofit.create(PostClient.class);
        Call<GetPostClass> call = client.GetAllPosts();
        call.enqueue(new Callback<GetPostClass>() {
            @Override
            public void onResponse(@NonNull Call<GetPostClass> call, @NonNull Response<GetPostClass> response) {
                Toast.makeText(HomeActivity.this, response.body().getReg().get(0).getUsername().toString(), Toast.LENGTH_LONG).show();
                postList = response.body().getReg();
                slimAdapter.updateData(postList);
                Log.d("MTAG", "onResponse: " + response.body().getReg().get(0));
                int i = 0;
            }

            @Override
            public void onFailure(Call<GetPostClass> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                Log.d("MTAG", "onFailure: ");
            }
        });
    }


    private void UpdateNetworkRequest(Integer id, Post post) {


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.10.11/LostFoundApi/api/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        Log.d("MTAG", "UpdateNetworkRequest: ");
        PostClient client = retrofit.create(PostClient.class);
        Call<Post> call = client.UpdatePost(id, post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                Log.d("MTAG", "onResponse:dsklkdjdhsdhlsdh " + response.body());
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.d("MTAG", "onFailure: ");
            }
        });
    }


    private void hometoHome() {
        startActivity(new Intent(HomeActivity.this, HomeActivity.class));
    }

    private void hometoAddPost() {
        startActivity(new Intent(HomeActivity.this, AddPost.class));
    }

    private void hometoMyProfile() {
        startActivity(new Intent(HomeActivity.this, MyProfile.class));
    }

}



