package com.example.unieats.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unieats.R;
import com.example.unieats.model.Order;

import java.util.List;

public class PendingOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Order> orders;
    private final Context context;

    public PendingOrderAdapter(Context context, List<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pending_orders, parent, false);
        return new PendingOrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PendingOrdersViewHolder) {
            Order order = orders.get(position);
            PendingOrdersViewHolder vh = (PendingOrdersViewHolder) holder;
            vh.businessName.setText(order.getBusinessName() != null ? order.getBusinessName() : "N/A");
            vh.timeOfOrder.setText(order.getOrderTime() != null ? order.getOrderTime().toString() : "N/A");
            vh.collectionTime.setText(order.getCollectionTime() != null ? order.getCollectionTime().toString() : "N/A");
            vh.orderId.setText(String.valueOf(order.getOrderNumber()));
            vh.navButton.setOnClickListener(v -> {
                // Handle navigation button click
                Intent intent = new Intent(context, PendingOrderItemsActivity.class);
                // Pass any necessary data to MapsActivity, e.g., order details or location
                intent.putExtra("businessName", order.getBusinessName()); // Example
                intent.putExtra("orderId", order.getOrderNumber());//example
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }


    static class PendingOrdersViewHolder extends RecyclerView.ViewHolder {
        Button navButton;
        TextView businessName, timeOfOrder, collectionTime, orderId;

        public PendingOrdersViewHolder(View view) {
            super(view);
            navButton = view.findViewById(R.id.nav_button);
            businessName = view.findViewById(R.id.business_name);
            timeOfOrder = view.findViewById(R.id.time_of_order);
            collectionTime = view.findViewById(R.id.collection_time);
            orderId = view.findViewById(R.id.order_id);

        }
    }
}
