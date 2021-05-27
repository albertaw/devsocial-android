package com.example.devsocial.user;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.devsocial.post.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel extends ViewModel {

    private MutableLiveData<List<Post>> posts;
    private UserRepository repository;
    private String TAG = "UserViewModel";

    public UserViewModel() {
        repository = new UserRepository();
    }

    public LiveData<List<Post>> getPosts(String id) {
        if (posts == null) {
            posts = new MutableLiveData<>();
            loadPosts(id);
        }
        return posts;
    }

    public void loadPosts(String id) {
        repository.getUserPosts(id, new Callback<List<Post>>() {
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
