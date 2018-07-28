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
    getProfileImage("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");

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
