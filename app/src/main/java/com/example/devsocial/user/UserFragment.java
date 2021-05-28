package com.example.devsocial.user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devsocial.R;
import com.example.devsocial.activities.LandingActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFragment extends Fragment implements View.OnClickListener {
    private TextView nameTextView;
    private Button logoutButton;
    private Button deleteAccountButton;
    private String name;
    private LinearLayout postsLink;
    private SharedPreferences sharedPref;
    private UserRepository repository;
    private String TAG = "UserFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPref = getActivity().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        repository = new UserRepository();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_user, container, false);
        nameTextView = view.findViewById(R.id.name_text_view);
        name = sharedPref.getString(getString(R.string.name_key), "");
        nameTextView.setText(name);
        postsLink = view.findViewById(R.id.posts_link);
        postsLink.setOnClickListener(this);
        logoutButton = view.findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(this);
        deleteAccountButton = view.findViewById(R.id.delete_account_button);
        deleteAccountButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.posts_link:
                startActivity(new Intent(getActivity(), UserPostsActivity.class));
                break;
            case R.id.logout_button:
                logout();
                break;
            case R.id.delete_account_button:
                showDeleteDialog();
        }
    }

    private void logout() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(getString(R.string.jwt_key));
        editor.commit();
        Intent intent = new Intent(getContext(), LandingActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete account?")
                .setMessage("This willl delete all of your profile info and your posts.")
                .setPositiveButton(getString(R.string.text_submit), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteAccount();
                    }
                })
                .setNegativeButton(R.string.text_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteAccount() {
        String jwt = sharedPref.getString(getString(R.string.jwt_key), "");
        repository.deleleUser(jwt, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), R.string.delete_account_confirmation, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getContext(), LandingActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }
}