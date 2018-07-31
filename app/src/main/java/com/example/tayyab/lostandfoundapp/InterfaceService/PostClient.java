package com.example.tayyab.lostandfoundapp.InterfaceService;

import com.example.tayyab.lostandfoundapp.CommentsPost;
import com.example.tayyab.lostandfoundapp.models.CommentAdd;
import com.example.tayyab.lostandfoundapp.models.GetPostClass;
import com.example.tayyab.lostandfoundapp.models.Post;
import com.example.tayyab.lostandfoundapp.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface PostClient {

    @POST("Posts")
    Call<Post> AddPost(@Body Post post);


    @GET("Posts")
    Call<GetPostClass> GetAllPosts();


    @GET("Search")
    Call<GetPostClass> getcategory(@Query("category") String email);

    @POST
    Call<CommentsPost.CommentsByPost> getcomments(@Url String url);

    @POST("AddComments")
    Call<CommentAdd> addcomments(@Body CommentAdd commentAdd);

    @PUT("Post/{PostID}")
    Call<Post> UpdatePost(@Path("PostID") Integer id, @Body Post post);
}
