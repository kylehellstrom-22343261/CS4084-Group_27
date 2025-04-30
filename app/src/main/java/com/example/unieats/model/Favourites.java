package com.example.unieats.model;

import java.util.ArrayList;
import java.util.List;

public class Favourites {
    private static Favourites instance;
    private final List<Restaurant> favourites;

    private Favourites() {
        favourites = new ArrayList<>();
    }

    public static Favourites getInstance() {
        if (instance == null) {
            instance = new Favourites();
        }
        return instance;
    }

    public void addFavourite(Restaurant restaurant) {
        if (!favourites.contains(restaurant)) {
            favourites.add(restaurant);
        }
    }

    public void removeFavourite(Restaurant restaurant) {
        favourites.remove(restaurant);
    }

    public List<Restaurant> getFavourites() {
        return new ArrayList<>(favourites); // Return a copy
    }

    public boolean isFavourite(Restaurant restaurant) {
        return favourites.contains(restaurant);
    }
}
