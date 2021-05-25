package com.example.devsocial.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.devsocial.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button submitButton;
    private TextView signupTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(this);

        signupTextView = findViewById(R.id.signup_text_view);
        signupTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.submit_button:
                submitForm();
                break;
            case R.id.signup_text_view:
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                break;
        }
    }

    private void submitForm() {
    }
}