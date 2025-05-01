package com.example.unieats.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unieats.R;
import com.example.unieats.controller.OrderController;
import com.example.unieats.model.Order;
import com.example.unieats.view.adapter.PendingOrderAdapter;

import java.util.List;

public class PendingOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decor = getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_pending_order);

        RecyclerView recyclerView = findViewById(R.id.pending_order_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Find the back button by ID
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            // End the PendingOrderActivity
            finish();
        });

        OrderController.getPendingOrders(orders -> {
            PendingOrderAdapter adapter = new PendingOrderAdapter(this, orders);
            recyclerView.setAdapter(adapter);
        });
    }
}