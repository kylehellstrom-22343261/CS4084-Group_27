package com.example.unieats.view.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unieats.R;
import com.example.unieats.controller.OrderController;
import com.example.unieats.model.Menu;
import com.example.unieats.model.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private final List<Order> orderList;
    private final List<Order> completedOrderList = new ArrayList<>();
    private final OnOrderStatusUpdatedListener orderStatusUpdatedListener;

    public interface OnOrderStatusUpdatedListener {
        void onOrderStatusUpdated(List<Order> updatedOrders);
    }

    public OrderAdapter(List<Order> orderList, OnOrderStatusUpdatedListener listener) {
        this.orderList = orderList;
        this.orderStatusUpdatedListener = listener;
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
        holder.orderTimeTextView.setText(order.getOrderTime());
        holder.orderStatusTextView.setText(order.isPending() ? "Pending" : "Complete");

        // Set button color
        holder.markDoneButton.setBackgroundColor(Color.parseColor("#4CAF50")); // green
        holder.markDoneButton.setTextColor(Color.WHITE);

        // Button click logic
        holder.markDoneButton.setOnClickListener(v -> {
            FirebaseDatabase db = FirebaseDatabase.getInstance("https://unieats-57c3e-default-rtdb.europe-west1.firebasedatabase.app/");
            DatabaseReference dbRef = db.getReference("Order/data");

            dbRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult() != null) {
                    for (DataSnapshot snapshot : task.getResult().getChildren()) {
                        Order o = snapshot.getValue(Order.class);
                        if (o != null && o.getOrderNumber() == order.getOrderNumber()) {
                            // Update Firebase order status to done (pending = false)
                            snapshot.getRef().child("pending").setValue(false);

                            // After successful Firebase update, remove from adapter list
                            int currentPosition = holder.getAdapterPosition();
                            if (currentPosition != RecyclerView.NO_POSITION) {
                                orderList.remove(currentPosition);
                                notifyItemRemoved(currentPosition);
                                notifyItemRangeChanged(currentPosition, orderList.size());
                            }

                            // Fetch updated orders after marking as done
                            OrderController.getOrders(order.getBusinessName(), orders -> {
                                completedOrderList.clear(); // Clear the previous list
                                completedOrderList.addAll(orders); // Add the updated orders
                                // Notify the listener to update the UI
                                if (orderStatusUpdatedListener != null) {
                                    orderStatusUpdatedListener.onOrderStatusUpdated(completedOrderList);
                                }
                            });

                            break;
                        }
                    }
                }
            });
        });
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

