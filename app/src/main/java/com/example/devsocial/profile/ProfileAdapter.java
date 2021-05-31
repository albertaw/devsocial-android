package com.example.devsocial.profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.devsocial.R;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {
    private List<Profile> profiles;
    private OnItemClickListener listener;

    public ProfileAdapter(List<Profile> profiles, OnItemClickListener listener) {
        this.profiles = profiles;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.profile_item, viewGroup, false);
        return new ProfileAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Profile profile = profiles.get(position);
        holder.nameTextView.setText(profile.getUser().getName());
        holder.usernameTextView.setText("@" + profile.getUsername());
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        private final TextView nameTextView;
        private final TextView usernameTextView;

        public ViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.profiles_name_text_view);
            usernameTextView = view.findViewById(R.id.profiles_username_text_view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
