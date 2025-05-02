package com.example.unieats.view.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.unieats.R;
import com.example.unieats.controller.BusinessUserController;
import com.example.unieats.controller.OrderController;
import com.example.unieats.model.Order;
import com.example.unieats.view.adapter.OrderAdapter;
import com.example.unieats.model.Menu;

import java.util.ArrayList;
import java.util.List;

public class BusinessDashboardFragment extends Fragment {

    private RecyclerView recentOrdersRecyclerView;
    private List<Order> orderList;
    private OrderAdapter orderAdapter;
    private String email;

    private TextView totalOrdersTextView;
    private TextView revenueTextView;

    public BusinessDashboardFragment(String email) {
        this.email = email;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_business_dashboard, container, false);

        recentOrdersRecyclerView = view.findViewById(R.id.recentOrdersRecyclerView);
        recentOrdersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        totalOrdersTextView = view.findViewById(R.id.totalOrders);
        revenueTextView = view.findViewById(R.id.revenue);

        orderList = new ArrayList<>();

        // Initialize OrderAdapter here
        orderAdapter = new OrderAdapter(orderList, updatedOrders -> {
            // Update the order list and notify the adapter
            orderList.clear();
            orderList.addAll(updatedOrders);
            orderAdapter.notifyDataSetChanged();  // Notify the adapter after updating the data

            // Update total orders and revenue
            //updateTotalOrdersAndRevenue(updatedOrders, totalOrdersTextView, revenueTextView);
        });

        if (email != null && !email.isEmpty()) {
            BusinessUserController businessUserController = new BusinessUserController();

            businessUserController.getBusinessUsers(email, businessUsers -> {
                if (!businessUsers.isEmpty()) {
                    OrderController.getOrders(businessUsers.get(0).getBusinessName(), orders -> {
                        // Fetch all orders (pending and completed)
                        ArrayList<Order> pendingOrders = new ArrayList<>();
                        ArrayList<Order> completedOrders = new ArrayList<>();
                        for(Order o : orders) {
                            if (o.isPending()) {
                                pendingOrders.add(o);
                            } else completedOrders.add(o);
                        }
                        updateTotalOrdersAndRevenue(completedOrders, totalOrdersTextView, revenueTextView);



//Set the adapter for the RecyclerView after fetching data
                        orderAdapter = new OrderAdapter(pendingOrders, updatedOrders -> {
  //                           Update the order list and notify the adapter
                            orderList.clear();
                            orderList.addAll(updatedOrders);
                            // Recalculate completed orders based on the latest state
                            ArrayList<Order> updatedCompletedOrders = new ArrayList<>();
                            for (Order o : orderList) {
                                if (!o.isPending()) {
                                    updatedCompletedOrders.add(o);
                                }
                            }
                            updateTotalOrdersAndRevenue(updatedCompletedOrders, totalOrdersTextView, revenueTextView);

                           // updateTotalOrdersAndRevenue(completedOrders, totalOrdersTextView, revenueTextView);
                            orderAdapter.notifyDataSetChanged();  // Notify the adapter after updating the data
                        });

                        // Set the adapter for the RecyclerView
                        recentOrdersRecyclerView.setAdapter(orderAdapter);
                    });
                } else {
                    Toast.makeText(getContext(), "Business not found", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(), "Invalid email", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    // Method to calculate total orders and revenue
    private void updateTotalOrdersAndRevenue(List<Order> orders,TextView totalOrdersTextView, TextView revenueTextView) {
        try {
            int totalOrders = orders.size();
            Log.d("BusinessDashboardFragment", "Total Orders: " + totalOrders);
            double totalRevenue = 0.0;

            for (Order order : orders) {
                double orderTotal = 0.0;
                for (Menu.MenuItem menuItem : order.getMenuItems()) {
                    orderTotal += menuItem.getPrice() * menuItem.getCount();
                }
                totalRevenue += orderTotal;
            }
            Log.d("BusinessDashboardFragment", "Total Revenue: " + totalRevenue);

            // Update the TextViews with the calculated values
            totalOrdersTextView.setText(String.valueOf(totalOrders));
            revenueTextView.setText(String.format("%.2f", totalRevenue));
        } catch (Exception e) {
            Log.e("BusinessDashboardFragment", "Error calculating total orders and revenue", e);
            totalOrdersTextView.setText("oops");
            revenueTextView.setText("oops");
            //throw new RuntimeException(e);
        }
    }
}
