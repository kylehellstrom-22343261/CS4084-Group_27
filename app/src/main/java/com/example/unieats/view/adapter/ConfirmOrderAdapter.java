package com.example.unieats.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unieats.R;
import com.example.unieats.model.Menu;

import java.util.List;

public class ConfirmOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ORDER = 1;
    private static final String HEADER_TEXT = "Confirm Your Order";
    private final List<Menu.MenuItem> basketItems;
    private final Context context;

    public ConfirmOrderAdapter(List<Menu.MenuItem> basketItems, Context context) {
        this.basketItems = basketItems;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        // Return the header type for position 0, otherwise return the menu item type
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_ORDER;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header, parent, false);
            return new OrderHeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_confirm_order, parent, false);
            return new ConfirmOrderViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OrderHeaderViewHolder) {
            ((OrderHeaderViewHolder) holder).headerTitle.setText(HEADER_TEXT);
        } else {
            // Only show items with count > 0
            Menu.MenuItem menuItem = basketItems.get(position -1);

            ((ConfirmOrderViewHolder) holder).name.setText(menuItem.getName());
            ((ConfirmOrderViewHolder) holder).price.setText(String.format("€%.2f", menuItem.getPrice()));
            ((ConfirmOrderViewHolder) holder).quantity.setText("" + menuItem.getCount());
        }


    }

    @Override
    public int getItemCount() {
        return 1 + basketItems.size(); // 1 for the header and the rest for items in the basket
    }

    static class OrderHeaderViewHolder extends RecyclerView.ViewHolder {
        TextView headerTitle;

        public OrderHeaderViewHolder(View view) {
            super(view);
            headerTitle = view.findViewById(R.id.header_title);
        }
    }

    public static class ConfirmOrderViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, quantity;

        public ConfirmOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.confirm_order_item_name);
            quantity = itemView.findViewById(R.id.confirm_order_item_quantity);
            price = itemView.findViewById(R.id.confirm_order_item_price);
        }
    }
}
