package com.example.unieats.controller;

import android.content.Context;

import com.example.unieats.model.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class
OrderController {
    private static final String localPendingOrdersFile = "pending_orders.dsv";

    public interface OrderCallback {
        void onOrdersLoaded(List<Order> orders);
    }

    public void makeOrder(Context context, Order order) {
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://unieats-57c3e-default-rtdb.europe-west1.firebasedatabase.app/");

        DatabaseReference dbRef = db.getReference("Order/data");

        dbRef.push().setValue(order);

        writeLocalPendingOrders(context, order.getOrderNumber());
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

    public static void getCompletedOrders(OrderCallback callback) {
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://unieats-57c3e-default-rtdb.europe-west1.firebasedatabase.app/");

        DatabaseReference dbRef = db.getReference("Order/data");

        Query query = dbRef.orderByChild("pending").equalTo(false);

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

    public static void updateOrderStatus(String orderNumber, boolean isPending) {
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://unieats-57c3e-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference dbRef = db.getReference("Order/data");

        dbRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                // Convert the orderNumber (String) to an int
                int targetOrderNumber = Integer.parseInt(orderNumber);

                for (DataSnapshot snapshot : task.getResult().getChildren()) {
                    Order order = snapshot.getValue(Order.class);
                    if (order != null && order.getOrderNumber() == targetOrderNumber) {
                        snapshot.getRef().child("pending").setValue(isPending);
                        break;
                    }
                }
            }
        });
    }

    public static void writeLocalPendingOrders(Context context, int orderNumber) {
        ArrayList<Integer> currentPendingOrders = readLocalPendingOrders(context);

        String outputString = "";

        for (int i : currentPendingOrders) {
            outputString += i + "\n";
        }

        try (FileOutputStream fos = context.openFileOutput(localPendingOrdersFile, Context.MODE_PRIVATE)) {
            if (!currentPendingOrders.contains(orderNumber)) {
                outputString += orderNumber + "\n";
            }

            fos.write(outputString.getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeLocalPendingOrder(Context context, Integer orderNumber) {
        ArrayList<Integer> currentPendingOrders = readLocalPendingOrders(context);
        currentPendingOrders.remove(orderNumber);

        String outputString = "";

        for (int i : currentPendingOrders) {
            outputString += i + "\n";
        }

        try (FileOutputStream fos = context.openFileOutput(localPendingOrdersFile, Context.MODE_PRIVATE)) {
            if (!currentPendingOrders.contains(orderNumber)) {
                outputString += orderNumber + "\n";
            }

            fos.write(outputString.getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Integer> readLocalPendingOrders(Context context) {
        ArrayList<Integer> result = new ArrayList<>();

        try (FileInputStream fis = context.openFileInput(localPendingOrdersFile)) {
            InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);

            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String line = reader.readLine();

                while(line != null) {
                    result.add(Integer.parseInt(line));
                    line = reader.readLine();
                }
            }
        } catch (FileNotFoundException e) {
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
