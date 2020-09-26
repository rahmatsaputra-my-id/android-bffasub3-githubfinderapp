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
import com.example.sub2_rahmatsaputra.models.search.ItemsItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    private Context context;
    private List<ItemsItem> itemsItem;
    private ListAdapter.CallbackInterface callbackInterface;

    public ListAdapter(Context context, List<ItemsItem> itemsItem) {
        this.context = context;
        this.itemsItem = itemsItem;
        this.callbackInterface = (CallbackInterface) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.MyViewHolder holder, int position) {

        ItemsItem _itemsItem = itemsItem.get(position);
        holder.textViewNameList.setText(_itemsItem.getLogin());

        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.image_not_found)
                .centerCrop()
                .priority(Priority.HIGH);


        Glide.with(context)
                .load(_itemsItem.getAvatar_url())
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
        holder.itemView.setOnClickListener(view -> {
            if (callbackInterface != null) {
                callbackInterface.onHandleSelection(position, itemsItem.get(position), holder.itemView.findViewById(R.id.frame_layout_list));

            }
        });
    }

    @Override
    public int getItemCount() {
        try {
            return itemsItem.size();
        } catch (NullPointerException e) {
            return 0;
        }
    }

    public interface CallbackInterface {
        void onHandleSelection(int position, ItemsItem itemsItem, View view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewList;
        TextView textViewNameList;
        ProgressBar progressBarList;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            progressBarList = itemView.findViewById(R.id.progressBarList);
            textViewNameList = itemView.findViewById(R.id.textViewNameList);
            imageViewList = itemView.findViewById(R.id.imageViewList);
        }
    }
}
