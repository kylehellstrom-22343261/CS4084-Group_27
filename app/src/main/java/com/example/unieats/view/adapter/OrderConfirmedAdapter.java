package com.example.unieats.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unieats.R;
import com.example.unieats.controller.BasketController;
import com.example.unieats.model.Menu;

import java.util.List;

public class OrderConfirmedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ORDER = 1;
    private static final String HEADER_TEXT = "Order Successful";

    private final List<Menu.MenuItem> basketItems;
    private final Context context;

    public OrderConfirmedAdapter(List<Menu.MenuItem> basketItems, Context context) {
        this.basketItems = basketItems;
        this.context = context;
        Log.d("OrderConfirmedAdapter", "count: " + BasketController.getInstance().getBasketItems().size());
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_HEADER : TYPE_ORDER;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == TYPE_HEADER) {
            View view = inflater.inflate(R.layout.item_header, parent, false);
            return new OrderHeaderViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_confirmed, parent, false);
            return new ConfirmOrderViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OrderHeaderViewHolder) {
            ((OrderHeaderViewHolder) holder).headerTitle.setText(HEADER_TEXT);
        } else {
            Menu.MenuItem item = basketItems.get(position - 1);
            ConfirmOrderViewHolder viewHolder = (ConfirmOrderViewHolder) holder;
            viewHolder.name.setText(" "+item.getName());
            viewHolder.quantity.setText(" "+String.valueOf(item.getCount()));
            viewHolder.price.setText(" "+String.format("€%.2f", item.getPrice()));
        }
    }

    @Override
    public int getItemCount() {
        return basketItems.size() + 1;
    }

    static class OrderHeaderViewHolder extends RecyclerView.ViewHolder {
        TextView headerTitle;

        public OrderHeaderViewHolder(View itemView) {
            super(itemView);
            headerTitle = itemView.findViewById(R.id.header_title);
        }
    }

    static class ConfirmOrderViewHolder extends RecyclerView.ViewHolder {
        TextView name, quantity, price;

        public ConfirmOrderViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.confirmed_order_item_name);
            quantity = itemView.findViewById(R.id.confirmed_order_item_quantity);
            price = itemView.findViewById(R.id.confirmed_order_item_price);
        }
    }
}
