package com.example.unieats.model;

public class Restaurant {
    private final String businessName;
    private final String description;
    private final double rating;
    private final String image;
    private final double latitude;
    private final double longitude;

    public Restaurant() {
        this.businessName = "";
        this.description = "";
        this.rating = 0;
        this.image = "";
        this.latitude = 0;
        this.longitude = 0;
    }

    public Restaurant(String businessName, String description, double rating, String image, double latitude, double longitude) {
        this.businessName = businessName;
        this.description = description;
        this.rating = rating;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getBusinessName() { return businessName; }
    public String getDescription() { return description; }
    public double getRating() { return rating; }
    public String getImage() { return image; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
}
