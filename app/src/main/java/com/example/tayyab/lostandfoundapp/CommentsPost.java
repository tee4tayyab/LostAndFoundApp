package com.example.tayyab.lostandfoundapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tayyab.lostandfoundapp.InterfaceService.PostClient;
import com.example.tayyab.lostandfoundapp.models.CommentAdd;
import com.example.tayyab.lostandfoundapp.models.Comments;
import com.example.tayyab.lostandfoundapp.models.Post;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.tayyab.lostandfoundapp.MyProfile.PREF_USERCOMMENT;

public class CommentsPost extends AppCompatActivity {
    private static final String TAG = "MTAG";
    ArrayList<CustomCommentitem> sampleList;
    RecyclerView.LayoutManager manager;
    String namepost, des, img, img1, status, postid;
    List<Reg> regs = new ArrayList<>();
    SlimAdapter slimAdapter;
    TextView textView, textView1, textView2;
    EditText Comment;
    ImageView imageView;
    Button sendComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_post);
        Intent i = getIntent();
        imageView = findViewById(R.id.imgviewPostPicture);
        Comment = findViewById(R.id.commenttext);
        SharedPreferences sharedPreferences = getSharedPreferences("",MODE_PRIVATE);
        final String username = sharedPreferences.getString(PREF_USERCOMMENT,"");
        Log.e("TGED",sharedPreferences.getString(PREF_USERCOMMENT,""));
        sendComment = findViewById(R.id.send);
        sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentAdd commentAdd = new CommentAdd(Comment.getText().toString(),Integer.valueOf(postid),username);
                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl("http://192.168.10.11/LostFoundApi/api/")
                        .addConverterFactory(GsonConverterFactory.create());

                Retrofit retrofit = builder.build();
                PostClient client = retrofit.create(PostClient.class);
                Call<CommentAdd> commentAddCall = client.addcomments(commentAdd);
                commentAddCall.enqueue(new Callback<CommentAdd>() {
                    @Override
                    public void onResponse(Call<CommentAdd> call, Response<CommentAdd> response) {
                        Log.d(TAG, "onResponse: "+response);
                    }

                    @Override
                    public void onFailure(Call<CommentAdd> call, Throwable t) {

                        Log.d(TAG, "onFailure: "+ "Failed Comment Post");

                    }
                });
            }
        });




        Bitmap bmp = null;
        String filename = getIntent().getStringExtra("imagepost");
        try {
            FileInputStream is = this.openFileInput(filename);
            bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(bmp);
        namepost = i.getStringExtra("name");
        postid = i.getStringExtra("postid");
        des = i.getStringExtra("description");
        status = i.getStringExtra("status");
        textView = findViewById(R.id.txt_ProfileName);
        textView1 = findViewById(R.id.txtLostFoundStatus);
        textView2 = findViewById(R.id.txtDescription);
        textView.setText("" + namepost);
        if (status.equalsIgnoreCase("1")) {
            textView1.setText("LOST");
        } else if (status.equalsIgnoreCase("2")) {
            textView1.setText("FOUND");
        }
        textView2.setText("" + des);

        RecyclerView recyclerLostFound = findViewById(R.id.my_recycler_view);
        manager = new LinearLayoutManager(this);
        recyclerLostFound.setAdapter(new CustomCommentAdapter(sampleList));

        slimAdapter = SlimAdapter.create()
                .register(R.layout.custom_comment_layout, new SlimInjector<Reg>() {
                    @Override
                    public void onInject(final Reg data, IViewInjector injector) {
                        injector.text(R.id.txtUsername, data.getUsername() + "")
                                .text(R.id.edtComment, data.getComment() + "");


                    }
                })

                .attachTo(recyclerLostFound);

        slimAdapter.updateData(regs);
        Log.e("TGEd", "POST ID " + postid);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.10.11/LostFoundApi/api/Comments/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        PostClient client = retrofit.create(PostClient.class);
        Call<CommentsByPost> call = client.getcomments(postid);
        call.enqueue(new Callback<CommentsByPost>() {
            @Override
            public void onResponse(Call<CommentsByPost> call, Response<CommentsByPost> response) {
                Log.d("MTAG", "onResponse:dsklkdjdhsdhlsdh " + response.body());
                regs = response.body().getReg();
                slimAdapter.updateData(regs);
            }

            @Override
            public void onFailure(Call<CommentsByPost> call, Throwable t) {
                Log.d("MTAG", "onFailure: ");
            }
        });

    }

    public void CommentAddRequest(CommentAdd commentAdd){


    }




    public class CommentsByPost {

        @SerializedName("reg")
        @Expose
        private List<Reg> reg = new ArrayList<>();

        public List<Reg> getReg() {
            return reg;
        }

        public void setReg(List<Reg> reg) {
            this.reg = reg;
        }

    }

    public class Reg {

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
        private Object posPicture;
        @SerializedName("Description")
        @Expose
        private Object description;
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

        public Object getPosPicture() {
            return posPicture;
        }

        public void setPosPicture(Object posPicture) {
            this.posPicture = posPicture;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
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
}