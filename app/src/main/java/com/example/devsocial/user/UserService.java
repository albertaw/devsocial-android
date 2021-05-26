package com.example.devsocial.user;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    @POST("/api/users")
    Call<User> createUser(@Body Map<String, String> options);
}
