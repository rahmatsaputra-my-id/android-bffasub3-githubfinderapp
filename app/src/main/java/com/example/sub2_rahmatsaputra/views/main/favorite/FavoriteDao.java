package com.example.sub2_rahmatsaputra.views.main.favorite;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.sub2_rahmatsaputra.models.favorite.FavoriteModel;

@Dao
public interface FavoriteDao {
    @Query("SELECT * FROM tusers")
    FavoriteModel[] getAll();

    @Query("SELECT * FROM tusers WHERE id = :id LIMIT 1")
    FavoriteModel getUserDetail(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertUser(FavoriteModel favoriteModel);

    @Delete
    int deleteUser(FavoriteModel favoriteModel);
}
