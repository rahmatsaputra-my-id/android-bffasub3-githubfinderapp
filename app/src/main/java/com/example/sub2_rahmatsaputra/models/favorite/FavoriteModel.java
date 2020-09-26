package com.example.sub2_rahmatsaputra.models.favorite;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "tusers")
public class FavoriteModel implements Serializable {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    @ColumnInfo(name = "login")
    public String login;

    @ColumnInfo(name = "avatar_url")
    public String avatar_url;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

//    public FavoriteModel(int id, String login, String avatar_url){
//        this.id = id;
//        this.login = login;
//        this.avatar_url = avatar_url;
//    }

}
