package com.example.devsocial.profile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProfileService {
    @GET("/api/profiles")
    Call<List<Profile>> getProfiles();
}
