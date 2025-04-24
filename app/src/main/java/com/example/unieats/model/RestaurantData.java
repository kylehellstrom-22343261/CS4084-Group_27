package com.example.unieats.model;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
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

            if(task.isSuccessful()) {
                GenericTypeIndicator<HashMap<String, Restaurant>> t = new GenericTypeIndicator<>() {};

                HashMap<String, Restaurant> m = task.getResult().getValue(t);

                if (m != null) {
                    for (Map.Entry<String, Restaurant> e : m.entrySet()) {
                        result.add(e.getValue());
                    }
                }

                callback.onRestaurantsLoaded(result);
            } else {
                System.out.println("Error getting restaurants: " + task.getException());
            }
        });
    }
}
