package com.example.unieats.controller;

import com.example.unieats.model.Menu.MenuItem;
import java.util.ArrayList;
import java.util.List;

public class BasketController {
    private static List<MenuItem> basket = new ArrayList<>();

    public static void addItem(MenuItem item) {
        basket.add(item);
    }

    public static void removeItem(MenuItem item) {
        basket.remove(item);
    }

    public static List<MenuItem> getBasket() {
        return basket;
    }

    public static void clearBasket() {
        basket.clear();
    }
}
