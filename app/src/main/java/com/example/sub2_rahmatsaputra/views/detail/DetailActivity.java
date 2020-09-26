package com.example.sub2_rahmatsaputra.views.detail;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.sub2_rahmatsaputra.adapter.SectionsPagerAdapter;
import com.example.sub2_rahmatsaputra.base.BaseActivity;
import com.example.sub2_rahmatsaputra.base.BasePreferences;
import com.example.sub2_rahmatsaputra.models.detail.DetailModel;
import com.example.sub2_rahmatsaputra.models.favorite.FavoriteModel;
import com.example.sub2_rahmatsaputra.views.main.MainActivity;
import com.example.sub2_rahmatsaputra.views.main.favorite.FavoriteDatabase;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends BaseActivity implements DetailContract.View {

    private MenuItem itemToHide;
    private MenuItem itemToShow;
    private FavoriteDatabase db;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_favorite, menu);
        itemToShow = menu.findItem(R.id.favorite);
        itemToHide = menu.findItem(R.id.favoriteClicked);
        return super.onCreateOptionsMenu(menu);
    }


    public void insertData(final FavoriteModel favoriteModel){
        new AsyncTask<Void, Void, Long>(){
            @Override
            protected Long doInBackground(Void... voids) {
                long status = db.favoriteDao().insertUser(favoriteModel);
                return status;
            }

            @Override
            protected void onPostExecute(Long aLong) {
//                super.onPostExecute(aLong);
                Toast.makeText(DetailActivity.this, "status row "+aLong, Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }

    public void onFavoriteItemDetail(MenuItem menuItem){
        itemToShow.setVisible(false);
        itemToHide.setVisible(true);

//        LayoutInflater inflater = getLayoutInflater();
//        View layout = inflater.inflate(R.layout.custom_toast,(ViewGroup) findViewById(R.id.customToastLayout));
//        textViewFavorite.setVisibility(View.VISIBLE);
//        Toast toast = new Toast(getApplicationContext());
//        toast.setGravity(Gravity.CENTER_VERTICAL,0 , 100);
//        toast.setDuration(Toast.LENGTH_LONG);
//        toast.setView(layout);
//        toast.show();p

        FavoriteModel favoriteModel =  new FavoriteModel();
        favoriteModel.setLogin(basePreferences.getLogin());
        favoriteModel.setAvatar_url(basePreferences.getAvatar_url());
//        insertData(favoriteModel);

        Toast.makeText(DetailActivity.this, "Favorit Clicked", Toast.LENGTH_SHORT).show();
    }

    public void onFavouriteItemDetailClicked(MenuItem item){
        itemToShow.setVisible(true);
        itemToHide.setVisible(false);
        Toast.makeText(DetailActivity.this, "UnFavorit Clicked", Toast.LENGTH_SHORT).show();
    }

    @BindView(R.id.nestedScrollViewDetail)
    NestedScrollView nestedScrollViewDetail;

    @BindView(R.id.textViewNoDataDetail)
    TextView textViewNoDataDetail;

    @BindView(R.id.imageViewDetail)
    ImageView imageViewDetail;

    @BindView(R.id.progressBarDetail)
    ProgressBar progressBarDetail;

    @BindView(R.id.textViewNameDetail)
    TextView textViewNameDetail;

    @BindView(R.id.textViewFollowersDetail)
    TextView textViewFollowersDetail;

    @BindView(R.id.textViewFollowingDetail)
    TextView textViewFollowingDetail;

    @BindView(R.id.textViewRepositoryDetail)
    TextView textViewRepositoryDetail;

    @BindView(R.id.textViewCompanyDetail)
    TextView textViewCompanyDetail;

    @BindView(R.id.textViewLocationDetail)
    TextView textViewLocationDetail;

    @BindView(R.id.tabLayoutDetail)
    TabLayout tabLayoutDetail;

    @BindView(R.id.viewPagerDetail)
    ViewPager viewPagerDetail;

    private BasePreferences basePreferences;
    private String userName;
    private DetailContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ButterKnife.bind(this);

        presenter = new DetailPresenter(this, this);

        basePreferences = new BasePreferences(this);
        userName = basePreferences.getUserName();
        getSupportActionBar().setTitle(userName);

        getDetail(userName);
        initialisationID();

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPagerDetail.setAdapter(sectionsPagerAdapter);
        tabLayoutDetail.setupWithViewPager(viewPagerDetail);
    }

    private void initialisationID() {
        textViewNameDetail = findViewById(R.id.textViewNameDetail);
        textViewCompanyDetail = findViewById(R.id.textViewCompanyDetail);
        textViewLocationDetail = findViewById(R.id.textViewLocationDetail);
        imageViewDetail = findViewById(R.id.imageViewDetail);
        progressBarDetail = findViewById(R.id.progressBarDetail);
        textViewFollowersDetail = findViewById(R.id.textViewFollowersDetail);
        textViewFollowingDetail = findViewById(R.id.textViewFollowingDetail);
        textViewRepositoryDetail = findViewById(R.id.textViewRepositoryDetail);
        textViewNoDataDetail = findViewById(R.id.textViewNoDataDetail);
        nestedScrollViewDetail = findViewById(R.id.nestedScrollViewDetail);
        tabLayoutDetail = findViewById(R.id.tabLayoutDetail);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFailedDetail(String Message) {
        onDataFound(false);
    }

    @Override
    public void onSuccessDetail(DetailModel detailModel) {
        onDataFound(true);
        basePreferences.setFollowers(detailModel.getFollowers());
        basePreferences.setFollowing(detailModel.getFollowing());
        basePreferences.setAvatar_url(detailModel.getAvatar_url());
        basePreferences.setLogin(detailModel.getLogin());

        if (detailModel.getName() == null) {
            textViewNameDetail.setText(userName);
        } else {
            setName(detailModel.getName());
            textViewNameDetail.setText(detailModel.getName());
        }

        if (detailModel.getLocation() == null) {
            textViewLocationDetail.setText("-");
        } else {
            textViewLocationDetail.setText(detailModel.getLocation());
        }

        if (detailModel.getCompany() == null) {
            textViewCompanyDetail.setText("-");
        } else {
            textViewCompanyDetail.setText(detailModel.getCompany());
        }

        textViewFollowersDetail.setText(String.valueOf(detailModel.getFollowers()));
        textViewFollowingDetail.setText(String.valueOf(detailModel.getFollowing()));
        textViewRepositoryDetail.setText(String.valueOf(detailModel.getPublic_repos()));

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.error(R.drawable.image_not_found);
        requestOptions.centerCrop();
        requestOptions.priority(Priority.HIGH);

        Glide.with(getBaseContext())
                .load(String.valueOf(detailModel.getAvatar_url()))
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBarDetail.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBarDetail.setVisibility(View.GONE);
                        return false;
                    }
                })
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageViewDetail);
    }

    private void getDetail(String username) {
        try {
            presenter.getDetail(username);
        } catch (NullPointerException e) {
            e.getMessage();
        }
    }

    private void onDataFound(boolean found) {
        if (found) {
            nestedScrollViewDetail.setVisibility(View.VISIBLE);
            textViewNoDataDetail.setVisibility(View.GONE);

        } else {
            nestedScrollViewDetail.setVisibility(View.GONE);
            textViewNoDataDetail.setVisibility(View.VISIBLE);
        }
    }
}
