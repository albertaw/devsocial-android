package com.example.devsocial.profile;

import com.example.devsocial.user.UserService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileRepository {
    private Retrofit retrofit;
    private ProfileService service;
    private String BASE_URL = "https://alberta-social.herokuapp.com/";

    public ProfileRepository() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ProfileService.class);
    }

    public void getProfiles(Callback<List<Profile>> callback) {
        Call<List<Profile>> call = service.getProfiles();
        call.enqueue(callback);
    }
}
