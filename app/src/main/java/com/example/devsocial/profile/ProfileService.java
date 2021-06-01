package com.example.devsocial.profile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProfileService {
    @GET("/api/profiles")
    Call<List<Profile>> getProfiles();

    @GET("/api/profiles/{id}")
    Call<Profile> getProfile(@Path("id") String id);
}
