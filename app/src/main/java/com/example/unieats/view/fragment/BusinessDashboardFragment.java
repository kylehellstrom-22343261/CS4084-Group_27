package com.example.unieats.view.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.unieats.R;
import com.example.unieats.controller.OrderController;
import com.example.unieats.model.Order;
import com.example.unieats.view.adapter.OrderAdapter;
//import com.example.unieats.view.adapter.OrderAdapter;

import java.util.ArrayList;
import java.util.List;

public class BusinessDashboardFragment extends Fragment {

    private RecyclerView recentOrdersRecyclerView;

//    private OrderAdapter orderAdapter;

    private List<Order> orderList;

    public BusinessDashboardFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_business_dashboard, container, false);

        recentOrdersRecyclerView = view.findViewById(R.id.recentOrdersRecyclerView);
        recentOrdersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        orderList = new ArrayList<>();

        OrderController.getOrders(orders -> {
            OrderAdapter orderAdapter = new OrderAdapter(orders);
            recentOrdersRecyclerView.setAdapter(orderAdapter);
        });


//        orderAdapter = new OrderAdapter(orderList);
//        recentOrdersRecyclerView.setAdapter(orderAdapter);

        return view;
    }
}
