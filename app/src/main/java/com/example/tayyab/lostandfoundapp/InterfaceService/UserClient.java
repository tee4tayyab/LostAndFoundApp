
package com.example.tayyab.lostandfoundapp.InterfaceService;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.widget.ImageView;

import com.example.tayyab.lostandfoundapp.models.Dummy;
import com.example.tayyab.lostandfoundapp.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserClient {

    @POST("RegUser")
    Call<User> CreateUserAccount(@Body User user);

    @GET("RegUser")
    Call<List<User>> GetUserDetails(@Query("email") String email);

    @Headers({"Content-Type:application/json","Accept:application/json"})


/*    @PUT("RegUser/{email}")
    Call<User> PutUserDetails(
            @Path("email") String email,
            @Body User user);*/
    @PUT("RegUser/{RegisterID}")
    Call<User> PutUserDetails(
            @Path("RegisterID") Integer id,
            @Body User user);

    @PUT("posts/{id}")
    Call<Dummy> updateDummy(@Path("id") Integer id, @Body Dummy dummy);




}
