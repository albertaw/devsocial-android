package com.example.devsocial.user;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {
    @POST("/api/users")
    Call<User> createUser(@Body Map<String, String> options);

    @POST("/api/users/login")
    Call<LoginResponse> login(@Body Map<String, String> options);
}
