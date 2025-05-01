package com.example.unieats.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unieats.R;
import com.example.unieats.model.Menu;
import com.example.unieats.model.Order;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private final List<Order> orderList;

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);

        holder.orderIdTextView.setText("Order ID: " + order.getOrderNumber());

        StringBuilder details = new StringBuilder();
        double totalPrice = 0.0;

        for (Menu.MenuItem item : order.getMenuItems()) {
            double itemTotal = item.getPrice() * item.getCount();
            details.append(item.getName())
                    .append(" x")
                    .append(item.getCount())
                    .append(" @ €")
                    .append(String.format("%.2f", item.getPrice()))
                    .append(" = €")
                    .append(String.format("%.2f", itemTotal))
                    .append("\n");

            totalPrice += itemTotal;
        }

        holder.orderDetailsTextView.setText(details.toString().trim());
        holder.orderTotalTextView.setText("Total: €" + String.format("%.2f", totalPrice));
        holder.orderTimeTextView.setText(order.getOrderTime().toString());
        holder.orderStatusTextView.setText(order.isPending() ? "Pending" : "Complete");
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderIdTextView, orderDetailsTextView, orderTotalTextView, orderTimeTextView, orderStatusTextView;
        Button markDoneButton;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdTextView = itemView.findViewById(R.id.order_id);
            orderDetailsTextView = itemView.findViewById(R.id.order_details);
            orderTotalTextView = itemView.findViewById(R.id.order_total);
            orderTimeTextView = itemView.findViewById(R.id.order_time);
            orderStatusTextView = itemView.findViewById(R.id.statusTextView);
            markDoneButton = itemView.findViewById(R.id.mark_done_button);
        }
    }
}
