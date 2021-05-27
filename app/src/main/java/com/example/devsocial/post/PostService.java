package com.example.devsocial.post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PostService {
    @GET("/api/posts")
    Call<List<Post>> getPosts();

    @POST("/api/posts")
    Call<Post> createPost(@Header("Authorization") String authorization, @Body Post post);
}
