package com.example.sub2_rahmatsaputra.views.main;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sub2_rahmatsaputra.R;
import com.example.sub2_rahmatsaputra.adapter.ListAdapter;
import com.example.sub2_rahmatsaputra.base.BaseActivity;
import com.example.sub2_rahmatsaputra.base.BasePreferences;
import com.example.sub2_rahmatsaputra.models.search.ItemsItem;
import com.example.sub2_rahmatsaputra.views.detail.DetailActivity;
import com.example.sub2_rahmatsaputra.views.main.favorite.FavoriteActivity;
import com.example.sub2_rahmatsaputra.views.main.reminder.ReminderActivity;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainContract.View,
        ListAdapter.CallbackInterface {

    @BindView(R.id.recyclerViewList)
    RecyclerView recyclerViewList;

    @BindView(R.id.textViewNoData)
    TextView textViewNoData;

    private MainContract.Presenter presenter;
    private ListAdapter listAdapter;
    private BasePreferences basePreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Github User Finder");
        }
        ButterKnife.bind(this);

        presenter = new MainPresenter(this, this);
        basePreferences = new BasePreferences(this);
        recyclerViewList = findViewById(R.id.recyclerViewList);
        textViewNoData = findViewById(R.id.textViewNoData);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search by name...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                checkIsConnected();
                if (query.length() > 2) {
                    getMainList(query);
                    searchView.clearFocus();
                } else {
                    Toast.makeText(MainActivity.this, "Type more than two letters..", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchMenuItem.getIcon().setVisible(false, false);
        return true;
    }

    public void onFavouriteItemList(MenuItem item){
        Toast.makeText(MainActivity.this, "Favorit klik", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, FavoriteActivity.class);
        startActivity(intent);
    }

    public void onReminderItemClick(MenuItem item){
        Intent intent = new Intent (this, ReminderActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFailedMainList(String Message) {
        onDataFound(false);
    }

    @Override
    public void onSuccessMainList(List<ItemsItem> mainSearchModel) {

        onDataFound(true);
        listAdapter = new ListAdapter(this, mainSearchModel);
        recyclerViewList.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewList.setAdapter(listAdapter);
    }

    private void onDataFound(boolean found) {
        if (found) {
            recyclerViewList.setVisibility(View.VISIBLE);
            textViewNoData.setVisibility(View.GONE);

        } else {
            recyclerViewList.setVisibility(View.GONE);
            textViewNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onHandleSelection(int position, ItemsItem itemsItem, View view) {
        checkIsConnected();
        if (isNetworkConnected()) {
            Intent intent = new Intent(this, DetailActivity.class);
            basePreferences.setUserName(itemsItem.getLogin());
            startActivity(intent);
        }
    }

    private void getMainList(String keyword) {
        try {
            presenter.getMainList(keyword);
        } catch (NullPointerException e) {
        }
    }

}
