package com.example.unieats.controller;

import com.example.unieats.model.Restaurant;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantController {
    public interface RestaurantCallback {
        void onRestaurantsLoaded(List<Restaurant> restaurants);
    }

    public static void getRestaurants(RestaurantCallback callback) {
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://unieats-57c3e-default-rtdb.europe-west1.firebasedatabase.app/");

        DatabaseReference dbRef = db.getReference("Restaurant/data");

        dbRef.get().addOnCompleteListener(task -> {
            List<Restaurant> result = new ArrayList<>();

            if (task.isSuccessful()) {
                GenericTypeIndicator<HashMap<String, Restaurant>> typeIndicator = new GenericTypeIndicator<>() {};

                HashMap<String, Restaurant> firebaseResult = task.getResult().getValue(typeIndicator);

                if (firebaseResult != null) {
                    for (Map.Entry<String, Restaurant> e : firebaseResult.entrySet()) {
                        if(e.getValue() != null){
                            result.add(e.getValue());
                        }
                    }
                }

                callback.onRestaurantsLoaded(result);
            } else {
                System.out.println("Error getting restaurants: " + task.getException());
            }
        });
    }
}