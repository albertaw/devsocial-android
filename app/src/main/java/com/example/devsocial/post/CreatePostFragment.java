package com.example.devsocial.post;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.devsocial.R;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePostFragment extends Fragment {
    private TextInputLayout text;
    private Button submitButton;
    private PostRepository repository;
    private String name;
    private String jwt;
    private String TAG = "CreatePostFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repository = new PostRepository();
        SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        name = sharedPref.getString(getString(R.string.name_key), "");
        jwt = sharedPref.getString(getString(R.string.jwt_key), "");
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
                //check if jwt has expired
                Post post = new Post();
                post.setName(name);
                post.setText(text.getEditText().getText().toString());
                repository.createPost(jwt, post, new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        if (response.isSuccessful()) {
                            //clear field
                            text.getEditText().setText(null);
                            //show success message
                            Toast.makeText(getContext(), "Post created", Toast.LENGTH_LONG).show();
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
        });
        return view;
    }
}