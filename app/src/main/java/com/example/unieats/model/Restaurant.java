package com.example.unieats.model;

public class Restaurant {
    private final String businessName;
    private final String description;
    private final double rating;
    private final int imageResId;

    public Restaurant(String businessName, String description, double rating, int imageResId) {
        this.businessName = businessName;
        this.description = description;
        this.rating = rating;
        this.imageResId = imageResId;
    }

    public String getBusinessName() { return businessName; }
    public String getDescription() { return description; }
    public double getRating() { return rating; }
    public int getImageResId() { return imageResId; }
}
