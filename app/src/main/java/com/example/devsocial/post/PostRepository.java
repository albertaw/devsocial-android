package com.example.devsocial.post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostRepository {
    private Retrofit retrofit;
    private PostService service;
    private String BASE_URL = "https://alberta-social.herokuapp.com/";

    public PostRepository() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(PostService.class);
    }

    public void getPosts(Callback<List<Post>> callback) {
        Call<List<Post>> call = service.getPosts();
        call.enqueue(callback);
    }

    public void createPost(String authorization, Post post, Callback<Post> callback) {
        Call<Post> call = service.createPost(authorization, post);
        call.enqueue(callback);
    }
}
