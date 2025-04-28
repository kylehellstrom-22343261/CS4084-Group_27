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

public class ConfirmOrderAdapter extends RecyclerView.Adapter<ConfirmOrderAdapter.ConfirmOrderViewHolder> {

    private final List<Menu.MenuItem> basketItems;
    private Context context;

    public ConfirmOrderAdapter(List<Menu.MenuItem> basketItems, Context context) {
        this.basketItems = basketItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ConfirmOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_confirm_order, parent, false);
        return new ConfirmOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConfirmOrderViewHolder holder, int position) {
        // Only show items with count > 0
        Menu.MenuItem menuItem = basketItems.get(position);

        holder.name.setText(menuItem.getName());
        holder.description.setText(menuItem.getDescription());
        holder.price.setText(String.format("€%.2f", menuItem.getPrice()));
        holder.quantity.setText("Quantity: " + menuItem.getCount());
    }

    @Override
    public int getItemCount() {
        int count = 0;
        for (Menu.MenuItem item : basketItems) {
            if (item.getCount() > 0) {
                count++;
            }
        }
        return count;
    }

    public static class ConfirmOrderViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView description;
        TextView price;
        TextView quantity;

        public ConfirmOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.confirm_order_item_name);
            description = itemView.findViewById(R.id.confirm_order_item_description);
            price = itemView.findViewById(R.id.confirm_order_item_price);
            quantity = itemView.findViewById(R.id.confirm_order_item_quantity);
        }
    }
}
