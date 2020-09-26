package com.example.sub2_rahmatsaputra.models.search;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemsItem implements Parcelable {
    public static final Creator<ItemsItem> CREATOR = new Creator<ItemsItem>() {
        @Override
        public ItemsItem createFromParcel(Parcel parcel) {
            return new ItemsItem(parcel);
        }

        @Override
        public ItemsItem[] newArray(int size) {
            return new ItemsItem[size];
        }
    };

    @SerializedName("login")
    @Expose
    private String login;

    @SerializedName("avatar_url")
    @Expose
    private String avatar_url;


    protected ItemsItem(Parcel in) {
        login = in.readString();
        avatar_url = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
