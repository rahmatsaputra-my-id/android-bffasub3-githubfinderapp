package com.example.sub2_rahmatsaputra.views.detail.followers;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sub2_rahmatsaputra.R;
import com.example.sub2_rahmatsaputra.adapter.FollowersAdapter;
import com.example.sub2_rahmatsaputra.base.BasePreferences;
import com.example.sub2_rahmatsaputra.models.follow.FollowModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowersActivity extends Fragment implements FollowersContract.View {

    private BasePreferences basePreferences;
    String userName;
    private FollowersContract.Presenter presenter;
    private RecyclerView recyclerViewFollowers;
    private TextView textViewNoDataFollowers;
    private NestedScrollView nestedScrollViewFollowers;
    private FollowersAdapter followersAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new FollowersPresenter(this, getContext());
        basePreferences = new BasePreferences(getContext());
        userName = basePreferences.getUserName();
        getFollowers(userName);

        recyclerViewFollowers = view.findViewById(R.id.recyclerViewFollowers);
        textViewNoDataFollowers = view.findViewById(R.id.textViewNoDataFollowers);
        nestedScrollViewFollowers = view.findViewById(R.id.nestedScrollViewFollowers);
    }

    @Override
    public void onFailedFollowers(String Message) {
        onDataFound(false);
    }

    @Override
    public void onSuccessFollowers(ArrayList<FollowModel> followModel) {
        onDataFound(true);
        followersAdapter = new FollowersAdapter(getContext(), followModel);
        recyclerViewFollowers.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewFollowers.setAdapter(followersAdapter);
    }

    private void onDataFound(boolean found) {
        if (found) {
            if (basePreferences.getFollowers() == 0) {
                nestedScrollViewFollowers.setVisibility(View.GONE);
                textViewNoDataFollowers.setVisibility(View.VISIBLE);
            } else {
                nestedScrollViewFollowers.setVisibility(View.VISIBLE);
                textViewNoDataFollowers.setVisibility(View.GONE);
            }
        } else {
            nestedScrollViewFollowers.setVisibility(View.GONE);
            textViewNoDataFollowers.setVisibility(View.VISIBLE);
        }
    }

    private void getFollowers(String username) {
        presenter.getFollowers(username);
    }

    @Override
    public void onLoading(boolean isLoading) {

    }
}
