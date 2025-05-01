package com.example.unieats.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unieats.R;
import com.example.unieats.controller.BasketController;
import com.example.unieats.model.Menu;

import java.util.List;

public class ConfirmOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ORDER = 1;
    private static final String HEADER_TEXT = "Confirm Your Order";
    private final List<Menu.MenuItem> basketItems;
    private final Context context;
    private OnBasketEmptyListener onBasketEmptyListener;

    private OnBasketChangedListener changeListener;


    public ConfirmOrderAdapter(List<Menu.MenuItem> basketItems, Context context, OnBasketEmptyListener onBasketEmptyListener, OnBasketChangedListener listener) {
        this.basketItems = basketItems;
        this.context = context;
        this.onBasketEmptyListener = onBasketEmptyListener;
        this.changeListener = listener;
    }

    // Define the listener interface
    public interface OnBasketEmptyListener {
        void onBasketEmpty();
    }

    public interface OnBasketChangedListener {
        void onQuantityChanged();
    }


    @Override
    public int getItemViewType(int position) {
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
            Menu.MenuItem menuItem = basketItems.get(position - 1);
            BasketController basketController = BasketController.getInstance();

            ((ConfirmOrderViewHolder) holder).name.setText(menuItem.getName());
            ((ConfirmOrderViewHolder) holder).price.setText(String.format("€%.2f", menuItem.getPrice()));
            ((ConfirmOrderViewHolder) holder).quantity.setText("" + menuItem.getCount());

            // Handle "Remove" button click
            ((ConfirmOrderViewHolder) holder).removeButton.setOnClickListener(v -> {
                basketController.removeItem(menuItem); // Remove item from basket

                if (basketController.getTotalItemCount() == 0) {
                    if (onBasketEmptyListener != null) {
                        onBasketEmptyListener.onBasketEmpty();  // Notify the parent that the basket is empty
                    }
                } else if (menuItem.getCount() == 0) {
                    basketItems.remove(position - 1);
                    notifyItemRemoved(position);  // Remove item from the adapter
                    notifyItemRangeChanged(position, basketItems.size()); // Refresh remaining items
                } else {
                    ((ConfirmOrderViewHolder) holder).quantity.setText(String.valueOf(menuItem.getCount()));
                    notifyItemChanged(position);  // Refresh the item
                }

                if (changeListener != null) {
                    changeListener.onQuantityChanged();
                }
            });



            // Handle "Add" button click
            ((ConfirmOrderViewHolder) holder).addButton.setOnClickListener(v -> {
                basketController.addItem(menuItem);
                ((ConfirmOrderViewHolder) holder).quantity.setText(String.valueOf(menuItem.getCount()));
                notifyItemChanged(position);  // Notify RecyclerView to refresh this item

                if (changeListener != null) {
                    changeListener.onQuantityChanged();
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return 1 + basketItems.size();  // 1 for the header and the rest for items
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
        Button removeButton, addButton;

        public ConfirmOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.confirm_order_item_name);
            quantity = itemView.findViewById(R.id.confirm_order_item_quantity);
            price = itemView.findViewById(R.id.confirm_order_item_price);
            removeButton = itemView.findViewById(R.id.order_item_remove_button);
            addButton = itemView.findViewById(R.id.order_item_add_button);
        }
    }
}
