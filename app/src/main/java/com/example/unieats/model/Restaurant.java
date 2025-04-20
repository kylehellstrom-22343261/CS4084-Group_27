package com.example.unieats.model;

public class Restaurant {
    private final String businessName;
    private final String description;
    private final double rating;
    private final int image;

    public Restaurant(String businessName, String description, double rating, int image) {
        this.businessName = businessName;
        this.description = description;
        this.rating = rating;
        this.image = image;
    }

    public String getBusinessName() { return businessName; }
    public String getDescription() { return description; }
    public double getRating() { return rating; }
    public int getImage() { return image; }
}
