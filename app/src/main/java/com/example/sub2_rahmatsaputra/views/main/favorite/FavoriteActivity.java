package com.example.sub2_rahmatsaputra.views.main.favorite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.sub2_rahmatsaputra.R;
import com.example.sub2_rahmatsaputra.adapter.FavoriteAdapter;
import com.example.sub2_rahmatsaputra.models.favorite.FavoriteModel;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteActivity extends AppCompatActivity {

    @BindView(R.id.recyclerViewFavorite)
    RecyclerView recyclerViewFavorite;

    @BindView(R.id.textViewNoDataFavorite)
    TextView textViewNoDataFavorite;


    private FavoriteDatabase db;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ArrayList<FavoriteModel> favoriteModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Favorite User");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ButterKnife.bind(this);

        favoriteModelArrayList = new ArrayList<>();

        db = Room.databaseBuilder(getApplicationContext(),FavoriteDatabase.class, "userdb").allowMainThreadQueries().build();

        recyclerViewFavorite.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewFavorite.setLayoutManager(layoutManager);

        favoriteModelArrayList.addAll(Arrays.asList(db.favoriteDao().getAll()));

        adapter = new FavoriteAdapter(this, favoriteModelArrayList);
        recyclerViewFavorite.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
