package com.example.unieats.view.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.unieats.R;
import com.example.unieats.model.Restaurant;
import com.example.unieats.view.adapter.RestaurantAdapter;

import java.util.List;

public class FavouritesFragment extends Fragment {

    private RecyclerView recyclerView;
    private RestaurantAdapter adapter;
    private List<Restaurant> favouriteRestaurants;

    public FavouritesFragment(List<Restaurant> favouriteRestaurants) {
        this.favouriteRestaurants = favouriteRestaurants;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_favourites, container, false);
        recyclerView = view.findViewById(R.id.favourites_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new RestaurantAdapter(favouriteRestaurants, restaurant -> {
            // handle restaurant click if needed
        });
        recyclerView.setAdapter(adapter);

        return view;
    }
}
