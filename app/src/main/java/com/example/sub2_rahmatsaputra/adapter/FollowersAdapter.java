package com.example.sub2_rahmatsaputra.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.sub2_rahmatsaputra.R;
import com.example.sub2_rahmatsaputra.models.follow.FollowModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.MyFollowerAdapter> {

    private Context context;
    private ArrayList<FollowModel> followModels;

    public FollowersAdapter(Context context, ArrayList<FollowModel> followModels) {
        this.context = context;
        this.followModels = followModels;
    }

    @NonNull
    @Override
    public MyFollowerAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyFollowerAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyFollowerAdapter holder, int position) {
        holder.textViewNameList.setText(followModels.get(position).getLogin());

        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.image_not_found)
                .centerCrop()
                .priority(Priority.HIGH);

        Glide.with(context)
                .load(followModels.get(position).getAvatar_url())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBarList.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBarList.setVisibility(View.GONE);
                        return false;
                    }
                })
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imageViewList);
    }

    @Override
    public int getItemCount() {
        try {
            return followModels.size();
        } catch (NullPointerException e) {
            return 0;
        }
    }

    public class MyFollowerAdapter extends RecyclerView.ViewHolder {

        ImageView imageViewList;
        TextView textViewNameList;
        ProgressBar progressBarList;

        public MyFollowerAdapter(@NonNull View itemView) {
            super(itemView);

            progressBarList = itemView.findViewById(R.id.progressBarList);
            textViewNameList = itemView.findViewById(R.id.textViewNameList);
            imageViewList = itemView.findViewById(R.id.imageViewList);
        }
    }
}
