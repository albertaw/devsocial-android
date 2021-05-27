package com.example.devsocial.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.devsocial.R;
import com.example.devsocial.post.Post;
import com.example.devsocial.post.PostAdapter;

import java.util.List;

public class UserPostsActivity extends AppCompatActivity {
    private List<Post> posts;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private LinearLayout emptyView;
    private UserViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_posts);

        recyclerView = findViewById(R.id.user_posts_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        emptyView = findViewById(R.id.user_posts_empty_view);
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String id = sharedPref.getString(getString(R.string.id_key), "");
        model = new ViewModelProvider(this).get(UserViewModel.class);
        model.getPosts(id).observe(this, response -> {
            posts = response;
            adapter = new PostAdapter(posts);
            recyclerView.setAdapter(adapter);
            if (posts.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
            }
            else {
                recyclerView.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);
            }
        });
    }
}