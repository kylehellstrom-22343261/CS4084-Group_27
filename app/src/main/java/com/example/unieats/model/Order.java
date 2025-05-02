package com.example.unieats.model;

import com.google.firebase.Timestamp;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<Menu.MenuItem> menuItems;
    private boolean pending;
    private int orderNumber;
    private String orderTime;
    private String collectionTime;
    private String businessName;

    public Order() {
        this.menuItems = new ArrayList<>();
        this.pending = true;
        this.orderNumber = 0;
        this.orderTime = Timestamp.now().toString();
        this.collectionTime = Timestamp.now().toString();
        this.businessName = "";
    }

    public Order(List<Menu.MenuItem> menuItems, boolean pending) {
        Timestamp currentTimestamp = Timestamp.now();

        this.menuItems = menuItems;
        this.pending = pending;
        this.orderNumber = ((int) currentTimestamp.getSeconds());
        this.orderTime = currentTimestamp.toString();
        this.collectionTime = new Timestamp(
                currentTimestamp.getSeconds() + ((15 * 60) + (int) (Math.random() * 15)),
                currentTimestamp.getNanoseconds()
        ).toString();
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

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(String collectionTime) {
        this.collectionTime = collectionTime;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
}
