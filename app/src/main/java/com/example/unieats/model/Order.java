package com.example.unieats.model;

import com.google.firebase.Timestamp;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Order {
    private List<Menu.MenuItem> menuItems;
    private boolean pending;
    private int orderNumber;
    private String orderTime;
    private String collectionTime;
    private String businessName;

    public Order() {
        this.menuItems = new ArrayList<>();
        this.pending = false;
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
        try {
            long seconds = Long.parseLong(orderTime.replaceAll(".*seconds=(\\d+),.*", "$1"));

            Date date = new Date(seconds * 1000L);

            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault());
            return sdf.format(date);
        } catch (Exception e) {
            return orderTime;
        }
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getCollectionTime() {
        try {
            long seconds = Long.parseLong(collectionTime.replaceAll(".*seconds=(\\d+),.*", "$1"));

            Date date = new Date(seconds * 1000L);

            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault());
            return sdf.format(date);
        } catch (Exception e) {
            return collectionTime; // fallback if parsing fails
        }
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
