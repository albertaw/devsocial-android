package com.example.devsocial.profile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProfileService {
    @GET("/api/profiles")
    Call<List<Profile>> getProfiles();

    @GET("/api/profiles/user/{userId}")
    Call<Profile> getProfileByUserId(@Path("userId") String userId);

    @POST("/api/profiles")
    Call<Profile> createOrUpdateProfile(@Header("Authorization") String authorization, @Body Profile profile);
}
