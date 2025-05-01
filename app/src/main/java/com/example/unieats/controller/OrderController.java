package com.example.unieats.controller;

import com.example.unieats.model.Menu;
import com.example.unieats.model.Order;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

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

    public void getOrders(OrderCallback callback) {
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

    public List<Order> getPendingOrders() {
        List<Order> result = new ArrayList<>();

        getOrders(orders -> {
            for (Order o : orders) {
                if (o.isPending()) {
                    result.add(o);
                }
            }
        });

        return result;
    }
}
