package com.example.unieats.model;

import com.example.unieats.controller.MenuController;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantData {
    public interface RestaurantCallback {
        void onRestaurantsLoaded(List<Restaurant> restaurants);
    }

    public void getRestaurants(RestaurantCallback callback) {
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

    public interface MenuCallback {
        void onMenuLoaded(List<Menu.MenuItem> menu);
    }

    public void getMenu(String restaurant, RestaurantData.MenuCallback callback) {
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://unieats-57c3e-default-rtdb.europe-west1.firebasedatabase.app/");

        DatabaseReference dbRef = db.getReference("Item/data");

        dbRef.orderByChild("businessName").equalTo(restaurant).get().addOnCompleteListener(task -> {
            List<Menu.MenuItem> result = new ArrayList<>();

            if(task.isSuccessful()) {
                GenericTypeIndicator<HashMap<String, Menu.MenuItem>> typeIndicator = new GenericTypeIndicator<>() {};

                HashMap<String, Menu.MenuItem> firebaseResult = task.getResult().getValue(typeIndicator);

                if(firebaseResult != null) {
                    for(Map.Entry<String, Menu.MenuItem> e : firebaseResult.entrySet()) {
                        result.add(e.getValue());
                    }
                }

                callback.onMenuLoaded(result);
            } else {
                System.out.println("Error getting restaurants: " + task.getException());
            }
        });
    }
}
