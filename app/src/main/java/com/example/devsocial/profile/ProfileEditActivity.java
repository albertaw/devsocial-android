package com.example.devsocial.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.auth0.android.jwt.JWT;
import com.example.devsocial.R;
import com.example.devsocial.user.LoginActivity;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileEditActivity extends AppCompatActivity implements View.OnClickListener{
    private ProfileViewModel model;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private String jwt;
    private JWT decoded;
    private TextInputLayout username;
    private TextInputLayout bio;
    private TextInputLayout location;
    private TextInputLayout skills;
    private TextInputLayout website;
    private TextInputLayout github;
    private Button submitButton;
    private ProfileRepository repository;
    private String TAG = "ProfileEditActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        repository = new ProfileRepository();

        username = findViewById(R.id.username_text_field);
        bio = findViewById(R.id.bio_text_field);
        location = findViewById(R.id.location_text_field);
        skills = findViewById(R.id.skills_text_field);
        website = findViewById(R.id.website_text_field);
        github = findViewById(R.id.github_text_field);

        submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(this);

        sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String userId = sharedPref.getString(getString(R.string.id_key), "");
        model = new ViewModelProvider(this).get(ProfileViewModel.class);
        model.getProfile(userId).observe(this, response-> {
            setFields(response);
        });

        editor = sharedPref.edit();
        jwt = sharedPref.getString(getString(R.string.jwt_key), "");
        decoded = new JWT(jwt);
        if (decoded.isExpired(10)) {
            editor.remove(getString(R.string.jwt_key));
            editor.commit();
            updateUI();
        }
    }

    private void setFields(Profile profile) {
        username.getEditText().setText(profile.getUsername());
        bio.getEditText().setText(profile.getBio());
        location.getEditText().setText(profile.getLocation());
        String str = String.join(", ", profile.getSkills());
        skills.getEditText().setText(str);
        website.getEditText().setText(profile.getWebsite());
        github.getEditText().setText(profile.getGithubUsername());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_button:
                submitForm();
                break;
        }
    }

    private void submitForm() {
        Profile profile = new Profile();
        profile.setUsername(username.getEditText().getText().toString());
        profile.setBio(bio.getEditText().getText().toString());
        profile.setLocation(location.getEditText().getText().toString());
        //TODO make sure an empty array is being sent if there are no skills
        String[] skillsArray;
        String skillsString = skills.getEditText().getText().toString().trim();
        if (skillsString.length() == 0) {
            skillsArray = new String[0];
        } else {
            skillsArray = skillsString.split(",");
        }
        profile.setSkills(skillsArray);
        profile.setWebsite(website.getEditText().getText().toString());
        profile.setGithubUsername(github.getEditText().getText().toString());
        repository.createOrUpdateProfile("Bearer " + jwt, profile, new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if(response.isSuccessful()){
                    clearErrors();
                    Toast.makeText(ProfileEditActivity.this, R.string.profile_created_success, Toast.LENGTH_LONG).show();
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
            public void onFailure(Call<Profile> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    private void setErrors(JSONObject error) {
        try {
            if (error.has("username")) {
                username.setError(error.getString("username"));
            }
            if (error.has("bio")) {
                bio.setError(error.getString("bio"));
            }
            if (error.has("location")) {
                location.setError(error.getString("location"));
            }
            if (error.has("skills")) {
                skills.setError(error.getString("skills"));
            }
            if (error.has("website")) {
                website.setError(error.getString("website"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearErrors() {
        username.setError(null);
        bio.setError(null);
        location.setError(null);
        skills.setError(null);
        website.setError(null);
    }

    private void updateUI() {
        Intent intent = new Intent(ProfileEditActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}