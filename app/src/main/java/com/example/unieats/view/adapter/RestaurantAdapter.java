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

    public RestaurantAdapter(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
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
        Restaurant r = restaurantList.get(position);
        holder.name.setText(r.getBusinessName());
        holder.description.setText(r.getDescription());
        holder.rating.setText(String.format("%.1f ⭐", r.getRating()));
        holder.image.setImageResource(r.getImageResId());
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }
}
