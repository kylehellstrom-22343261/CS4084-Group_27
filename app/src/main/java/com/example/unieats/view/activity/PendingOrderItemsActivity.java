package com.example.unieats.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unieats.R;
import com.example.unieats.controller.OrderController;
import com.example.unieats.model.Menu;
import com.example.unieats.model.Order;
import com.example.unieats.view.adapter.PendingOrderAdapter;
import com.example.unieats.view.adapter.PendingOrderItemsAdapter;

import java.util.ArrayList;
import java.util.List;

public class PendingOrderItemsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decor = getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_pending_order_items);

        RecyclerView recyclerView = findViewById(R.id.pending_order_items_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Find the back button by ID
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            // End the PendingOrderActivity
            finish();
        });

        // Retrieve the passed extra order
        List<Menu.MenuItem> items = (List<Menu.MenuItem>) getIntent().getSerializableExtra("menuItems");

        PendingOrderItemsAdapter adapter = new PendingOrderItemsAdapter(this, items);
        recyclerView.setAdapter(adapter);


//        OrderController.getPendingOrders(orders -> {
//            if (orders != null && !orders.isEmpty()) {
//                // Loop through all orders and collect all MenuItems
//                for (Order order : orders) {
//                    items.addAll(order.getMenuItems());
//                }
//                items.addAll(orders.get(1).getMenuItems());
//                Log.d("PendingOrderItemsActivity", "Number of order MenuItems: " + orders.size());
//                Log.d("PendingOrderItemsActivity", "Number of items: " + items.size());
//
//                // Notify the adapter that data has been updated
//                PendingOrderItemsAdapter adapter = new PendingOrderItemsAdapter(this, items, orders.size());
//                recyclerView.setAdapter(adapter);
//            } else {
//                // Handle the case when there are no orders
//                Log.d("PendingOrderItemsActivity", "No orders found or the list is empty.");
//            }
//        });

    }
}