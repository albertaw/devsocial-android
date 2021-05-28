package com.example.devsocial.user;

import com.example.devsocial.post.Post;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {
    @POST("/api/users")
    Call<User> createUser(@Body Map<String, String> options);

    @POST("/api/users/login")
    Call<LoginResponse> login(@Body Map<String, String> options);

    @GET("/api/users/{id}/posts")
    Call<List<Post>> getUserPosts(@Path("id") String id);

    @DELETE("/api/user")
    Call<Void> deleteUser(@Header("Authorization") String authorization);
}
