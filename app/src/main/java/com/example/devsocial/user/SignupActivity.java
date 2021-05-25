package com.example.devsocial.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.devsocial.R;

import org.w3c.dom.Text;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    private Button submitButton;
    private TextView loginTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(this);

        loginTextView = findViewById(R.id.login_text_view);
        loginTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_button:
                submitForm();
                break;
            case R.id.login_text_view:
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
        }
    }

    private void submitForm() {
        // check for errors
        // query api
        // redirect to login if successful
    }
}