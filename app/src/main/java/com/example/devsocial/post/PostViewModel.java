package com.example.devsocial.post;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostViewModel extends ViewModel {
    private MutableLiveData<List<Post>> posts;
    private PostRepository repository;
    private String TAG = "PostsViewModel";

    public PostViewModel() {
        repository = new PostRepository();
    }

    public LiveData<List<Post>> getPosts() {
        if (posts == null) {
            posts = new MutableLiveData<>();
            loadPosts();
        }
        return posts;
    }

    public void loadPosts() {
        repository.getPosts(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    posts.setValue(response.body());
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e(TAG, errorBody);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }
}
