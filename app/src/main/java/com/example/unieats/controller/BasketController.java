package com.example.unieats.controller;

import android.util.Log;

import com.example.unieats.model.Menu.MenuItem;

import java.util.HashMap;
import java.util.Map;

public class BasketController {

    /**
     * Only 1 basket can exist
     */
    private static BasketController instance;

    /**
     * Basket: Map each MenuItem to the quantity
     */
    private Map<MenuItem, Integer> basketItems;

    /**
     * Constructor
     */
    private BasketController() {
        basketItems = new HashMap<>();
    }

    /**
     * Get instance of BasketController
     * @return instance
     */
    public static BasketController getInstance() {
        if (instance == null) {
            instance = new BasketController();
        }
        return instance;
    }

    /**
     * Add item to basket
     */
    public void addItem(MenuItem item) {
        int currentQuantity = basketItems.containsKey(item) ? basketItems.get(item) : 0;
        basketItems.put(item, currentQuantity + 1);
    }

    /**
     * Remove 1 item from basket
     */
    public void removeItem(MenuItem item) {
        if (basketItems.containsKey(item)) {
            int quantity = basketItems.get(item);
            if (quantity > 1) {
                basketItems.put(item, quantity - 1);
            } else {
                basketItems.remove(item);
            }
        }
    }

    /**
     * Delete all of 1 item from basket
     */
    public void deleteItem(MenuItem item) {
        basketItems.remove(item);
    }

    /**
     * Get current basket items
     */
    public Map<MenuItem, Integer> getBasketItems() {
        return basketItems;
    }

    /**
     * Clear basket
     */
    public void clearBasket() {
        basketItems.clear();
    }

    /**
     * Total number of items in basket
     */
    public int getTotalItemCount() {
        int total = 0;
        for (int quantity : basketItems.values()) {
            total += quantity;
        }
        return total;
    }
}
