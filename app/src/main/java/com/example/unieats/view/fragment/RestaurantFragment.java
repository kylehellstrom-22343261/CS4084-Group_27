package com.example.unieats.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unieats.R;
import com.example.unieats.controller.OrderController;
import com.example.unieats.controller.RestaurantController;
import com.example.unieats.model.Order;
import com.example.unieats.model.Restaurant;
import com.example.unieats.view.activity.PendingOrderActivity;
import com.example.unieats.view.adapter.RestaurantAdapter;

import java.util.ArrayList;
import java.util.List;

public class RestaurantFragment extends Fragment {
    private RestaurantAdapter restaurantAdapter;
    private RecyclerView recyclerView;
    private List<Restaurant> allRestaurants = new ArrayList<>();

    public RestaurantFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant, container, false);

        recyclerView = view.findViewById(R.id.restaurant_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        RestaurantController.getRestaurants(restaurants -> {
            allRestaurants = restaurants;
            restaurantAdapter = new RestaurantAdapter(allRestaurants, restaurant -> {
                // On restaurant click, open MenuFragment
                Fragment menuFragment = MenuFragment.newInstance(restaurant.getBusinessName());

                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, menuFragment).addToBackStack(null).commit();
            });

            recyclerView.setAdapter(restaurantAdapter);
        });

        Button pendingOrderButton = view.findViewById(R.id.pending_order_button);
        pendingOrderButton.setOnClickListener(v -> {
            // Start PendingOrderActivity
            List<Order> orders = OrderController.getPendingOrders();
            OrderController.getOrders(orders1 -> {
                if (orders1.isEmpty()) {
                    // Handle empty or null list
                    Log.d("OrderController", "No pending orders: " + orders1.size());
                    Toast.makeText(requireContext(), "No pending orders", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(requireActivity(), PendingOrderActivity.class);
                    startActivity(intent);
                }
            });

        });


        return view;
    }

    public void filterRestaurants(String query) {
        // Clear previous filtered list
        List<Restaurant> filteredList = new ArrayList<>();

        if (query.isEmpty()) {
            // Show all restaurants when query is empty
            filteredList.addAll(allRestaurants);
        } else {
            // Filter restaurants based on current query
            String lowerCaseQuery = query.toLowerCase();
            for (Restaurant restaurant : allRestaurants) {
                if (restaurant.getBusinessName().toLowerCase().contains(lowerCaseQuery)) {
                    filteredList.add(restaurant);
                }
            }
        }

        restaurantAdapter.updateData(filteredList);
    }


}
