package com.example.unieats.view.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.unieats.R;
import com.example.unieats.model.Restaurant;
import com.example.unieats.model.RestaurantData;
import com.example.unieats.view.adapter.RestaurantAdapter;

import java.util.List;

public class RestaurantFragment extends Fragment {

    public RestaurantFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.restaurant_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        RestaurantData restaurantData = new RestaurantData();
        restaurantData.getRestaurants(new RestaurantData.RestaurantCallback() {
            @Override
            public void onRestaurantsLoaded(List<Restaurant> restaurants) {
                RestaurantAdapter adapter = new RestaurantAdapter(restaurants, restaurant -> {
                    // On restaurant click, open MenuFragment
                    Fragment menuFragment = MenuFragment.newInstance(restaurant.getBusinessName());

                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, menuFragment)
                            .addToBackStack(null)
                            .commit();
                });

                recyclerView.setAdapter(adapter);
            }
        });

        return view;
    }

}
