package com.example.devsocial.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.example.devsocial.R;
import com.example.devsocial.activities.MainActivity;
import com.example.devsocial.utils.DecodedToken;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button submitButton;
    private TextView signupTextView;
    private TextInputLayout email;
    private TextInputLayout password;
    private UserRepository repository;
    private String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        repository = new UserRepository();

        submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(this);

        signupTextView = findViewById(R.id.signup_text_view);
        signupTextView.setOnClickListener(this);

        email = findViewById(R.id.email_text_field);
        password = findViewById(R.id.password_text_field);
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
        Map<String, String> options = new HashMap<>();
        String emailData = email.getEditText().getText().toString().trim();
        options.put("email", emailData);
        String passwordData = password.getEditText().getText().toString().trim();
        options.put("password", passwordData);
        repository.login(options, new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    String token = response.body().getToken();
                    String[] parts = token.split(" ");
                    JWT  jwt = new JWT(parts[1]);
                    String id = jwt.getClaim("id").asString();
                    String name = jwt.getClaim("name").asString();

                    SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(getString(R.string.jwt_key), token);
                    editor.putString(getString(R.string.id_key), id);
                    editor.putString(getString(R.string.name_key), name);
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("jwt", jwt);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
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
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }

    private void setErrors(JSONObject error) {
        try {
            if (error.has("email")) {
                email.setError(error.getString("email"));
            }
            if (error.has("password")) {
                password.setError(error.getString("password"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearErrors() {
        email.setError(null);
        password.setError(null);
    }

}