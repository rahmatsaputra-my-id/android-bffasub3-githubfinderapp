package com.example.sub2_rahmatsaputra.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sub2_rahmatsaputra.R;
import com.example.sub2_rahmatsaputra.models.favorite.FavoriteModel;
import com.example.sub2_rahmatsaputra.models.search.ItemsItem;
import com.example.sub2_rahmatsaputra.views.main.favorite.FavoriteDatabase;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import butterknife.BindView;

public class FavoriteAdapter extends RecyclerView.Adapter <FavoriteAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<FavoriteModel> favoriteModels;
    private ListAdapter.CallbackInterface callbackInterface;
    private FavoriteDatabase favoriteDatabase;

    public FavoriteAdapter(Context context, ArrayList<FavoriteModel> favoriteModels){
        this.context = context;
        this.favoriteModels = favoriteModels;
//        this.callbackInterface = (ListAdapter.CallbackInterface) context;

        favoriteDatabase = Room.databaseBuilder(context.getApplicationContext(),
                FavoriteDatabase.class, "userdb").allowMainThreadQueries().build();
    }

    public interface CallbackInterface {
        void onHandleSelection(int position, ItemsItem itemsItem, View view);
    }

    @NonNull
    @Override
    public FavoriteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.MyViewHolder holder, int position) {

        final String name = favoriteModels.get(position).getLogin();

//        holder.

    }

    @Override
    public int getItemCount() {
        return favoriteModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textViewNameList)
        TextView textViewNameList;

        @BindView(R.id.imageViewList)
        ImageView imageViewList;

//        @BindView(R.id.progress_bar_list)

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
