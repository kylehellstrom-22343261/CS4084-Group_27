package com.example.unieats.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unieats.R;
import com.example.unieats.model.Restaurant;

import java.util.Comparator;
import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_RESTAURANT = 1;
    private static final String HEADER_TEXT = "All Restaurants";

    private final List<Restaurant> restaurantList;
    private final OnRestaurantClickListener listener;

    public RestaurantAdapter(
            List<Restaurant> restaurants,
            OnRestaurantClickListener listener)
    {
        restaurants.sort(Comparator.comparingDouble(Restaurant::getRating).reversed());
        this.restaurantList = restaurants;

        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_HEADER : TYPE_RESTAURANT;
    }

    @Override
    public int getItemCount() {
        return restaurantList.size() + 1; // +1 for header
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
                    .inflate(R.layout.item_restaurant, parent, false);
            return new RestaurantViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.headerTitle.setText(HEADER_TEXT);
        } else if (holder instanceof RestaurantViewHolder) {
            Restaurant restaurant = restaurantList.get(position - 1); // offset by 1
            RestaurantViewHolder vh = (RestaurantViewHolder) holder;

            vh.name.setText(restaurant.getBusinessName());
            vh.description.setText(restaurant.getDescription());

            double rating = restaurant.getRating();
            int colorId;
            if (rating >= 4.5) {
                colorId = R.color.green;
            } else if (rating >= 3.0) {
                colorId = R.color.orange;
            } else {
                colorId = R.color.red;
            }
            Context context = vh.itemView.getContext();
            int color = ContextCompat.getColor(context, colorId);
            // Rating star and rating text colour
            vh.ratingStar.setTextColor(color);
            vh.rating.setTextColor(color);
            vh.rating.setText(String.format("%.1f", restaurant.getRating()));
            
            vh.image.setImageResource(holder.itemView.getContext().getResources()
                    .getIdentifier(restaurant.getImage(), "drawable", holder.itemView.getContext().getPackageName()));


            vh.itemView.setOnClickListener(v -> {
                if (listener != null) listener.onRestaurantClick(restaurant);
            });
        }
    }

    public interface OnRestaurantClickListener {
        void onRestaurantClick(Restaurant restaurant);
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
     * Restaurant ViewHolder
     */
    static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        TextView name, description, ratingStar, rating;
        ImageView image;

        public RestaurantViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.restaurant_name);
            description = view.findViewById(R.id.restaurant_description);
            ratingStar = view.findViewById(R.id.restaurant_rating_star);
            rating = view.findViewById(R.id.restaurant_rating);
            image = view.findViewById(R.id.restaurant_image);
        }
    }
}
