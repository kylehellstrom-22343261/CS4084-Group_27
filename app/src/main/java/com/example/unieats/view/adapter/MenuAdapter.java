package com.example.unieats.view.adapter;

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

public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_MENU = 1;
    private static final String HEADER_TEXT = "All Items";
    private final List<Menu.MenuItem> items;
    private BasketUpdateListener basketUpdateListener;

    public MenuAdapter(List<Menu.MenuItem> items, BasketUpdateListener basketUpdateListener) {
        this.items = items;
        this.basketUpdateListener = basketUpdateListener;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_HEADER : TYPE_MENU;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_menu, parent, false);
            return new MenuViewHolder(view);
        }
    }

    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        Menu.MenuItem item = items.get(position);
        holder.name.setText(item.getName());
        holder.description.setText(item.getDescription());
        holder.price.setText(String.format("$%.2f", item.getPrice()));
//        holder.image.setImageResource(holder.itemView.getContext().getResources()
//                .getIdentifier(item.getImage(), "drawable", holder.itemView.getContext().getPackageName()));
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Menu.MenuItem menuItem = items.get(position);

        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.headerTitle.setText(HEADER_TEXT);
        } else if (holder instanceof MenuViewHolder) {
            Menu.MenuItem menu = items.get(position - 1); // offset by 1
            MenuViewHolder vh = (MenuViewHolder) holder;

            vh.name.setText(menu.getName());
            vh.description.setText(menu.getDescription());
            vh.price.setText(String.format("$%.2f", menu.getPrice()));
//          vh.image.setImageResource(item.getImage());

            // Update UI
            updateUI(vh, menuItem);

        }
    }

    private void updateUI(MenuViewHolder vh, Menu.MenuItem menuItem) {
        BasketController basketController = BasketController.getInstance();
        int count = basketController.getBasketItems().getOrDefault(menuItem, 0);

        vh.removeButton.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
        vh.itemCountText.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
        vh.itemCountText.setText(String.valueOf(count));


        // Add button logic
        vh.addButton.setOnClickListener(v -> {
            basketController.addItem(menuItem);
            int position = vh.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                notifyItemChanged(position);
            }
            basketUpdateListener.onBasketUpdated(); // update basket button
        });

        // Delete button logic
        vh.removeButton.setOnClickListener(v -> {
            basketController.removeItem(menuItem);
            int position = vh.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                notifyItemChanged(position);
            }
            basketUpdateListener.onBasketUpdated();
        });


        vh.itemCountText.setText(String.valueOf(count));
        vh.itemCountText.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
        vh.removeButton.setVisibility(count > 0 ? View.VISIBLE : View.GONE);

        // Optional: animate count pop when it changes
        if (count == 1) {
            vh.itemCountText.animate()
                    .scaleX(1.2f).scaleY(1.2f)
                    .setDuration(20)
                    .withEndAction(() -> vh.itemCountText.animate()
                            .scaleX(1f).scaleY(1f)
                            .setDuration(20))
                    .start();
        }

        // Also notify the basket button to update at the top
        if (basketUpdateListener != null) {
            basketUpdateListener.onBasketUpdated();
        }
    }


    public interface BasketUpdateListener {
        void onBasketUpdated();
    }


    /* ViewHolders */

    /**
     * Header ViewHolder
     */
    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView headerTitle; // Header
        public HeaderViewHolder(View view) {
            super(view);
            headerTitle = view.findViewById(R.id.header_title);
        }
    }

    /**
     * Menu ViewHolder
     */
    static class MenuViewHolder extends RecyclerView.ViewHolder {
        TextView name, description, price, itemCountText;
        Button addButton, removeButton;
//        ImageView image;

        public MenuViewHolder(View view) {
            super(view);
            // From MenuItem
            name = view.findViewById(R.id.menu_item_name);
            description = view.findViewById(R.id.menu_item_description);
            price = view.findViewById(R.id.menu_item_price);
            addButton = view.findViewById(R.id.menu_item_add_button);
            removeButton = view.findViewById(R.id.menu_item_remove_button);
            itemCountText = view.findViewById(R.id.menu_item_count);
//            image = view.findViewById(R.id.menu_item_image);
        }
    }

}
