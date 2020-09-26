package com.example.sub2_rahmatsaputra.views.detail.following;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sub2_rahmatsaputra.R;
import com.example.sub2_rahmatsaputra.adapter.FollowingAdapter;
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
public class FollowingActivity extends Fragment implements FollowingContract.View {

    private BasePreferences basePreferences;
    private String userName;
    private FollowingContract.Presenter presenter;
    private RecyclerView recyclerViewFollowing;
    private TextView textViewNoDataFollowing;
    private NestedScrollView nestedScrollViewFollowing;
    private FollowingAdapter followingAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new FollowingPresenter(this, getContext());
        basePreferences = new BasePreferences(getContext());
        userName = basePreferences.getUserName();
        getFollowing(userName);

        recyclerViewFollowing = view.findViewById(R.id.recyclerViewFollowing);
        textViewNoDataFollowing = view.findViewById(R.id.textViewNoDataFollowing);
        nestedScrollViewFollowing = view.findViewById(R.id.nestedScrollViewFollowing);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false);
    }

    private void onDataFound(boolean found) {
        if (found) {
            if (basePreferences.getFollowing() == 0) {
                nestedScrollViewFollowing.setVisibility(View.GONE);
                textViewNoDataFollowing.setVisibility(View.VISIBLE);
            } else {
                nestedScrollViewFollowing.setVisibility(View.VISIBLE);
                textViewNoDataFollowing.setVisibility(View.GONE);
            }
        } else {
            nestedScrollViewFollowing.setVisibility(View.GONE);
            textViewNoDataFollowing.setVisibility(View.VISIBLE);
        }
    }

    private void getFollowing(String username) {
        presenter.getFollowing(username);
    }

    @Override
    public void onFailedFollowing(String Message) {
        onDataFound(false);
    }

    @Override
    public void onSuccessFollowing(ArrayList<FollowModel> followModel) {
        onDataFound(true);
        followingAdapter = new FollowingAdapter(getContext(), followModel);
        recyclerViewFollowing.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewFollowing.setAdapter(followingAdapter);
    }

    @Override
    public void onLoading(boolean isLoading) {

    }
}
