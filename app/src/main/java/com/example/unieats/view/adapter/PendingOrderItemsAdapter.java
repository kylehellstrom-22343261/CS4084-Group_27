package com.example.unieats.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

public class PendingOrderItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Menu.MenuItem> items;
    private final Context context;

    public PendingOrderItemsAdapter(Context context, List<Menu.MenuItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_confirmed, parent, false);
        return new PendingOrderItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PendingOrderItemsViewHolder) {
            Menu.MenuItem allItems = items.get(position);

//            Log.d("PendingOrderItemsAdapter", "Binding item name: " + items.get(0).getName());
//            Log.d("PendingOrderItemsAdapter", "Binding item count: " + items.get(0).getCount());

            PendingOrderItemsViewHolder vh = (PendingOrderItemsViewHolder) holder;
            vh.itemName.setText(allItems.getName());
            vh.itemQuantity.setText(String.valueOf(allItems.getCount()));
            vh.itemPrice.setText(String.valueOf(allItems.getPrice()));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    static class PendingOrderItemsViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemQuantity, itemPrice;

        public PendingOrderItemsViewHolder(View view) {
            super(view);
            itemName = view.findViewById(R.id.confirmed_order_item_name);
            itemQuantity = view.findViewById(R.id.confirmed_order_item_quantity);
            itemPrice = view.findViewById(R.id.confirmed_order_item_price);
        }
    }
}
