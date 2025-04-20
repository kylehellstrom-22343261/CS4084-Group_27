package com.example.unieats.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unieats.R;
import com.example.unieats.model.Restaurant;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    private final List<Restaurant> restaurantList;
    private final OnRestaurantClickListener listener;

    public RestaurantAdapter(List<Restaurant> restaurants, OnRestaurantClickListener listener) {
        this.restaurantList = restaurants;
        this.listener = listener;
    }
    public interface OnRestaurantClickListener {
        void onRestaurantClick(Restaurant restaurant);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, description, rating;
        ImageView image;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.restaurant_name);
            description = view.findViewById(R.id.restaurant_description);
            rating = view.findViewById(R.id.restaurant_rating);
            image = view.findViewById(R.id.restaurant_image);
        }
    }

    @NonNull
    @Override
    public RestaurantAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_restaurant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantAdapter.ViewHolder holder, int position) {
        Restaurant restaurant = restaurantList.get(position);
        holder.name.setText(restaurant.getBusinessName());
        holder.description.setText(restaurant.getDescription());
        holder.rating.setText(String.format("%.1f ⭐", restaurant.getRating()));
        holder.image.setImageResource(restaurant.getImage());

        // listener to change fragment to menu
        holder.itemView.setOnClickListener(view -> {
            if (listener != null) {
                listener.onRestaurantClick(restaurant);
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }
}
