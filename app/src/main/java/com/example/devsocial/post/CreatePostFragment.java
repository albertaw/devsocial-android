package com.example.devsocial.post;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.auth0.android.jwt.JWT;
import com.example.devsocial.R;
import com.example.devsocial.user.LoginActivity;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePostFragment extends Fragment {
    private TextInputLayout text;
    private Button submitButton;
    private PostRepository repository;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private String jwt;
    private JWT decoded;
    private String TAG = "CreatePostFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repository = new PostRepository();
        sharedPref = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        jwt = sharedPref.getString(getString(R.string.jwt_key), "");
        decoded = new JWT(jwt);
        if (decoded.isExpired(10)) {
            editor.remove(getString(R.string.jwt_key));
            editor.commit();
            updateUI();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_post, container, false);
        text = view.findViewById(R.id.text_text_field);
        submitButton = view.findViewById(R.id.post_submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (decoded.isExpired(10)) {
                    editor.remove(getString(R.string.jwt_key));
                    editor.commit();
                    updateUI();
                } else {
                    createPost();
                }
            }
        });
        return view;
    }
    private void updateUI() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    private void createPost(){
        Post post = new Post();
        post.setText(text.getEditText().getText().toString());
        repository.createPost("Bearer " + jwt, post, new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()) {
                    text.getEditText().setText(null);
                    Toast.makeText(getContext(), R.string.text_post_created_success, Toast.LENGTH_LONG).show();
                } else {
                    try {
                        JSONObject error = new JSONObject(response.errorBody().string());
                        text.setError(null);
                        if (error.has("text")) {
                            text.setError(error.getString("email"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }
}