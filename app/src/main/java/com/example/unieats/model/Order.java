package com.example.unieats.model;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<Menu.MenuItem> menuItems;
    private boolean pending;
    private int orderNumber;

    public Order() {
        this.menuItems = new ArrayList<>();
        this.pending = false;
        this.orderNumber = 0;
    }

    public Order(List<Menu.MenuItem> menuItems, boolean pending) {
        this.menuItems = menuItems;
        this.pending = pending;
        this.orderNumber = ((int) Timestamp.now().getSeconds());
    }


    // Getters and setters
    public List<Menu.MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<Menu.MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }
}
