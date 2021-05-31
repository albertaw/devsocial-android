package com.example.devsocial.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.devsocial.R;

import java.util.List;

public class ProfileFragment extends Fragment implements ProfileAdapter.OnItemClickListener {
    private List<Profile> profiles;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private TextView emptyView;
    private ProfileViewModel model;
    private String TAG = "ProfileFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        model.loadProfiles();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profiles, container, false);
        recyclerView = view.findViewById(R.id.profiles_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        emptyView = view.findViewById(R.id.profiles_empyt_view);
        model.getProfiles().observe(getViewLifecycleOwner(), response -> {
            profiles = response;
            adapter = new ProfileAdapter(profiles, this);
            recyclerView.setAdapter(adapter);
            if (profiles.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
            }
            else {
                recyclerView.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);
            }
        });
        return view;
    }

    @Override
    public void onItemClick(int position) {
        Profile profile = profiles.get(position);

    }
}
