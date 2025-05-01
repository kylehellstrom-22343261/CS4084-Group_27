package com.example.unieats.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unieats.R;
import com.example.unieats.view.activity.AboutUsActivity;
import com.example.unieats.view.activity.BusinessLoginActivity;

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
            vh.businessName.setText(order.getBusinessName());
            vh.timeOfOrder.setText(order.getOrderTime().toString());
            vh.collectionTime.setText(order.getCollectionTime());
            vh.orderId.setText(order.getOrderNumber());
            vh.navButton.setOnClickListener(v -> {
                // Handle navigation button click

            });
        }
    };

    @Override
    public int getItemCount() {
        return orders.size();
    }


    static class PendingOrdersViewHolder extends RecyclerView.ViewHolder {
        TextView businessName, timeOfOrder, collectionTime, orderId;
        Button navButton;

        public PendingOrdersViewHolder(View view) {
            super(view);
            businessName = view.findViewById(R.id.business_name);
            timeOfOrder = view.findViewById(R.id.time_of_order);
            collectionTime = view.findViewById(R.id.collection_time);
            orderId = view.findViewById(R.id.order_id);
            navButton = view.findViewById(R.id.nav_button);

        }
    }
}
