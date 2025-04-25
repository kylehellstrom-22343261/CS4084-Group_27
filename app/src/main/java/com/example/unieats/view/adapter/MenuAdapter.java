package com.example.unieats.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unieats.R;
import com.example.unieats.model.Menu;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_MENU = 1;
    private static final String HEADER_TEXT = "All Items";
    private final List<Menu.MenuItem> items;

    public MenuAdapter(List<Menu.MenuItem> items) {
        this.items = items;
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

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
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
        }
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
        TextView name, description, price;
//        ImageView image;

        public MenuViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.menu_item_name);
            description = view.findViewById(R.id.menu_item_description);
            price = view.findViewById(R.id.menu_item_price);
//            image = view.findViewById(R.id.menu_item_image);
        }
    }

}
