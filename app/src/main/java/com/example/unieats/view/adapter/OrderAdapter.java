package com.example.unieats.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unieats.R;
import com.example.unieats.model.Order;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orderList;

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        if(order != null) {
            holder.orderIdTextView.setText("Order ID: " + order.getOrderNumber());
            holder.orderDetailsTextView.setText(order.getMenuItems().toString());
            holder.orderTimeTextView.setText(order.getOrderTime().toString());
            holder.orderStatusTextView.setText(order.isPending() ? "Pending" : "Complete");
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderIdTextView, orderDetailsTextView, orderTimeTextView, orderStatusTextView;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdTextView = itemView.findViewById(R.id.order_id);
            orderDetailsTextView = itemView.findViewById(R.id.order_details);
            orderTimeTextView = itemView.findViewById(R.id.order_time);
            orderStatusTextView = itemView.findViewById(R.id.statusTextView);
        }
    }
}
