package com.example.unieats.view.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.unieats.R;
import com.example.unieats.controller.BusinessUserController;
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

    private String email;

    public BusinessDashboardFragment(String email) {
        this.email = email;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_business_dashboard, container, false);

        recentOrdersRecyclerView = view.findViewById(R.id.recentOrdersRecyclerView);
        recentOrdersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        orderList = new ArrayList<>();

//        email = "stablesclub@ul.ie";

//        Toast.makeText(getContext(), email, Toast.LENGTH_SHORT).show();
        BusinessUserController businessUserController = new BusinessUserController();

        businessUserController.getBusinessUsers(email, businessUsers -> {
//            Toast.makeText(getContext(), businessUsers.get(0).getBusinessName(), Toast.LENGTH_SHORT).show();

            OrderController.getOrders(businessUsers.get(0).getBusinessName(), orders -> {
                ArrayList<Order> pendingOrders = new ArrayList<>();
                for(Order o : orders) {
                    if(o.isPending()) {
                        pendingOrders.add(o);
                    }
                }
//                Toast.makeText(getContext(), pendingOrders.get(0).getMenuItems().get(0).getName(), Toast.LENGTH_SHORT).show();
                OrderAdapter orderAdapter = new OrderAdapter(pendingOrders);
                recentOrdersRecyclerView.setAdapter(orderAdapter);
            });
        });

//        orderAdapter = new OrderAdapter(orderList);
//        recentOrdersRecyclerView.setAdapter(orderAdapter);

        return view;
    }
}
