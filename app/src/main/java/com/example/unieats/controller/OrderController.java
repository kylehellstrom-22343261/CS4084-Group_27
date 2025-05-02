package com.example.unieats.controller;

import com.example.unieats.model.Menu;
import com.example.unieats.model.Order;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class
OrderController {
    public interface OrderCallback {
        void onOrdersLoaded(List<Order> orders);
    }

    public void makeOrder(Order order) {
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://unieats-57c3e-default-rtdb.europe-west1.firebasedatabase.app/");

        DatabaseReference dbRef = db.getReference("Order/data");

        dbRef.push().setValue(order);
    }

    public static void getOrders(OrderCallback callback) {
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://unieats-57c3e-default-rtdb.europe-west1.firebasedatabase.app/");

        DatabaseReference dbRef = db.getReference("Order/data");

        dbRef.get().addOnCompleteListener(task -> {
            List<Order> result = new ArrayList<>();

            if (task.isSuccessful()) {
                GenericTypeIndicator<HashMap<String, Order>> typeIndicator = new GenericTypeIndicator<HashMap<String, Order>>() {
                };

                HashMap<String, Order> firebaseResult = task.getResult().getValue(typeIndicator);

                if (firebaseResult != null) {
                    for (Map.Entry<String, Order> e : firebaseResult.entrySet()) {
                        result.add(e.getValue());
                    }
                }

                callback.onOrdersLoaded(result);
            }
        });
    }

    public static void getOrders(String businessName, OrderCallback callback) {
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://unieats-57c3e-default-rtdb.europe-west1.firebasedatabase.app/");

        DatabaseReference dbRef = db.getReference("Order/data");

        dbRef.orderByChild("businessName").equalTo(businessName).get().addOnCompleteListener(task -> {
            List<Order> result = new ArrayList<>();

            if (task.isSuccessful()) {
                GenericTypeIndicator<HashMap<String, Order>> typeIndicator = new GenericTypeIndicator<HashMap<String, Order>>() {
                };

                HashMap<String, Order> firebaseResult = task.getResult().getValue(typeIndicator);

                if (firebaseResult != null) {
                    for (Map.Entry<String, Order> e : firebaseResult.entrySet()) {
                        result.add(e.getValue());
                    }
                }

                callback.onOrdersLoaded(result);
            }
        });
    }

    public static void getPendingOrders(OrderCallback callback) {
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://unieats-57c3e-default-rtdb.europe-west1.firebasedatabase.app/");

        DatabaseReference dbRef = db.getReference("Order/data");

        Query query = dbRef.orderByChild("pending").equalTo(true);

        query.get().addOnCompleteListener(task -> {
            List<Order> result = new ArrayList<>();

            if (task.isSuccessful()) {
                GenericTypeIndicator<HashMap<String, Order>> typeIndicator = new GenericTypeIndicator<HashMap<String, Order>>() {
                };

                HashMap<String, Order> firebaseResult = task.getResult().getValue(typeIndicator);

                if (firebaseResult != null) {
                    for (Map.Entry<String, Order> e : firebaseResult.entrySet()) {
                        result.add(e.getValue());
                    }
                }

                callback.onOrdersLoaded(result);
            }
        });
    }
}
