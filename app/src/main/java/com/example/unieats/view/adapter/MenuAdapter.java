package com.example.unieats.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
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

    /**
     * Returns the view type of the item at the specified position.
     * @param position position to query
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (position == 0) return TYPE_RESTAURANT_HEADER;
        else if (position == 1) return TYPE_HEADER;
        else return TYPE_MENU;
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return
     */
    @Override
    public int getItemCount() {
        return items.size() + 2;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_RESTAURANT_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant_header, parent, false);
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
            Menu.MenuItem restaurantInfo = items.get(0);
            RestaurantHeaderViewHolder vh = (RestaurantHeaderViewHolder) holder;
            vh.restaurantName.setText(restaurantInfo.getBusinessName());
            vh.restaurantDistance.setText("1.2 km away");
            vh.costIcon1.setVisibility(View.VISIBLE);
            vh.costIcon2.setVisibility(View.VISIBLE);
            vh.costIcon3.setVisibility(View.GONE);

            vh.mapButton.setOnClickListener(v -> {
                if (context instanceof AppCompatActivity) {
                    FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
                    fm.beginTransaction()
                            .replace(R.id.fragment_container, new MapFragment(restaurantInfo.getBusinessName()))
                            .addToBackStack(null)
                            .commit();
                }
            });
        } else if (holder instanceof MenuHeaderViewHolder) {
            ((MenuHeaderViewHolder) holder).headerTitle.setText(HEADER_TEXT);
        } else if (holder instanceof MenuViewHolder) {
            Menu.MenuItem menuItem = items.get(position - 2);
            MenuViewHolder vh = (MenuViewHolder) holder;

            vh.name.setText(menuItem.getName());
            vh.description.setText(menuItem.getDescription());
            vh.price.setText(String.format("$%.2f", menuItem.getPrice()));

            int count = menuItem.getCount();
            vh.itemCountText.setText(String.valueOf(count));
            vh.itemCountText.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
            vh.removeButton.setVisibility(count > 0 ? View.VISIBLE : View.GONE);

            vh.addButton.setOnClickListener(v -> {
                BasketController.getInstance().addItem(menuItem);
                notifyItemChanged(position);
                basketUpdateListener.onBasketUpdated();
            });

            vh.removeButton.setOnClickListener(v -> {
                BasketController.getInstance().removeItem(menuItem);
                notifyItemChanged(position);
                basketUpdateListener.onBasketUpdated();
            });
        }
    }


    public interface BasketUpdateListener {
        void onBasketUpdated();
    }

    /* ViewHolders */

    static class RestaurantHeaderViewHolder extends RecyclerView.ViewHolder {
        TextView restaurantName, restaurantDistance, costIcon1, costIcon2, costIcon3;
        Button mapButton;

        public RestaurantHeaderViewHolder(View view) {
            super(view);
            restaurantName = view.findViewById(R.id.restaurant_name);
            restaurantDistance = view.findViewById(R.id.restaurant_distance);
            costIcon1 = view.findViewById(R.id.cost_icon_1);
            costIcon2 = view.findViewById(R.id.cost_icon_2);
            costIcon3 = view.findViewById(R.id.cost_icon_3);
            mapButton = view.findViewById(R.id.map_button);
        }
    }

    static class MenuHeaderViewHolder extends RecyclerView.ViewHolder {
        TextView headerTitle;
        public MenuHeaderViewHolder(View view) {
            super(view);
            headerTitle = view.findViewById(R.id.header_title);
        }
    }

    static class MenuViewHolder extends RecyclerView.ViewHolder {
        TextView name, description, price, itemCountText;
        Button addButton, removeButton;
        public MenuViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.menu_item_name);
            description = view.findViewById(R.id.menu_item_description);
            price = view.findViewById(R.id.menu_item_price);
            addButton = view.findViewById(R.id.menu_item_add_button);
            removeButton = view.findViewById(R.id.menu_item_remove_button);
            itemCountText = view.findViewById(R.id.menu_item_count);
        }
    }
}
