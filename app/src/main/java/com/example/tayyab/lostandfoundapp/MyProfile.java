package com.example.tayyab.lostandfoundapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tayyab.lostandfoundapp.Event.Event;
import com.example.tayyab.lostandfoundapp.InterfaceService.UserClient;
import com.example.tayyab.lostandfoundapp.models.User;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyProfile extends AppCompatActivity {
    private static final int Pick_Image = 100;
    private static final String TAG = "MTAG";
    Uri imageUri;
    ImageButton imageButton;
    CircleImageView circleImageView;
    Button Edit, Save;
    EditText FName,EMail;
    TextView UName;
    String profileimage;

    private String Emailevent,EncodedText;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Event event){

        this.EncodedText = event.getDecodedText();

    }
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
    public void getProfileImage(String picture){
        byte[] decodeimageBytes = Base64.decode(picture, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(decodeimageBytes, 0, decodeimageBytes.length);
        circleImageView.setImageBitmap(decodedImage);
    }

    public static final String MY_PREFS_NAME = "MyPrefsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        FName = findViewById(R.id.profile_username);
        UName = findViewById(R.id.profile_fullname);

        User user = new User(1028,"tayyadkjsdhb123","Tayyab","","tayyabrasheed330@gmail.com",1);

        ProfileUpdate(1028,user);


        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        Emailevent = sharedPreferences.getString("Email", null);
        Log.d(TAG, "onCreate: "+Emailevent);
        sendNetworkRequest(Emailevent);

        EditText editUsername = findViewById(R.id.profile_username);
        editUsername.setEnabled(false);
        EditText editEmail = findViewById(R.id.profile_email);
        editEmail.setEnabled(false);
        EditText editPhone = findViewById(R.id.profile_phone);
        editPhone.setEnabled(false);
        EditText editLocation = findViewById(R.id.profile_location);
        editLocation.setEnabled(false);

        Edit = findViewById(R.id.btnEdit);
        Save = findViewById(R.id.btnSave);
        circleImageView = findViewById(R.id.profile_image);
       /*
        Picasso.get().setIndicatorsEnabled(true);
        Picasso.get().load("@drawable/profile.png").placeholder(R.drawable.placeholder).error(R.drawable.error).into(circleImageView);
*/
        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editUsername = findViewById(R.id.profile_username);
                editUsername.setEnabled(true);
                EditText editPhone = findViewById(R.id.profile_phone);
                editPhone.setEnabled(true);
                EditText editLocation = findViewById(R.id.profile_location);
                editLocation.setEnabled(true);
                Edit.setVisibility(View.GONE);
                Save.setVisibility(View.VISIBLE);
            }
        });
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editUsername = findViewById(R.id.profile_username);
                editUsername.setEnabled(false);
                EditText editEmail = findViewById(R.id.profile_email);
                editEmail.setEnabled(false);
                EditText editPhone = findViewById(R.id.profile_phone);
                editPhone.setEnabled(false);
                EditText editLocation = findViewById(R.id.profile_location);
                editLocation.setEnabled(false);
                /*User user = new User("nullnullnull","huvyffgyf","","abcdef",1);

                ProfileUpdate("abcdef",user);*/
                Edit.setVisibility(View.VISIBLE);
                Save.setVisibility(View.GONE);





            }
        });
        imageButton = findViewById(R.id.chooseimgbtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
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
            circleImageView.setImageURI(imageUri);
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                new ProfileAsynctask().execute(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
    }

    // GET DATA

    private void sendNetworkRequest(String email) {


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.15.190/LostFoundApi/api/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        Log.d(TAG, "sendNetworkRequest: "+email);
        UserClient client = retrofit.create(UserClient.class);
       Call<List<User>> call = client.GetUserDetails(email);
       call.enqueue(new Callback<List<User>>() {
           @Override
           public void onResponse(Call<List<User>> call, Response<List<User>> response) {
               User u = response.body().get(0);
               String username = u.getUsername();
               String picture = u.getPicture();
               setUserProfile(username,picture,u.getEmail());
               Log.d(TAG, "onResponse: ");
           }

           @Override
           public void onFailure(Call<List<User>> call, Throwable t) {
               Log.d(TAG, "onFailure: ");
           }
       });

    }


    private void ProfileUpdate(Integer id,User user) {


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.1.7/LostFoundApi/api/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        Log.d(TAG, "sendNetworkRequest: ");
        UserClient client = retrofit.create(UserClient.class);

        /*String userjson = "{\n" +
                "        \n" +
                "        \"Password\": \"test\",\n" +
                "        \"Username\": \"testingjson\",\n" +
                "        \"Picture\": \"\",\n" +
                "     \n" +
                "        \"UserID\": 1\n" +
                "    }";

        userjson = "updated";*/


        Call<User> call = client.PutUserDetails(id,user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d(TAG, "onResponse: bjkgkgu ");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "onFailure: hjgkhhiyg");
            }
        });

    }


    private void setUserProfile(String username,String picture,String Email) {

    FName.setText(username);
    UName.setText(username);
    EMail = findViewById(R.id.profile_email);
    EMail.setText(Email);
    getProfileImage("");

    }


    //AsyncTask For Profile Picture

    public class ProfileAsynctask extends AsyncTask<Bitmap,Void,String> {

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

            EventBus.getDefault().post(new Event(s));

        }
    }




}
