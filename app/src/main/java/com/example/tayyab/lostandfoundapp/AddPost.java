package com.example.tayyab.lostandfoundapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.tayyab.lostandfoundapp.Event.Event;
import com.example.tayyab.lostandfoundapp.Event.PostImageEvent;
import com.example.tayyab.lostandfoundapp.Event.RegisterEvent;
import com.example.tayyab.lostandfoundapp.InterfaceService.PostClient;
import com.example.tayyab.lostandfoundapp.InterfaceService.UserClient;
import com.example.tayyab.lostandfoundapp.models.Post;
import com.example.tayyab.lostandfoundapp.models.User;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.tayyab.lostandfoundapp.LoginActivity.PREF_ID;

public class AddPost extends AppCompatActivity {

    private static final String TAG = "MTAG";
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private static final int Pick_Image = 100;
    Uri imageUri;
    Button addPost;
    EditText description;
    ImageButton btnImage;
    private String PostImageText;
    RadioButton losttype, foundtype;
    String registerID;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PostImageEvent event) {

        PostImageText = event.getDecodedText();

    }

    /*    @Subscribe(threadMode = ThreadMode.MAIN)
        public void onRegisterEvent(RegisterEvent event){
            this.registerID = event.getRegisterID();
        }*/
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        losttype = findViewById(R.id.rbLost);
        foundtype = findViewById(R.id.rbFound);
        description = findViewById(R.id.etdescription);


        final Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected: ");


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "onNothingSelected: ");
            }
        });
        description = findViewById(R.id.etdescription);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        registerID = preferences.getString(PREF_ID, "");

        addPost = findViewById(R.id.btnPost);
        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int posttypeid;
                if (losttype.isChecked()) {
                    posttypeid = 1;
                } else if (foundtype.isChecked()) {
                    posttypeid = 2;
                } else {
                    posttypeid = 0;
                }
                Post post = new Post(posttypeid, PostImageText, description.getText().toString(), spinner.getSelectedItemPosition(), Integer.valueOf(registerID));
                sendNetworkRequest(post);
            }
        });


        btnImage = findViewById(R.id.btnInsertImage);
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });


//        ProfileUpdate("email.com", "againchanged");


    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, Pick_Image);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Pick_Image) {

            imageUri = data.getData();
            /*imageButton.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageButton.setImageResource(R.drawable.circle);*/
            // imageButton.setImageURI(imageUri);

//circleImageView.setImageResource(R.drawable.circle);
            //  circleImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                new PostImageAsynctask().execute(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            btnImage.setImageBitmap(bitmap);

        }
    }

    private void sendNetworkRequest(Post post) {


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.1.19/LostFoundApi/api/")
                .addConverterFactory(GsonConverterFactory.create());
        Log.d("MTAG", "sendNetworkRequest: Successful");

        Retrofit retrofit = builder.build();

        PostClient client = retrofit.create(PostClient.class);

        //????????????????????????????????????????????????????
        Call<Post> call = client.AddPost(post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Log.d(TAG, "onResponse: ");
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });

    }


    public class PostImageAsynctask extends AsyncTask<Bitmap, Void, String> {

        String text;
        Bitmap bitmap;

        @Override
        protected String doInBackground(Bitmap... bitmaps) {
            this.bitmap = bitmaps[0];

            ByteArrayOutputStream baos = new ByteArrayOutputStream();


            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            onPostExecute(imageString);
            return imageString;
        }

        @Override
        protected void onPostExecute(String s) {
            EventBus.getDefault().post(new PostImageEvent(s));

        }
    }


}
