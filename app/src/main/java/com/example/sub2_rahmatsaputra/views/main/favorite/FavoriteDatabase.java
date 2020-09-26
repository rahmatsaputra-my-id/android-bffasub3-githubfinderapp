package com.example.sub2_rahmatsaputra.views.main.favorite;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.sub2_rahmatsaputra.models.favorite.FavoriteModel;

@Database(entities = {FavoriteModel.class}, version = 1)
public abstract class FavoriteDatabase extends RoomDatabase {
    public abstract FavoriteDao favoriteDao();
}
