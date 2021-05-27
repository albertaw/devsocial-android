package com.example.devsocial.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.devsocial.R;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.security.spec.ECField;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    private Button submitButton;
    private TextView loginTextView;
    private TextInputLayout name;
    private TextInputLayout email;
    private TextInputLayout password;
    private TextInputLayout password2;
    private UserRepository repository;
    private String TAG = "SignupActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        repository = new UserRepository();

        submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(this);

        loginTextView = findViewById(R.id.login_text_view);
        loginTextView.setOnClickListener(this);

        name = findViewById(R.id.name_text_field);
        email = findViewById(R.id.email_text_field);
        password = findViewById(R.id.password_text_field);
        password2 = findViewById(R.id.password2_text_field);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_button:
                submitForm();
                break;
            case R.id.login_text_view:
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                break;
        }
    }

    private void submitForm() {
        Map<String, String> options = new HashMap<>();
        options.put("name", name.getEditText().getText().toString()).trim();
        options.put("email", email.getEditText().getText().toString()).trim();
        options.put("password", password.getEditText().getText().toString()).trim();
        options.put("password2", password2.getEditText().getText().toString()).trim();

        repository.createUser(options, new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    try {
                        JSONObject error = new JSONObject(response.errorBody().string());
                        clearErrors();
                        setErrors(error);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    private void setErrors(JSONObject error) {
        try {
            if (error.has("name")) {
                name.setError(error.getString("name"));
            }
            if (error.has("email")) {
                email.setError(error.getString("email"));
            }
            if (error.has("password")) {
                password.setError(error.getString("password"));
            }
            if (error.has("password2")) {
                password2.setError(error.getString("password2"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearErrors() {
        name.setError(null);
        email.setError(null);
        password.setError(null);
        password2.setError(null);
    }

}