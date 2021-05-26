package com.example.devsocial.user;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRepository {
    private Retrofit retrofit;
    private UserService service;
    private String BASE_URL = "https://alberta-social.herokuapp.com/";

    public UserRepository() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(UserService.class);
    }

    public void createUser(Map<String, String> options, Callback<User> callback) {
        Call<User> call = service.createUser(options);
        call.enqueue(callback);
    }

    public void login(Map<String, String> options, Callback<LoginResponse> callback) {
        Call<LoginResponse> call = service.login(options);
        call.enqueue(callback);
    }
}
