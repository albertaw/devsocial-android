package com.example.devsocial.post;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.devsocial.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private List<Post> posts;

    public PostAdapter(List<Post> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.nameTextView.setText(post.getUser().getName());
        DateFormat formatter;
        formatter = new SimpleDateFormat("MMM d, YYYY");
        String formattedDate = formatter.format(post.getCreatedAt());
        holder.dateTextView.setText(formattedDate );
        holder.textTextView.setText(post.getText());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        private final TextView nameTextView;
        private final TextView dateTextView;
        private final TextView textTextView;

        public ViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.post_name);
            dateTextView = view.findViewById(R.id.post_date);
            textTextView = view.findViewById(R.id.post_text);
        }
    }

}
