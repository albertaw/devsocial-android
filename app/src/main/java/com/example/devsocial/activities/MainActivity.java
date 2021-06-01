package com.example.devsocial.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.auth0.android.jwt.JWT;
import com.example.devsocial.R;
import com.example.devsocial.post.CreatePostFragment;
import com.example.devsocial.post.PostsFragment;
import com.example.devsocial.profile.ProfilesFragment;
import com.example.devsocial.user.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JWT jwt = getIntent().getParcelableExtra("jwt");
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.posts_menu_item:
                        loadFragment(new PostsFragment());
                        break;
                    case R.id.create_menu_item:
                        loadFragment(new CreatePostFragment());
                        break;
                    case R.id.account_menu_item:
                        loadFragment(new UserFragment());
                        break;
                    case R.id.profiles_menu_item:
                        loadFragment(new ProfilesFragment());
                        break;
                }
                return true;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.posts_menu_item);
    }


    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences sharedPref = this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String jwt = sharedPref.getString(getString(R.string.jwt_key), "");
        if (jwt.isEmpty()) {
            Intent intent = new Intent(MainActivity.this,LandingActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_view, fragment)
                .commit();
    }
}