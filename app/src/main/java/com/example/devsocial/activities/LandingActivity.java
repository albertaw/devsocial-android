package com.example.devsocial.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.devsocial.R;
import com.example.devsocial.user.LoginActivity;
import com.example.devsocial.user.SignupActivity;

public class LandingActivity extends AppCompatActivity implements View.OnClickListener {
    private Button signupButton;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        signupButton = findViewById(R.id.signup_button);
        signupButton.setOnClickListener(this);

        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signup_button:
                startActivity(new Intent(LandingActivity.this, SignupActivity.class));
                break;
            case R.id.login_button:
                startActivity(new Intent(LandingActivity.this, LoginActivity.class));
                break;
        }
    }

}