package com.example.tayyab.lostandfoundapp.InterfaceService;

import com.example.tayyab.lostandfoundapp.models.GetPostClass;
import com.example.tayyab.lostandfoundapp.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PostClient {

    @POST("Posts")
    Call<Post> AddPost(@Body Post post);


    @GET("Posts")
    Call<GetPostClass> GetAllPosts();

    @PUT("Post/{PostID}")
    Call<Post> UpdatePost(@Path("PostID") Integer id, @Body Post post);
}
