package com.example.devsocial.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.devsocial.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onStart() {
        super.onStart();
        // if there is not a jwt key, then the user
        // is not logged in and needs to be redirected
        // to the landing page
        SharedPreferences sharedPref = this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String jwt = sharedPref.getString(getString(R.string.jwt_key), "");
        if (jwt.isEmpty()) {
            Intent intent = new Intent(MainActivity.this,LandingActivity.class);
            startActivity(intent);
            finish();
        }
    }
}