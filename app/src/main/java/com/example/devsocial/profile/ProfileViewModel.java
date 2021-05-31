package com.example.devsocial.profile;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<List<Profile>> profiles;
    private ProfileRepository repository;
    private String TAG = "ProfileViewModel";

    public ProfileViewModel() {
        repository = new ProfileRepository();
    }

    public LiveData<List<Profile>> getProfiles() {
        if(profiles == null) {
            profiles = new MutableLiveData<>();
            loadProfiles();
        }
        return profiles;
    }

    public void loadProfiles() {
        repository.getProfiles(new Callback<List<Profile>>() {
            @Override
            public void onResponse(Call<List<Profile>> call, Response<List<Profile>> response) {
                if(response.isSuccessful()) {
                    profiles.setValue(response.body());
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e(TAG, errorBody);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Profile>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }
}
