package com.example.unieats.view.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.unieats.R;
import com.example.unieats.controller.BusinessUserController;
import com.example.unieats.controller.OrderController;
import com.example.unieats.model.Menu;
import com.example.unieats.model.Order;
import com.example.unieats.view.adapter.OrderAdapter;
//import com.example.unieats.view.adapter.OrderAdapter;

import java.util.ArrayList;
import java.util.List;

public class BusinessDashboardFragment extends Fragment implements OrderAdapter.OnOrderStatusUpdatedListener {

    private RecyclerView recentOrdersRecyclerView;
    private TextView totalOrdersTextView, revenueTextView;
    private List<Order> orderList;
    private List<Order> completedOrderList = new ArrayList<>();
    private String email;

    public BusinessDashboardFragment(String email) {
        this.email = email;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_business_dashboard, container, false);
        totalOrdersTextView = view.findViewById(R.id.totalOrders);
        revenueTextView = view.findViewById(R.id.revenue);

        // Initialize orders and fetch only once
        orderList = new ArrayList<>();

        BusinessUserController businessUserController = new BusinessUserController();
        businessUserController.getBusinessUsers(email, businessUsers -> {
            OrderController.getOrders(businessUsers.get(0).getBusinessName(), orders -> {
                orderList = orders;  // Store orders locally

                updateOrderSummary(orderList);

                // Set the adapter and pass the listener
                OrderAdapter orderAdapter = new OrderAdapter(orderList, this);
                recentOrdersRecyclerView.setAdapter(orderAdapter);
            });
        });

        // Set RecyclerView
        recentOrdersRecyclerView = view.findViewById(R.id.recentOrdersRecyclerView);
        recentOrdersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    // Method to update total orders and revenue
    private void updateOrderSummary(List<Order> orders) {
        int totalOrders = orders.size();
        totalOrdersTextView.setText(String.valueOf(totalOrders));

        double totalRevenue = 0.0;
        for (Order o : orders) {
            for (Menu.MenuItem item : o.getMenuItems()) {
                totalRevenue += item.getPrice() * item.getCount();
            }
        }
        revenueTextView.setText(String.format("%.2f", totalRevenue));
    }

    // Method called from OrderAdapter to update status
    @Override
    public void onOrderStatusUpdated(List<Order> updatedOrders) {
        // Update total orders and revenue after a change in the order list
        completedOrderList = updatedOrders;
        updateOrderSummary(orderList);
    }
}
