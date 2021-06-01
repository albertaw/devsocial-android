package com.example.devsocial.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.devsocial.R;

public class ProfileActivity extends AppCompatActivity {
    private TextView name;
    private TextView username;
    private TextView bio;
    private TextView skills;
    private TextView location;
    private TextView website;
    private TextView github;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Profile profile = (Profile)getIntent().getSerializableExtra("profile");

        name = findViewById(R.id.profile_name_text_view);
        name.setText(profile.getUser().getName());

        username = findViewById(R.id.profile_username_text_view);
        username.setText("@" + profile.getUsername());

        bio = findViewById(R.id.profile_bio_text_view);
        bio.setText(profile.getBio());

        skills = findViewById(R.id.profile_skills_text_view);
        String str = String.join(", ", profile.getSkills());
        skills.setText(str);

        location = findViewById(R.id.profile_location_text_view);
        location.setText(profile.getLocation());

        website = findViewById(R.id.profile_website_text_view);
        website.setText(profile.getWebsite());

        github = findViewById(R.id.profile_github_text_view);
        github.setText(profile.getGithubUsername());
    }
}