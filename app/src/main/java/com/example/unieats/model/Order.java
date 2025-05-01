package com.example.unieats.model;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<Menu.MenuItem> menuItems;
    private boolean pending;
    private int orderNumber;
    private Timestamp orderTime;
    private Timestamp collectionTime;
    private String businessName;

    public Order() {
        this.menuItems = new ArrayList<>();
        this.pending = false;
        this.orderNumber = 0;
        this.orderTime = Timestamp.now();
        this.businessName = "";
    }

    public Order(List<Menu.MenuItem> menuItems, boolean pending) {
        this.menuItems = menuItems;
        this.pending = pending;
        this.orderNumber = ((int) Timestamp.now().getSeconds());
        this.orderTime = Timestamp.now();
        this.collectionTime = new Timestamp(
                orderTime.getSeconds() + ((15 * 60) + (int) (Math.random() * 15)),
                orderTime.getNanoseconds()
        );
        this.businessName = menuItems.get(0).getBusinessName();
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

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    public Timestamp getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(Timestamp collectionTime) {
        this.collectionTime = collectionTime;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
}
