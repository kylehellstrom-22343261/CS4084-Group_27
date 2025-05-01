package com.example.unieats.controller;

import com.example.unieats.model.Menu.MenuItem;

import java.util.List;


public class BasketController {
    private static BasketController instance;

    private List<MenuItem> allMenuItems;

    private BasketController() { }

    public static BasketController getInstance() {
        if (instance == null) {
            instance = new BasketController();
        }
        return instance;
    }

    public void setMenuItems(List<MenuItem> newMenuItems) {
        // Preserve previous counts if already set
        if (this.allMenuItems != null) {
            for (MenuItem newItem : newMenuItems) {
                for (MenuItem oldItem : this.allMenuItems) {
                    if (oldItem.getName().equals(newItem.getName()) &&
                            oldItem.getBusinessName().equals(newItem.getBusinessName())) {
                        newItem.setCount(oldItem.getCount());
                        break;
                    }
                }
            }
        }
        this.allMenuItems = newMenuItems;
    }

    public void addItem(MenuItem item) {
        item.setCount(item.getCount() + 1);
    }

    public void removeItem(MenuItem item) {
        if (item.getCount() > 0) {
            item.setCount(item.getCount() - 1);
        }
    }

    public int getTotalItemCount() {
        if (allMenuItems == null) return 0;
        int total = 0;
        for (MenuItem item : allMenuItems) {
            total += item.getCount();
        }
        return total;
    }

    public List<MenuItem> getBasketItems() {
        return allMenuItems;
    }

    public void clearBasket() {
        if (allMenuItems != null) {
            for (MenuItem item : allMenuItems) {
                item.setCount(0);
            }
        }
    }

    public double getTotalPrice() {
        if (allMenuItems == null) return 0.0;
        double total = 0.0;
        for (MenuItem item : allMenuItems) {
            total += item.getPrice() * item.getCount();
        }
        return total;
    }
}