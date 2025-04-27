package com.example.unieats.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unieats.R;
import com.example.unieats.controller.BasketController;
import com.example.unieats.model.Menu;
import com.example.unieats.view.fragment.MapFragment;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_RESTAURANT_HEADER = 0;
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_MENU = 2;
    private static final String HEADER_TEXT = "All Items";
    private final List<Menu.MenuItem> items;
    private final BasketUpdateListener basketUpdateListener;
    private Context context;

    public MenuAdapter(List<Menu.MenuItem> items, BasketUpdateListener basketUpdateListener, Context context) {
        this.items = items;
        this.basketUpdateListener = basketUpdateListener;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_RESTAURANT_HEADER;
        } else if (position == 1) {
            return TYPE_HEADER;
        } else return TYPE_MENU;
    }

    @Override
    public int getItemCount() {
        return items.size() + 2;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_RESTAURANT_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_header, parent, false);
            return new RestaurantHeaderViewHolder(view);
        } else if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header, parent, false);
            return new MenuHeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
            return new MenuViewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RestaurantHeaderViewHolder) {
            // Position 0: Restaurant Header
            RestaurantHeaderViewHolder restaurantHeaderViewHolder = (RestaurantHeaderViewHolder) holder;

            Menu.MenuItem restaurantInfo = items.get(0);

            restaurantHeaderViewHolder.restaurantName.setText(restaurantInfo.getBusinessName());
            restaurantHeaderViewHolder.restaurantDistance.setText("1.2 km away");

            restaurantHeaderViewHolder.costIcon1.setVisibility(View.VISIBLE);
            restaurantHeaderViewHolder.costIcon2.setVisibility(View.VISIBLE);
            restaurantHeaderViewHolder.costIcon3.setVisibility(View.GONE);

            restaurantHeaderViewHolder.mapButton.setOnClickListener(v -> {
                if (context instanceof AppCompatActivity) {
                    MapFragment mapFragment = new MapFragment();
                    FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, mapFragment)
                            .addToBackStack(null).commit();
                }
            });

        } else if (holder instanceof MenuHeaderViewHolder) {
            // Position 1: Menu Header
            MenuHeaderViewHolder menuHeaderViewHolder = (MenuHeaderViewHolder) holder;
            menuHeaderViewHolder.headerTitle.setText(HEADER_TEXT);

        } else if (holder instanceof MenuViewHolder) {
            // Positions 2: Menu Items
            int menuPosition = position - 2; // Offset because of two headers
            Menu.MenuItem menuItem = items.get(menuPosition);

            MenuViewHolder menuViewHolder = (MenuViewHolder) holder;

            menuViewHolder.name.setText(menuItem.getName());
            menuViewHolder.description.setText(menuItem.getDescription());
            menuViewHolder.price.setText(String.format("$%.2f", menuItem.getPrice()));

            updateUI(menuViewHolder, menuItem);
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
            vh.itemCountText.animate().scaleX(1.2f).scaleY(1.2f).setDuration(20).withEndAction(() -> vh.itemCountText.animate().scaleX(1f).scaleY(1f).setDuration(20)).start();
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
     * Restaurant Header ViewHolder
     */
    static class RestaurantHeaderViewHolder extends RecyclerView.ViewHolder {
        TextView restaurantName;
        ImageView restaurantImage;
        TextView restaurantDistance;
        TextView costIcon1, costIcon2, costIcon3;
        Button mapButton;

        public RestaurantHeaderViewHolder(View view) {
            super(view);
            restaurantName = view.findViewById(R.id.restaurant_name);
            restaurantImage = view.findViewById(R.id.restaurant_image);
            restaurantDistance = view.findViewById(R.id.restaurant_distance);
            costIcon1 = view.findViewById(R.id.cost_icon_1);
            costIcon2 = view.findViewById(R.id.cost_icon_2);
            costIcon3 = view.findViewById(R.id.cost_icon_3);
            mapButton = view.findViewById(R.id.map_button);
        }
    }


    /**
     * Header ViewHolder
     */
    static class MenuHeaderViewHolder extends RecyclerView.ViewHolder {
        TextView headerTitle;

        public MenuHeaderViewHolder(View view) {

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
