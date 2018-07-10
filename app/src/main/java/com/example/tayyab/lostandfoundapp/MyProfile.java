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
    EditText FName, UName, EMail;
    String profileimage;

    private String Emailevent,EncodedText;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Event event){

        EncodedText = event.getDecodedText().toString();

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
    public void getProfileImage(){
        byte[] decodeimageBytes = Base64.decode(profileimage, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(decodeimageBytes, 0, decodeimageBytes.length);
        circleImageView.setImageBitmap(decodedImage);
    }

    public static final String MY_PREFS_NAME = "MyPrefsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        User user = new User(1028,"tayyadkjsdhb123","Tayyab","","tayyabrasheed330@gmail.com",1);

        ProfileUpdate(1028,user);


        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        Emailevent = sharedPreferences.getString("Email", null);
        Log.d(TAG, "onCreate: "+Emailevent);
        //sendNetworkRequest("email@ymail.com");

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
        Picasso.get().setIndicatorsEnabled(true);
        Picasso.get().load("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxMQEhUSEhMVFRUVFxcVFxgVFxgVFRoVFRoYFhUXFxcYHSggGB0lHRUXITEhJSkrLi4uFx8zODMtNygtLisBCgoKDg0OGxAQGS0lHx4tLTI1KystLS0tNzctNS0tLS0tLS0tLTctLS0rNS0tLS8tLS0tLS0tLS0tLS0tNS0tNf/AABEIALEBHAMBIgACEQEDEQH/xAAbAAEAAgMBAQAAAAAAAAAAAAAAAQYCBQcEA//EAEoQAAEDAgQDBAUIBQgLAQAAAAEAAgMEEQUSITEGQVEHE2FxIjKBkaEUI0JSYrHB0QgVkrKzJCY1Q3JzguEzNEVjdKLC0tPw8Rb/xAAZAQEAAwEBAAAAAAAAAAAAAAAAAgMEAQX/xAAnEQEAAgEDBAEDBQAAAAAAAAAAAQIRAyExBBIyQSITUYEzQmFxkf/aAAwDAQACEQMRAD8A7eSgClEBERAUXUogBERAQlEQQNVKIgIiIIcUAUogIiICi91KIAREQEJRLIIGqlEQEREEEqQERAREQFje6yRACBEQEREBRcJdSAgIiICISoGqCUREBEUEoJvqigBSgIiICAqL3UgICIiAhKEqLXQSEREBEUE+9BJKIAiAiIgJdY7rJAREQECEqACglYkqSpCAEREBEXxmnDd/YAuWtFYzLkzEby+g1/BZLXSV7jsLfEr4uncfpFZbdZSON1E9RWOG3RabvXdT7ys21Txzv56qMdbX3DkdTHuG1JQBeKGu+sPaPyXta8EXGq001aX8ZXVvW3EpREViYsTrf4qXBSgIiICEoosggdVkURAREKCCfegGiAKUBERAWJ1UlSEEclKIgKLqVACABqpCIgIiICIvLVz5RYbn7uqhe8Ur3Sja0VjMsaurto3fmV4CVBK5fxFxXUYlMaHDL5NQ+ZpIzDmQ8epHuL7u5ePlWtbWtmWG1rak5laOI+PaOiJYXGWQbsis4g/bdezfLfwVXHG+K1f+p0OVnJzmueP23ZWKwcL9ntLRgOkaJ5t8zwCxp/3bDoPM3Kt+yjmkcRlHNY4cz7ziI65WDwtD+awfxZjNJrU0QkbzLWO/ejLgPaF1BV7izjCDDg0SZnyO2jZbNb6xufRH3rsWzOO2HYtn013D/aRSVRDJCaeQ6WkIyE9BINPfZXaGYt1B/IqkVPD1DjVO2pYwxukBIka1rZA5pLS2QC4fYtI1v4FVrDcYq8CmbTVl5aVx9B4u6w6xk8hzjO3Lx7Eb5ptMGN/jy7hBMHi49oX0WioK0ODZI3BzHAODgbtc06ggrdscCLjYr0On1vqRieYa9LU7435ZIipHEPaTDS1b6KOlqqqWNodIKeMPDcwBAOtzo5t9LajW+2hcu6Kh4T2nwzVMVLNSVdK+YkRmojDGucOQ1vrte25CvbUEoiICIocUEogRAREQFKxvqpQFKhEBSsSdFIQEREBAii4QShKXWI1/BA8eS1M8mZxP/tlsqt1mnyt71qbrz+tvvFWTqbbxVz/tUx97Qygp7maotmy+tkJs1g6F5uPIHqrHwdw0zDoBG2xkdYyvtq53QfZF7AfmqTwEz9YYpU1zxdsR+bvsC/MyP3RtP7S6mSs1/jHbCi22wSgCAKscccXx4dHYWfO8fNs6DbO/o0H3+8iuImZxCMRk434vjw6Ows+d4+bZ0+2/o0fFchw+hkxCSSpqZS2JpvNMdTc+rHGObzsGjQD4zhtBJiEklTUyFsTTmnmOpvyjjHN52DRoPv8AViuJd7ljjb3cEVxFEDe193OP0nnckrRtSMRyt8V24H4uZ3raTu2xQH0YAN2nfK930i43N+p8Vc8eweKthdBKLtdsfpNcPVc3xC4Ox5aQ5psQQQehGoPvXf8ACqsTwxyj+sY1/wC0ASqbbTlXO27nnZ1iUtFVSYVUnYuMRO2bezfsub6Q6G/Vdbw6TdvtH4rk3a/RGJ1NXx6Pje1hI+zeSInyLXD/ABLpGC1omZFM31ZGsePJ4B/FXUti9bx7W1ti0Wb9cm4WP86MS/uW/uwLrJK5Lwy8N4oxEuIA7lu5t9GBeq3Nzx1wzU1eJ4bUwsDoqd4dKS5osM7XaAm50B2W07RuMXYVFEY4hLLPKImNLsrb8yT7h7VaPlLPrt/aC5d28f7O/wCLH/Sg++P8cYxQQmepw6nZGC1pd8ozWLjYaN13KtMuO1MOGS1s8UQlZE+YRxvL2ZWjMwF/O4106qv9uszThEoDmn5yLYg/TC9uNH+br+v6uH8EINW/tVIw6nqW04fVVPfFkDHGwZA97ZJHOtcNDWX8/I2mftYb8ggqYacyVNR3lqfN6ohzd9IXAeoA24019hto+zLh2KPA5643fNNT1LAXa93Ewyt7tnQFwLj1JXz7KeH4WYLVVts08sNVGHHXIxrXjIzpci56+xB0vgnicYnRMqwzITmDmXvZ7CQ6x5jS481S+HO0HFcRbI+lw+B7Y3mNxM+T0gAdneBC9fYU8fqcC4vnmNr679Fr/wBH2VraWrzOA/lTtyB9BiCy8A8bSV81VS1EDYJ6UgODH52kEkHXqCPivJj/AB3UjEX4bQUrJ5YoxJIZJO7Goa6zethI3nz8Fo+zWRv68xf0hYu0Nxr6Z2WODvDeLa0kgD5O3c2/qqZBtKbjzEI8QpqGsooYTUagtl7whvpa6abt5rddo3GMmFimLImyd/N3RzEiw01Ft1VeN5mniPCiHCwYbm4tvIsf0g295DQhrrZqmwcNbEtABFjyQbjjvtPFA90dNB8pdBlNS4OyxxB5ytYXWPpk8uX3WPGOJu7w12IxNDgIBO1rtL5gCASPNUntK4chw7h+aGAH14XPe7V8khkZmkeeZPw2XvxJwPDJsQSKBlwDe3ot3QWfhDH3V2Hx1rmNY57HuyAktGRz2jU6/RC8PZjxg/F6V9RJE2ItldHZpLgQGsdfX+0fcvD2WztGB04Lmg91LoSAfXkWm/R0eP1fKLi5qX2F9f8ARxckHVkRQSgEoAgCkIFkREHlxH1faPxVfx+bu6Wd43bFI73NJVir23Z5WVexuDvKeaP68Ujfe0heZ1f6rFr+am9i8AbRPdzdMf8Ala0BX8Bc+7FqkOo5Gc2Sk28HtaR9x9y3XG/F8eHR2FnzvHoM6fbf0b9/3UXiZvMKrRmxxvxfHh0dhZ87x6DOn239Gj4rlfDeAVGMVLpJHOy5rzTH2egzlmtsNgPYDPDeAVGMVLpJHOy5rzTH9xnLNbYbAfHt+G0EVLE2KJoZGwf/AFzjzPMkqczGnGI5SzFYxHLWYhwpTyUgpGMDGsF4zza+2jyeZNze+9yuL1EDo3OY8Wc0lrh0INir/wAYce3zQ0brDUOmH3R/93u6qq8UnPJFNzngikd/bsWO95Zf2qEZ9ow0y7dwOCKCnv8AU+BJI+FlUOEOAi+01YCG7ti2J8ZOg+z7+i6UxoaAAAABYAaAAbABRtLkyrHabAH4bPf6OR482vaV9ezWYuw6mPRpb7GOLR9y+HahUCPDZr/TyMHm5w/AH3L1dmtPlw+lH1m5/wBtxcPgVOPCP7Sjx/K+EKr8R9nuH4hL39TT5pLBpc172FwGgzZHAGw0urQi9l6KkUfZNhMUjJG0t3McHDNLK5txqLtLrHXkVY+I+HabEYu5qohIwODhqWkOFwCHNII0JHtW0RBRIuyDCGkO+Sk2INjNKRp1GfUeCuGIYbHPC+nkbeKRhjc0Et9Ai1gRqNOi9aINTh3DlPT0nyGJhEGV7MuZxOWQuc/0ib6lx581ngOAQUMApqdmWIFxylxf6+rtXEk3WzXJ+1fiGakxPDA2ofDA57DMA8sjLBMzOXi9iMt735ILtgfBlDh5lkpYGxvkaQ513OOU65W5icrbgGw6DoFyzsi4LosSpah1XBnLKpwa4Ocx1sjCW5mkXGt7HqvfxlxlX4jBPLhmaCip2uc+pddj5i3TJDzDdfA9SNjb+xiYPwimcGNYfTDsotmc2RzC93Vxy3JQZ4Z2V4VTysmjpvTjIc3NJI8Bw2OVziDbfVe7iXgHD8RkE1TBnkAy5mvewkDYHIRe3irMiCmS9leEuiZCaQZWFzgQ+QPJda+Z4dmdsNzpbSy2uKcHUdTFBDJD83TEGFrXOYGloAHqkX25rfIg8uJ4dFVRPhmYHxyNyvab2I8xqD4jVazAuD6Kihkp4IGtjlv3gcXPzgjLZxeSSLG1vFb1Sg5nPwDgFO5xywiSMOOSSpcbOsbBzHya8tCFpv0dsCg+TyVrorziV8TZDfSPKwkNGw1JF7X5LoGI8A4bUyvmmpI3yPN3OJdcnbkfBbrC8MhpY2wwRtjjbezWCw11J8Tfmg9SAIiAiIEBEUEoMZm3BHULTAW3W7AWur4bHNyP3rF1mnmItHpm6iuY7nDI8VfgVbWxNZmEgvGD6upL4nHqAHuBt0Wt4bwCoxipdJI92W95pj8GM5ZrbDYD2A3nizs8dV1bqk1AZE4NMgLSXtDGgHJysQOe2u63fAeN0VREYaMZGwkgMOji0nSXxzb33vus03+OY5U922Yb3C8OipomwwsDGNFgB8STzJ3JWq4wrGMia2SMyMe7K5uYsaRa9nW38tlvt15cUw9lRGY37HY8w4bOCzTlTOXL6zg8zuElCW9071myPAdC7mHdW9DqVaMFo6UVUbW/PPiibG1xt3bBGN2/WeXOJzbC+irmJ4dJTPLJB5EbOHUfktlwUf5U3xa/7r/go/Umdke+eHREJQrTcUcQR0EBmk1O0bOb38gPDqeQU4jOycRlSe1erNTPTYdFq5z2vdbkX3Yy/k0vcegsupYLRtjayNo9GJjWjyaA1v3LmnZlgsk0r8UqtXyl3dX6OteQDkLei3wB6hddpIcrfE6n8ls0ad14j1Vfp1zaI+z7oiglek2hKNCkBEBERAXFu2imZNi2ExSDMyR7GOB2LXTMa4e0Ers+6492u/01g397F/HjQXXtIp2RYNVxxsaxjIC1rWgNaGi1gANAF4ew7+hqb+1N/Getv2lQOkwutawFx7h5AGp01PwBVT7CeIqZ1DDQtkvUMEz3Myv0YZXG5dbLs5vPmg0VNh7saxuvgqKmqjjpwRGIJcgAa5rLWLSNbknTcry4zgAwvGsOghqaqRkrmucJpc+uci1mhotpzC+2AY7T4Xj+JvrX9y198hLXOvme17bZQd26rz8d8V0s+K4bWxyk08Zs6Xu5Gtux93AZm3dYObe19wgufb1O+PC8zHOae/j1aS02s/mFS8bxeqxLD700skVJh9LEZZWlwdPVhjAWBwNy1t9b89eisvbZicVXgjJ4XZo3zxlri1zbgZxcBwBtovbi1IyHhctjaGtNFG6wFrue1jnOPUkkm/igplDxBXYxh8dLFLIxlLA+WtqLuDnGMO7qEOvdziA0k89+Wt87DsUlqcMa6Z7pHMkfGHOJLsgyloJOptmI8rLx9mVEDw7liaM8sVTew1c8l7RfqdAPYFrv0fMahFK6izH5QJJJSzK7RnoC5dbKNdLXugr2DUzMWxPERXV08AhlLYhHO2Ftg97MoDwRYBg2tueq9MELcMx2hho62eeKYWk7yZswOYuaWnIAOQOovdavh1+G02J4m3F2Ri8zjEJY3P8AWke4kBoNrtc0+0K84RjXDTZ4jTtgE2dojyQS587jZuX0N7lB1BERAWN+infyUhBBKAKUQFjIwOBB2WSLkxnaSd2onhLDY+wrmfFvBUsU3y7DCWSglzo22FyfWdHfTXW7DoeXRdhkYHCxFwtfPRlu2o+K87U0Lac5pvDHfSmk5rw5vw12mQyfNVg+Tyg5SSD3ZdzvpeM+DtPFXuCZr2hzHBzTsWkEH2hajiDhSlrtZ4hn2D2+hIP8Q38jcKmSdl0sLi6jrXx35OBafa6Mi/7Kz4pb+FPxl0XEKCOdmSRtx8QeoPIrQYVw0aWo77vAY2tdqfRcLi2vL2qs/wD5bHBoK9tv71//AI/xUHszqqg/yyvc8dBnk92cgD3Ln068zLnZH3bniXtHpaUFsRFRLsGsPzYP2pLWPk258lo8C4UqcTmFbidwzdkR9EubuBlv82zwPpO5+Nx4b4FpKQh0URfIP6yT03Dy+i32AK409HbV2p6cv81dp0m21I/K2lZt4/6+WH0gFjawAGUbCw206LYIi9HS04064hrpSKRiEOPvQBSisTEREBY7rJEEL4z0UT3Ne+NjnN9Vzmhzm219EkXGuq+6IC89PRxRXMcbGZt8jQ255XsNV6EQeSpw2GU5pYo3m1gXsa426XIUvw6FzBGYoyxuzSxpaPJtrDmvUiD41FHHI0MfGxzRs1zQ5ottYEWUupmFndljSywblIBbYbDLtbwX1UoPhDCyNoZG1rWjYNAa0X8AkFLGwuLGMa52ri1oBceriBqvspQeOpwyCU5pIYnna72NcbDxIuogwqnjcHMgia4bFsbWkeRAXsRAWO/kskQECIgIiICgFN1KAiIgwfC124uvg6iZ4j2r1EqAFXbSpbmEZpWeYeUUDep+CzbRsHK/mvQi5Ghpx+1GNKkekNbbZSiglWrE31RQApQEREBAVjusggIiICEqCUA6oJCIiAiKHFBN0QBEBERAQFY7+SyQEREBAhKxAQZKDqhCBBICIiAiKAEABSiICIhQQSgCNClAREQFidVLgpQLIiICFFFtUABSiICIoOyASgCAKUBERAUb+SEe5SgWREQEJRYgIJA5qUQICIiAiEqAglERARFBKCUREBERARQDqpQEREBEJQICIiAiKHFBKIiAiIgIoupQFKhEBFBOikICIiAiKLoJCgIiDF+4WSIgkoiIIUN3KhEGSlEQFi7ZEQS1ERBKhEQQ7cLJEQQpREEKG/ioRBmFHL3IiCVi/Y+1EQSFIREEf5KURBg7f3rNEQFCIglYM/EqEQf/2Q==").placeholder(R.drawable.placeholder).error(R.drawable.error).into(circleImageView);

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
                Log.d(TAG, "onResponse: ");

                if (response.body()!= null) {
                    User u = response.body().get(0);
                    setUserProfile(u);
                    profileimage = u.getPicture();
                    getProfileImage();

                }

                // TWO KFC Zinger Burgerso  ok done!
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


    private void setUserProfile(User u) {


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
