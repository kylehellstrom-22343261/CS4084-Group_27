package com.example.unieats.controller;

import com.example.unieats.R;
import com.example.unieats.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantController {

    public List<Restaurant> getRestaurants() {
        List<Restaurant> list = new ArrayList<>();
        list.add(new Restaurant("Tasty Bites", "Delicious homemade food", 4.5, R.drawable.scholars_club));
        list.add(new Restaurant("Ocean View", "Seafood with a view", 4.7, R.drawable.scholars_club));

        return list;
    }
}
